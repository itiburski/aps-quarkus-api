package br.com.jitec.aps.data.repository;

import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.data.model.CategoriaCliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CategoriaClienteRepository
		implements APSRepository<CategoriaCliente>, PanacheRepository<CategoriaCliente> {

	public Optional<CategoriaCliente> findByUid(UUID uid) {
		return find("uid", uid).firstResultOptional();
	}

	@Override
	public int customUpdate(CategoriaCliente categ) {
		return update("descricao = ?1 where id = ?2", categ.descricao, categ.id);
	}

}
