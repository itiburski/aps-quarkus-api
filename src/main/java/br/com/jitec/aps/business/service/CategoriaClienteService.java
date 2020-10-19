package br.com.jitec.aps.business.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.business.exception.DataNotFoundException;
import br.com.jitec.aps.data.model.CategoriaCliente;
import br.com.jitec.aps.data.repository.CategoriaClienteRepository;

@ApplicationScoped
public class CategoriaClienteService {

	@Inject
	CategoriaClienteRepository repository;

	public List<CategoriaCliente> getAll() {
		return repository.listAll();
	}

	public CategoriaCliente get(UUID uid) {
		return repository.findByUid(uid)
				.orElseThrow(() -> new DataNotFoundException("Categoria de cliente não encontrada"));
	}

	@Transactional
	public CategoriaCliente create(String descricao) {
		CategoriaCliente categ = new CategoriaCliente(descricao);
		repository.persist(categ);
		return categ;
	}

	@Transactional
	public CategoriaCliente update(UUID uid, String descricao) {
		CategoriaCliente categ = get(uid);
		categ.descricao = descricao;
		repository.customUpdate(categ);
		return categ;
	}

	@Transactional
	public void delete(UUID uid) {
		CategoriaCliente categ = get(uid);
		repository.delete(categ);
	}
}
