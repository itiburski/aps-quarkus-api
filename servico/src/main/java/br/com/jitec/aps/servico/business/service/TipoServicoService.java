package br.com.jitec.aps.servico.business.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.jitec.aps.servico.data.model.TipoServico;
import br.com.jitec.aps.servico.data.repository.TipoServicoRepository;

@ApplicationScoped
public class TipoServicoService {

	@Inject
	TipoServicoRepository repository;

	public List<TipoServico> getAll() {
		return repository.list("order by nome");
	}

}
