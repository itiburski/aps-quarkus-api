package br.com.jitec.aps.cadastro.business.consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import br.com.jitec.aps.cadastro.business.service.ClienteService;
import br.com.jitec.aps.commons.business.data.ClienteSaldoDto;
import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
public class ClienteSaldoConsumer {

	private static final Logger LOG = Logger.getLogger(ClienteSaldoConsumer.class);

	private static final String TOPIC_CLIENTE_SALDO_UPDATE = "clienteSaldoUpdate";
	private static final String MSG_LOG = "Saldo recebido para o cliente %s";

	@Inject
	ClienteService clienteService;

	@Incoming(TOPIC_CLIENTE_SALDO_UPDATE)
	@Blocking
	public void receiveClienteSaldoUpdate(ClienteSaldoDto dto) {
		LOG.infof(MSG_LOG, dto.getClienteUid());
		clienteService.handleSaldoUpdate(dto);
	}

}
