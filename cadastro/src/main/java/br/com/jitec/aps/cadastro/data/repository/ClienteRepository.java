package br.com.jitec.aps.cadastro.data.repository;

import java.math.BigInteger;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.commons.data.repository.APSRepository;

@ApplicationScoped
public class ClienteRepository implements APSRepository<Cliente> {

	public Integer getNextCodigoCliente() {
		BigInteger codigo = (BigInteger) getEntityManager().createNativeQuery("SELECT nextval('CLIENTE_CODIGO')").getSingleResult();
		return codigo.intValue();
	}

	public Long getQtdClientePorCategoria(Long categoriaClienteId) {
		return (Long) getEntityManager()
				.createQuery("select count(1) from Cliente where categoria.id = :categoriaClienteId")
				.setParameter("categoriaClienteId", categoriaClienteId).getSingleResult();
	}

	public Long getQtdClientePorCidade(Long cidadeId) {
		return (Long) getEntityManager().createQuery("select count(1) from Cliente where cidade.id = :cidadeId")
				.setParameter("cidadeId", cidadeId).getSingleResult();
	}

}
