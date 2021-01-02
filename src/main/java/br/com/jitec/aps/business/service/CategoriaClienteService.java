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

	private CategoriaCliente get(UUID categoriaClienteUid, Integer version) {
		return repository.findByUidVersion(categoriaClienteUid, version).orElseThrow(
				() -> new DataNotFoundException("Categoria de cliente não encontrada para versao especificada"));
	}

	@Transactional
	public CategoriaCliente create(String descricao) {
		CategoriaCliente categoria = new CategoriaCliente(descricao);
		repository.persist(categoria);
		return categoria;
	}

	@Transactional
	public CategoriaCliente update(UUID categoriaClienteUid, Integer version, String descricao) {
		CategoriaCliente categoria = get(categoriaClienteUid, version);
		categoria.setDescricao(descricao);
		return categoria;
	}

	@Transactional
	public void delete(UUID categoriaClienteUid, Integer version) {
		CategoriaCliente categoria = get(categoriaClienteUid, version);
		repository.delete(categoria);
	}
}
