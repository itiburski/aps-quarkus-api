package br.com.jitec.aps.business.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.business.exception.DataNotFoundException;
import br.com.jitec.aps.data.model.TipoTelefone;
import br.com.jitec.aps.data.repository.TipoTelefoneRepository;

@ApplicationScoped
public class TipoTelefoneService {

	@Inject
	TipoTelefoneRepository repository;

	public List<TipoTelefone> getAll() {
		return repository.list("order by descricao");
	}

	public TipoTelefone get(UUID uid) {
		return repository.findByUid(uid)
				.orElseThrow(() -> new DataNotFoundException("Tipo de telefone não encontrado"));
	}

	@Transactional
	public TipoTelefone create(String descricao) {
		TipoTelefone tipoTelefone = new TipoTelefone(descricao);
		repository.persist(tipoTelefone);
		return tipoTelefone;
	}

	@Transactional
	public TipoTelefone update(UUID uid, String descricao) {
		TipoTelefone tipoTelefone = get(uid);
		tipoTelefone.setDescricao(descricao);
		return tipoTelefone;
	}

	@Transactional
	public void delete(UUID uid) {
		TipoTelefone tipoTelefone = get(uid);
		repository.delete(tipoTelefone);
	}
}
