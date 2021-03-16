package br.com.jitec.aps.servico.business.producer;

import java.math.BigDecimal;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import br.com.jitec.aps.commons.business.data.ClienteSaldoDto;

@ApplicationScoped
public class ClienteSaldoProducer {

	private static final Logger LOG = Logger.getLogger(ClienteSaldoProducer.class);

	private static final String TOPIC_CLIENTE_SALDO_UPDATE = "clienteSaldoUpdate";
	private static final String MSG_LOG = "Variação de saldo de %s do cliente %s enviado para o topico %s";

	@Inject
	@Channel(TOPIC_CLIENTE_SALDO_UPDATE)
	Emitter<ClienteSaldoDto> clienteUpdateSaldoEmitter;

	public void sendUpdateSaldoCliente(UUID clienteUid, BigDecimal variacaoSaldo) {
		ClienteSaldoDto dto = new ClienteSaldoDto(clienteUid, variacaoSaldo);
		clienteUpdateSaldoEmitter.send(dto);
		LOG.infof(MSG_LOG, variacaoSaldo, clienteUid, TOPIC_CLIENTE_SALDO_UPDATE);
	}

}
