package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.commons.business.util.QueryBuilder;
import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.data.repository.BaixaRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class BaixaService {

	@Inject
	ClienteReplicaService clienteService;

	@Inject
	TipoBaixaService tipoBaixaService;

	@Inject
	BaixaRepository repository;

	@Inject
	ClienteSaldoProducer clienteSaldoProducer;

	public Paged<Baixa> getAll(Pagination pagination) {
		QueryBuilder builder = new QueryBuilder();
		builder.setSortBy("id");

		PanacheQuery<Baixa> query = repository.find(builder.getQuery(), builder.getParams())
				.page(Page.of(pagination.getPageZeroBased(), pagination.getSize()));
		return new Paged<Baixa>(query.list(), query.pageCount(), query.count());
	}

	public Baixa get(UUID baixaUid) {
		return repository.findByUid(baixaUid).orElseThrow(() -> new DataNotFoundException("Baixa não encontrada"));
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

}
