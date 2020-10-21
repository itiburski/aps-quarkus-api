package br.com.jitec.aps.data.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.data.model.Cidade;

@ApplicationScoped
public class CidadeRepository implements APSRepository<Cidade> {

	@Override
	public int customUpdate(Cidade cidade) {
		return update("nome = ?1, uf = ?2 where id = ?3", cidade.nome, cidade.uf, cidade.id);
	}

}
