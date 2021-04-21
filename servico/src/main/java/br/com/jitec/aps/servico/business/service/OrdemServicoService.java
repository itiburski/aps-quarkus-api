package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.commons.business.util.QueryBuilder;
import br.com.jitec.aps.servico.business.data.OrdemServicoFilter;
import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.data.repository.OrdemServicoRepository;
import br.com.jitec.aps.servico.payload.mapper.OrdemServicoMapper;
import br.com.jitec.aps.servico.payload.request.OrdemServicoCreateRequest;
import br.com.jitec.aps.servico.payload.request.OrdemServicoLancamentoRequest;
import br.com.jitec.aps.servico.payload.request.OrdemServicoUpdateRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class OrdemServicoService {

	private static final ZoneOffset OFFSET = OffsetDateTime.now().getOffset();

	@Inject
	OrdemServicoRepository repository;

	@Inject
	OrdemServicoMapper ordemServicoMapper;

	@Inject
	ClienteReplicaService clienteService;

	@Inject
	TipoServicoService tipoServicoService;

	@Inject
	ClienteSaldoProducer clienteSaldoProducer;

	public Paged<OrdemServico> getAll(Pagination pagination, OrdemServicoFilter filter) {
		QueryBuilder builder = new QueryBuilder();
		builder.setSortBy("numero");

		builder.addFilter(Objects.nonNull(filter.getClienteUid()), "cliente.uid = :clienteUid", "clienteUid",
				filter.getClienteUid());
		if (Objects.nonNull(filter.getEntradaFrom())) {
			builder.addFilter("entrada >= :entradaFrom", "entradaFrom",
					OffsetDateTime.of(filter.getEntradaFrom(), LocalTime.MIN, OFFSET));
		}
		if (Objects.nonNull(filter.getEntradaTo())) {
			builder.addFilter("entrada <= :entradaTo", "entradaTo",
					OffsetDateTime.of(filter.getEntradaTo(), LocalTime.MAX, OFFSET));
		}
		if (Objects.nonNull(filter.getEntregue())) {
			builder.addFilter(Boolean.TRUE.equals(filter.getEntregue()) ? "entrega is not null" : "entrega is null");
		}
		if (Objects.nonNull(filter.getLancado())) {
			builder.addFilter(
					Boolean.TRUE.equals(filter.getLancado()) ? "lancamento is not null" : "lancamento is null");
		}
		if (Objects.nonNull(filter.getFaturado())) {
			builder.addFilter(Boolean.TRUE.equals(filter.getFaturado()) ? "fatura is not null" : "fatura is null");
		}

		PanacheQuery<OrdemServico> query = repository.find(builder.getQuery(), builder.getParams())
				.page(Page.of(pagination.getPageZeroBased(), pagination.getSize()));
		return new Paged<OrdemServico>(query.list(), query.pageCount(), query.count());
	}

	public List<OrdemServico> get(List<UUID> ordensServicoUid) {
		List<OrdemServico> ordensServico = repository.findByUids(ordensServicoUid);
		if (ordensServico.size() != ordensServicoUid.size()) {
			throw new DataNotFoundException(
					"Uma ou mais ordens de serviço não foram encontradas para os parâmetros informados");
		}
		return ordensServico;
	}

	public OrdemServico get(UUID ordemServicoUid) {
		return repository.findByUid(ordemServicoUid)
				.orElseThrow(() -> new DataNotFoundException("Ordem de Serviço não encontrada"));
	}

	private OrdemServico get(UUID ordemServicoUid, Integer version) {
		return repository.findByUidVersion(ordemServicoUid, version).orElseThrow(
				() -> new DataNotFoundException("Ordem de Serviço não encontrada para versao especificada"));
	}

	@Transactional
	public OrdemServico create(OrdemServicoCreateRequest request) {
		OrdemServico os = ordemServicoMapper.toOrdemServico(request);
		os.setNumero(repository.getNextNumeroOS());

		if (Objects.nonNull(request.getClienteUid())) {
			os.setCliente(getCliente(request.getClienteUid()));
		}
		if (Objects.nonNull(request.getTipoServicoUid())) {
			os.setTipoServico(tipoServicoService.get(request.getTipoServicoUid()));
		}

		repository.persist(os);

		return os;
	}

	private ClienteReplica getCliente(UUID clienteUid) {
		ClienteReplica cliente = clienteService.get(clienteUid);
		if (!cliente.getAtivo()) {
			throw new InvalidDataException("Não é possível cadastrar uma ordem de serviço para um cliente inativo");
		}
		return cliente;
	}

	@Transactional
	public OrdemServico update(UUID ordemServicoUid, Integer version, OrdemServicoUpdateRequest request) {
		OrdemServico os = get(ordemServicoUid, version);
		ordemServicoMapper.update(request, os);

		if (Objects.nonNull(request.getTipoServicoUid())) {
			os.setTipoServico(tipoServicoService.get(request.getTipoServicoUid()));
		}

		repository.persist(os);

		return os;
	}

	@Transactional
	public OrdemServico definirLancamento(UUID ordemServicoUid, Integer version,
			OrdemServicoLancamentoRequest request) {
		OrdemServico os = get(ordemServicoUid, version);

		if (Objects.nonNull(os.getFatura())) {
			throw new InvalidDataException("Ordem de serviço já foi faturada");
		}

		BigDecimal vlAnterior = os.getLancamento() != null ? os.getValor() : BigDecimal.ZERO;
		BigDecimal saldoARefletirNoCliente = vlAnterior.subtract(request.getValor());

		os.setValor(request.getValor());
		os.setLancamento(request.getLancamento());
		repository.persist(os);

		clienteSaldoProducer.sendUpdateSaldoCliente(os.getCliente().getUid(), saldoARefletirNoCliente);

		return os;
	}

}
