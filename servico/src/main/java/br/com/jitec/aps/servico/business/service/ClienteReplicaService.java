package br.com.jitec.aps.servico.business.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import br.com.jitec.aps.commons.business.dto.ClienteResumidoDTO;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.repository.ClienteReplicaRepository;

@ApplicationScoped
public class ClienteReplicaService {

	private static final Logger LOG = Logger.getLogger(ClienteReplicaService.class);

	@Inject
	ClienteReplicaRepository repository;

	@Transactional
	public void handleClienteNovo(ClienteResumidoDTO dto) {
		ClienteReplica cliente = new ClienteReplica(dto.getUid(), dto.getAtivo());

		repository.persist(cliente);
		LOG.infof("Cliente cadastrado/replicado %s", dto);
	}

	@Transactional
	public void handleClienteAtualizado(ClienteResumidoDTO dto) {
		Optional<ClienteReplica> opCliente = repository.findByUid(dto.getUid());
		if (opCliente.isPresent()) {
			ClienteReplica cliente = opCliente.get();
			cliente.setAtivo(dto.getAtivo());

			repository.persist(cliente);
			LOG.infof("Cliente atualizado %s", dto);
		} else {
			LOG.warnf("Cliente nao encontrado %s", dto);
		}
	}

}
