package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.data.repository.BaixaRepository;

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
