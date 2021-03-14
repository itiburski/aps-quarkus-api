package br.com.jitec.aps.cadastro.business.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.commons.business.data.ClienteResumidoDto;

@ApplicationScoped
public class ClienteProducer {

	private static final Logger LOG = Logger.getLogger(ClienteProducer.class);

	private static final String TOPIC_CLIENTE_NOVO = "clienteNovo";
	private static final String TOPIC_CLIENTE_ATUALIZADO = "clienteAtualizado";
	private static final String MSG_LOG = "Cliente %s enviado para o topico %s";

	@Inject
	@Channel(TOPIC_CLIENTE_NOVO)
	Emitter<ClienteResumidoDto> clienteNovoEmitter;

	@Inject
	@Channel(TOPIC_CLIENTE_ATUALIZADO)
	Emitter<ClienteResumidoDto> clienteAtualizadoEmitter;

	public void sendClienteNovo(Cliente cliente) {
		ClienteResumidoDto dto = new ClienteResumidoDto(cliente.getUid(), cliente.getNome(), cliente.getAtivo());
		clienteNovoEmitter.send(dto);
		LOG.infof(MSG_LOG, dto.getUid(), TOPIC_CLIENTE_NOVO);
	}

	public void sendClienteAtualizado(Cliente cliente) {
		ClienteResumidoDto dto = new ClienteResumidoDto(cliente.getUid(), cliente.getNome(), cliente.getAtivo());
		clienteAtualizadoEmitter.send(dto);
		LOG.infof(MSG_LOG, dto.getUid(), TOPIC_CLIENTE_ATUALIZADO);
	}

}
