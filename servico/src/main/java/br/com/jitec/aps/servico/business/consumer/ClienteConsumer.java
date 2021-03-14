package br.com.jitec.aps.servico.business.consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import br.com.jitec.aps.commons.business.data.ClienteResumidoDto;
import br.com.jitec.aps.servico.business.service.ClienteReplicaService;
import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
public class ClienteConsumer {

	private static final Logger LOG = Logger.getLogger(ClienteConsumer.class);

	private static final String TOPIC_CLIENTE_NOVO = "clienteNovo";
	private static final String TOPIC_CLIENTE_ATUALIZADO = "clienteAtualizado";
	private static final String MSG_LOG = "Cliente recebido %s";

	@Inject
	ClienteReplicaService service;

	@Incoming(TOPIC_CLIENTE_NOVO)
	@Blocking
	public void receiveClienteNovo(ClienteResumidoDto dto) {
		LOG.infof(MSG_LOG, dto);
		service.handleClienteNovo(dto);
	}

	@Incoming(TOPIC_CLIENTE_ATUALIZADO)
	@Blocking
	public void receiveClienteAtualizado(ClienteResumidoDto dto) {
		LOG.infof(MSG_LOG, dto);
		service.handleClienteAtualizado(dto);
	}

}
