package br.com.jitec.aps.cadastro.business.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.cadastro.data.model.TipoTelefone;
import br.com.jitec.aps.cadastro.data.repository.TipoTelefoneRepository;
import br.com.jitec.aps.commons.business.exception.DataNotFoundException;

@ApplicationScoped
public class TipoTelefoneService {

	@Inject
	TipoTelefoneRepository repository;

	public List<TipoTelefone> getAll() {
		return repository.list("order by descricao");
	}

	public TipoTelefone get(UUID tipoTelefoneUid) {
		return repository.findByUid(tipoTelefoneUid)
				.orElseThrow(() -> new DataNotFoundException("Tipo de telefone não encontrado"));
	}

	private TipoTelefone get(UUID tipoTelefoneUid, Integer version) {
		return repository.findByUidVersion(tipoTelefoneUid, version).orElseThrow(
				() -> new DataNotFoundException("Tipo de telefone não encontrado para versao especificada"));
	}

	@Transactional
	public TipoTelefone create(String descricao) {
		TipoTelefone tipoTelefone = new TipoTelefone(descricao);
		repository.persist(tipoTelefone);
		return tipoTelefone;
	}

	@Transactional
	public TipoTelefone update(UUID tipoTelefoneUid, Integer version, String descricao) {
		TipoTelefone tipoTelefone = get(tipoTelefoneUid, version);
		tipoTelefone.setDescricao(descricao);
		return tipoTelefone;
	}

	@Transactional
	public void delete(UUID tipoTelefoneUid, Integer version) {
		TipoTelefone tipoTelefone = get(tipoTelefoneUid, version);
		repository.delete(tipoTelefone);
	}
}
