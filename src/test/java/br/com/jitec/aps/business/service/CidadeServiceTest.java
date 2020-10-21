package br.com.jitec.aps.business.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.jitec.aps.business.exception.DataNotFoundException;
import br.com.jitec.aps.data.model.Cidade;
import br.com.jitec.aps.data.repository.CidadeRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class CidadeServiceTest {

	@Inject
	CidadeService service;

	@InjectMock
	CidadeRepository repositoryMock;

	@Test
	public void shouldListAll() {
		Cidade cidade1 = new Cidade("City1", "UF");
		Cidade cidade2 = new Cidade("City2", "UF");
		List<Cidade> cidades = Arrays.asList(cidade1, cidade2);
		Mockito.when(repositoryMock.listAll()).thenReturn(cidades);

		List<Cidade> result = service.getAll();

		Assertions.assertEquals(2, result.size());
	}

	@Test
	public void shouldGet() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Cidade cidade1 = new Cidade("City1", "UF");
		cidade1.uid = uid;
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cidade1));

		Cidade result = service.get(uid);

		Assertions.assertEquals("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da", result.uid.toString());
		Assertions.assertEquals("City1", result.nome);
		Assertions.assertEquals("UF", result.uf);
	}

	@Test
	public void shouldThrowExceptionWhenGetInexistentUid() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Cidade não encontrada"));
	}

	@Test
	public void shouldCreate() {
		Cidade created = service.create("City", "UF");
		Assertions.assertEquals("City", created.nome);
		Assertions.assertEquals("UF", created.uf);
	}

	@Test
	public void shouldUpdate() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Cidade cidade = new Cidade("old-city", "UF");
		cidade.uid = uid;
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cidade));

		Cidade result = service.update(uid, "new-city", "UF");

		Assertions.assertEquals("new-city", result.nome);
		Assertions.assertEquals("UF", result.uf);
		Assertions.assertEquals("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da", result.uid.toString());
	}

	@Test
	public void shouldThrowExceptionWhenUpdateInexistentUid() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> service.update(uid, "new-city", "UF"), "should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Cidade não encontrada"));
	}

	@Test
	public void shouldDelete() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Cidade cidade = new Cidade("old-city", "UF");
		cidade.uid = uid;
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cidade));

		service.delete(uid);

		Mockito.verify(repositoryMock).delete(Mockito.any(Cidade.class));
	}

	@Test
	public void shouldThrowExceptionWhenDeleteInexistentUid() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.delete(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Cidade não encontrada"));
	}

}
