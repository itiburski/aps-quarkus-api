package br.com.jitec.aps.servico.data.repository;

import java.math.BigInteger;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.commons.data.repository.APSRepository;
import br.com.jitec.aps.servico.data.model.OrdemServico;

@ApplicationScoped
public class OrdemServicoRepository implements APSRepository<OrdemServico> {

	public BigInteger getNextNumeroOS() {
		return (BigInteger) getEntityManager().createNativeQuery("SELECT nextval('ORDEM_SERVICO_NUMERO')")
				.getSingleResult();
	}

}
