package br.com.jitec.aps.business.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.business.exception.DataNotFoundException;
import br.com.jitec.aps.data.model.Cidade;
import br.com.jitec.aps.data.repository.CidadeRepository;

@ApplicationScoped
public class CidadeService {

	@Inject
	CidadeRepository repository;

	public List<Cidade> getAll() {
		return repository.list("order by nome");
	}

	public Cidade get(UUID cidadeUid) {
		return repository.findByUid(cidadeUid).orElseThrow(() -> new DataNotFoundException("Cidade não encontrada"));
	}

	@Transactional
	public Cidade create(String nome, String uf) {
		Cidade cidade = new Cidade(nome, uf);
		repository.persist(cidade);
		return cidade;
	}

	@Transactional
	public Cidade update(UUID cidadeUid, String nome, String uf) {
		Cidade cidade = get(cidadeUid);
		cidade.setNome(nome);
		cidade.setUf(uf);
		return cidade;
	}

	@Transactional
	public void delete(UUID cidadeUid) {
		Cidade cidade = get(cidadeUid);
		repository.delete(cidade);
	}

}
