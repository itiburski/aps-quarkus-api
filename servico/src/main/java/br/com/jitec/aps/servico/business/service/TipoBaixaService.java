package br.com.jitec.aps.servico.business.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.servico.data.model.TipoBaixa;
import br.com.jitec.aps.servico.data.repository.TipoBaixaRepository;

@ApplicationScoped
public class TipoBaixaService {

	@Inject
	TipoBaixaRepository repository;

	public TipoBaixa get(UUID tipoBaixaUid) {
		return repository.findByUid(tipoBaixaUid)
				.orElseThrow(() -> new DataNotFoundException("Tipo de Baixa não encontrado"));
	}

	public List<TipoBaixa> getAll() {
		return repository.list("order by nome");
	}

}
