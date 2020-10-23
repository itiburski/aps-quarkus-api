package br.com.jitec.aps.data.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.data.model.Cliente;

@ApplicationScoped
public class ClienteRepository implements APSRepository<Cliente> {

	@Override
	public int customUpdate(Cliente entity) {
		throw new RuntimeException("should implement ClienteRepository.customUpdate");
	}

}
