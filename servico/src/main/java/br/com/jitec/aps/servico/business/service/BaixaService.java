package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.commons.business.util.QueryBuilder;
import br.com.jitec.aps.servico.business.data.BaixaFilter;
import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.data.repository.BaixaRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class BaixaService {

	private static final ZoneOffset OFFSET = OffsetDateTime.now().getOffset();

	@Inject
	ClienteReplicaService clienteService;

	@Inject
	TipoBaixaService tipoBaixaService;

	@Inject
	BaixaRepository repository;

	@Inject
	ClienteSaldoProducer clienteSaldoProducer;

	public Paged<Baixa> getAll(Pagination pagination, BaixaFilter filter) {
		QueryBuilder builder = new QueryBuilder();
		builder.setSortBy("id");

		builder.addFilter(Objects.nonNull(filter.getClienteUid()), "cliente.uid = :clienteUid", "clienteUid",
				filter.getClienteUid());
		builder.addFilter(Objects.nonNull(filter.getTipoBaixaUid()), "tipoBaixa.uid = :tipoBaixaUid", "tipoBaixaUid",
				filter.getTipoBaixaUid());
		if (Objects.nonNull(filter.getDataFrom())) {
			builder.addFilter("data >= :dataFrom", "dataFrom",
					OffsetDateTime.of(filter.getDataFrom(), LocalTime.MIN, OFFSET));
		}
		if (Objects.nonNull(filter.getDataTo())) {
			builder.addFilter("data <= :dataTo", "dataTo",
					OffsetDateTime.of(filter.getDataTo(), LocalTime.MAX, OFFSET));
		}

		PanacheQuery<Baixa> query = repository.find(builder.getQuery(), builder.getParams())
				.page(Page.of(pagination.getPageZeroBased(), pagination.getSize()));
		return new Paged<Baixa>(query.list(), query.pageCount(), query.count());
	}

	public Baixa get(UUID baixaUid) {
		return repository.findByUid(baixaUid).orElseThrow(() -> new DataNotFoundException("Baixa não encontrada"));
	}

	private Baixa get(UUID baixaUid, Integer version) {
		return repository.findByUidVersion(baixaUid, version)
				.orElseThrow(() -> new DataNotFoundException("Baixa não encontrada para versao especificada"));
	}

	@Transactional
	public Baixa create(UUID tipoBaixaUid, OffsetDateTime data, BigDecimal valor, String observacao, UUID clienteUid) {
		Baixa baixa = new Baixa();
		baixa.setData(data);
		baixa.setValor(valor);
		baixa.setObservacao(observacao);
		baixa.setTipoBaixa(tipoBaixaService.get(tipoBaixaUid));
		baixa.setCliente(clienteService.get(clienteUid));

		repository.persist(baixa);

		BigDecimal saldoARefletirNoCliente = valor;
		clienteSaldoProducer.sendUpdateSaldoCliente(clienteUid, saldoARefletirNoCliente);

		return baixa;
	}

	@Transactional
	public Baixa update(UUID baixaUid, Integer version, UUID tipoBaixaUid, OffsetDateTime data, BigDecimal valor,
			String observacao) {
		Baixa baixa = get(baixaUid, version);

		BigDecimal saldoARefletirNoCliente = valor.subtract(baixa.getValor());

		baixa.setData(data);
		baixa.setValor(valor);
		baixa.setObservacao(observacao);
		baixa.setTipoBaixa(tipoBaixaService.get(tipoBaixaUid));

		repository.persist(baixa);

		clienteSaldoProducer.sendUpdateSaldoCliente(baixa.getCliente().getUid(), saldoARefletirNoCliente);

		return baixa;
	}

	@Transactional
	public void delete(UUID baixaUid, Integer version) {
		Baixa baixa = get(baixaUid, version);

		BigDecimal saldoARefletirNoCliente = baixa.getValor().negate();

		repository.delete(baixa);

		clienteSaldoProducer.sendUpdateSaldoCliente(baixa.getCliente().getUid(), saldoARefletirNoCliente);
	}
}
