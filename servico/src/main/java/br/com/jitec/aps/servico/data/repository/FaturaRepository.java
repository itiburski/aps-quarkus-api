package br.com.jitec.aps.servico.data.repository;

import java.math.BigInteger;

import javax.enterprise.context.ApplicationScoped;

import br.com.jitec.aps.commons.data.repository.APSRepository;
import br.com.jitec.aps.servico.data.model.Fatura;

@ApplicationScoped
public class FaturaRepository implements APSRepository<Fatura> {

	public Integer getNextCodigoFatura() {
		BigInteger codigo = (BigInteger) getEntityManager().createNativeQuery("SELECT nextval('FATURA_CODIGO')")
				.getSingleResult();
		return codigo.intValue();
	}

}
