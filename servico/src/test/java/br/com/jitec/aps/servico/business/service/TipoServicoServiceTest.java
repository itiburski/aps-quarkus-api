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
import br.com.jitec.aps.servico.data.model.TipoServico;
import br.com.jitec.aps.servico.data.repository.TipoServicoRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class TipoServicoServiceTest {

	@Inject
	TipoServicoService service;

	@InjectMock
	TipoServicoRepository repositoryMock;

	@Test
	public void shouldListAll() {
		TipoServico ts1 = new TipoServico("arte final");
		TipoServico ts2 = new TipoServico("impresso");
		List<TipoServico> tiposServico = Arrays.asList(ts1, ts2);
		Mockito.when(repositoryMock.list("order by nome")).thenReturn(tiposServico);

		List<TipoServico> result = service.getAll();

		Assertions.assertEquals(2, result.size());
	}

	@Test
	public void get_UsingExistentUid_ShouldReturnEntity() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");

		TipoServico ts1 = new TipoServico("arte final");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(ts1));

		TipoServico result = service.get(uid);

		Assertions.assertEquals("arte final", result.getNome());
	}

	@Test
	public void get_UsingUnexistentUid_ShouldThrowException() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Tipo de Serviço não encontrado", thrown.getMessage());
	}

}
