package br.com.jitec.aps.servico.business.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
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
	public void get_UsingExistentUid_ShouldReturnEntity() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");

		TipoBaixa tb1 = new TipoBaixa("cheque");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(tb1));

		TipoBaixa result = service.get(uid);

		Assertions.assertEquals("cheque", result.getNome());
	}

	@Test
	public void get_UsingUnexistentUid_ShouldThrowException() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Tipo de Baixa não encontrado", thrown.getMessage());
	}

	@Test
	public void shouldListAll() {
		TipoBaixa tb1 = new TipoBaixa("cheque");
		TipoBaixa tb2 = new TipoBaixa("dinheiro");
		List<TipoBaixa> tiposBaixa = Arrays.asList(tb1, tb2);
		Mockito.when(repositoryMock.list("order by nome")).thenReturn(tiposBaixa);

		List<TipoBaixa> result = service.getAll();

		Assertions.assertEquals(2, result.size());
	}
}
