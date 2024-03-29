package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.commons.business.util.QueryBuilder;
import br.com.jitec.aps.servico.business.data.FaturaFilter;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.data.repository.FaturaRepository;
import br.com.jitec.aps.servico.data.repository.OrdemServicoRepository;
import br.com.jitec.aps.servico.payload.request.FaturaRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class FaturaService {

	private static final String FATURA_NAO_ENCONTRADA = "Fatura não encontrada";
	private static final String FATURA_NAO_ENCONTRADA_VERSION = "Fatura não encontrada para versão especificada";

	private static final ZoneOffset OFFSET = OffsetDateTime.now().getOffset();

	@Inject
	OrdemServicoService ordemServicoService;

	@Inject
	FaturaRepository repository;

	@Inject
	OrdemServicoRepository osRepository;

	public Paged<Fatura> getAll(Pagination pagination, FaturaFilter filter) {
		QueryBuilder builder = new QueryBuilder();
		builder.setSortBy("codigo");

		builder.addFilter(Objects.nonNull(filter.getClienteUid()), "cliente.uid = :clienteUid", "clienteUid",
				filter.getClienteUid());
		builder.addFilter(Objects.nonNull(filter.getCodigo()), "codigo = :codigo", "codigo", filter.getCodigo());
		if (Objects.nonNull(filter.getDataFrom())) {
			builder.addFilter("data >= :dataFrom", "dataFrom",
					OffsetDateTime.of(filter.getDataFrom(), LocalTime.MIN, OFFSET));
		}
		if (Objects.nonNull(filter.getDataTo())) {
			builder.addFilter("data <= :dataTo", "dataTo",
					OffsetDateTime.of(filter.getDataTo(), LocalTime.MAX, OFFSET));
		}

		PanacheQuery<Fatura> query = repository.find(builder.getQuery(), builder.getParams())
				.page(Page.of(pagination.getPageZeroBased(), pagination.getSize()));
		return new Paged<Fatura>(query.list(), query.pageCount(), query.count());
	}

	public Fatura getComplete(UUID faturaUid) {
		return getComplete(faturaUid, null);
	}

	private Fatura getComplete(UUID faturaUid, Integer version) {
		QueryBuilder builder = new QueryBuilder();
		builder.addFilter("f.uid = :faturaUid", "faturaUid", faturaUid);
		builder.addFilter(Objects.nonNull(version), "f.version = :version", "version", version);

		Optional<Fatura> faturaOp = repository
				.find("from Fatura f left join fetch f.ordensServico where " + builder.getQuery(), builder.getParams())
				.singleResultOptional();
		if (faturaOp.isEmpty()) {
			throw new DataNotFoundException(
					Objects.nonNull(version) ? FATURA_NAO_ENCONTRADA_VERSION : FATURA_NAO_ENCONTRADA);
		}
		Fatura fatura = faturaOp.get();
		return fatura;
	}

	@Transactional
	public Fatura create(FaturaRequest request) {
		List<OrdemServico> ordensServico = ordemServicoService.get(request.getOrdensServicoUid());

		long clientesDistintos = ordensServico.stream().map(os -> os.getCliente().getUid()).distinct().count();
		if (clientesDistintos != 1) {
			throw new InvalidDataException("As ordens de serviço informadas devem ser do mesmo cliente");
		}

		long ordensServicoNaoLancadas = ordensServico.stream().map(OrdemServico::getLancamento).filter(Objects::isNull)
				.count();
		if (ordensServicoNaoLancadas > 0) {
			throw new InvalidDataException("Uma ou mais ordens de serviço informadas ainda não foram lançadas");
		}

		long ordensServicoJaFaturadas = ordensServico.stream().map(OrdemServico::getFatura).filter(Objects::nonNull)
				.count();
		if (ordensServicoJaFaturadas > 0) {
			throw new InvalidDataException("Uma ou mais ordens de serviço informadas já foram faturadas");
		}

		BigDecimal totalFatura = ordensServico.stream().map(OrdemServico::getValor).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		Fatura fatura = new Fatura();
		fatura.setCliente(ordensServico.get(0).getCliente());
		fatura.setValorTotal(totalFatura);
		fatura.setCodigo(repository.getNextCodigoFatura());
		fatura.setData(request.getData());
		repository.persist(fatura);

		ordensServico.forEach(os -> os.setFatura(fatura));
		osRepository.persist(ordensServico);

		return fatura;
	}

	@Transactional
	public void delete(UUID faturaUid, Integer version) {
		Fatura fatura = getComplete(faturaUid, version);
		fatura.getOrdensServico().forEach(os -> os.setFatura(null));
		repository.delete(fatura);
	}

}
