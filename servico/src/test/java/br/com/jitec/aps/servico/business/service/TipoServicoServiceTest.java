package br.com.jitec.aps.servico.business.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}
