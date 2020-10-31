package br.com.jitec.aps.data.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.data.model.Cliente;

@ApplicationScoped
public class ClienteRepository implements APSRepository<Cliente> {

	public Optional<Cliente> findByCodigo(Integer codigo) {
		return find("codigo", codigo).firstResultOptional();
	}

	public Integer getMaiorCodigoCliente() {
		return (Integer) getEntityManager().createQuery("select max(codigo) from Cliente").getSingleResult();
	}

}
