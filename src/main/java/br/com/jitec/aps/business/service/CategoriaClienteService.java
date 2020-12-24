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
		return repository.list("order by descricao");
	}

	public CategoriaCliente get(UUID categoriaClienteUid) {
		return repository.findByUid(categoriaClienteUid)
				.orElseThrow(() -> new DataNotFoundException("Categoria de cliente não encontrada"));
	}

	@Transactional
	public CategoriaCliente create(String descricao) {
		CategoriaCliente categoria = new CategoriaCliente(descricao);
		repository.persist(categoria);
		return categoria;
	}

	@Transactional
	public CategoriaCliente update(UUID categoriaClienteUid, String descricao) {
		CategoriaCliente categoria = get(categoriaClienteUid);
		categoria.setDescricao(descricao);
		return categoria;
	}

	@Transactional
	public void delete(UUID categoriaClienteUid) {
		CategoriaCliente categoria = get(categoriaClienteUid);
		repository.delete(categoria);
	}
}
