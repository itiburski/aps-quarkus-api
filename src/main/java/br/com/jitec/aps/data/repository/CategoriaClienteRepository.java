package br.com.jitec.aps.data.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.data.model.CategoriaCliente;

@ApplicationScoped
public class CategoriaClienteRepository implements APSRepository<CategoriaCliente> {

	@Override
	public int customUpdate(CategoriaCliente categ) {
		return update("descricao = ?1 where id = ?2", categ.descricao, categ.id);
	}

}
