package br.com.jitec.aps.servico.business.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.jitec.aps.servico.data.model.TipoBaixa;
import br.com.jitec.aps.servico.data.repository.TipoBaixaRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class TipoBaixaServiceTest {

	@Inject
	TipoBaixaService service;

	@InjectMock
	TipoBaixaRepository repositoryMock;

	@Test
	public void shouldListAll() {
		TipoBaixa tb1 = new TipoBaixa("arte final");
		TipoBaixa tb2 = new TipoBaixa("impresso");
		List<TipoBaixa> tiposBaixa = Arrays.asList(tb1, tb2);
		Mockito.when(repositoryMock.list("order by nome")).thenReturn(tiposBaixa);

		List<TipoBaixa> result = service.getAll();

		Assertions.assertEquals(2, result.size());
	}
}
