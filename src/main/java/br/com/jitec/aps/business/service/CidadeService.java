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

	public Cidade get(UUID uid) {
		return repository.findByUid(uid).orElseThrow(() -> new DataNotFoundException("Cidade não encontrada"));
	}

	@Transactional
	public Cidade create(String nome, String uf) {
		Cidade cidade = new Cidade(nome, uf);
		repository.persist(cidade);
		return cidade;
	}

	@Transactional
	public Cidade update(UUID uid, String nome, String uf) {
		Cidade cidade = get(uid);
		cidade.setNome(nome);
		cidade.setUf(uf);
		return cidade;
	}

	@Transactional
	public void delete(UUID uid) {
		Cidade cidade = get(uid);
		repository.delete(cidade);
	}

}
