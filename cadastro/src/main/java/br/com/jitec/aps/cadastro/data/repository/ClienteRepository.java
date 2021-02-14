package br.com.jitec.aps.cadastro.data.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.cadastro.data.model.Cliente;

@ApplicationScoped
public class ClienteRepository implements APSRepository<Cliente> {

	public Optional<Cliente> findByCodigo(Integer codigo) {
		return find("codigo", codigo).firstResultOptional();
	}

	public Optional<Integer> getMaiorCodigoCliente() {
		return Optional.ofNullable((Integer) getEntityManager().createQuery("select max(codigo) from Cliente").getSingleResult());
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
