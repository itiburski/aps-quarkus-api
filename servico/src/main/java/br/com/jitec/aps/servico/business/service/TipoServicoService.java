package br.com.jitec.aps.servico.business.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.servico.data.model.TipoServico;
import br.com.jitec.aps.servico.data.repository.TipoServicoRepository;

@ApplicationScoped
public class TipoServicoService {

	@Inject
	TipoServicoRepository repository;

	public TipoServico get(UUID tipoServicoUid) {
		return repository.findByUid(tipoServicoUid)
				.orElseThrow(() -> new DataNotFoundException("Tipo de Serviço não encontrado"));
	}

	public List<TipoServico> getAll() {
		return repository.list("order by nome");
	}

}
