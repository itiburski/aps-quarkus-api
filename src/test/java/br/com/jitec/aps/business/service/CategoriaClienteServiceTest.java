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
import br.com.jitec.aps.data.model.CategoriaCliente;
import br.com.jitec.aps.data.repository.CategoriaClienteRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class CategoriaClienteServiceTest {

	@Inject
	CategoriaClienteService service;

	@InjectMock
	CategoriaClienteRepository repositoryMock;

	@Test
	public void shouldListAll() {
		CategoriaCliente categ1 = new CategoriaCliente("category1");
		CategoriaCliente categ2 = new CategoriaCliente("category2");
		CategoriaCliente categ3 = new CategoriaCliente("category3");
		List<CategoriaCliente> categorias = Arrays.asList(categ1, categ2, categ3);

		Mockito.when(repositoryMock.list("order by descricao")).thenReturn(categorias);

		List<CategoriaCliente> all = service.getAll();
		Assertions.assertEquals(3, all.size());
	}

	@Test
	public void shouldGet() {
		CategoriaCliente categoria = new CategoriaCliente("category");
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		categoria.setUid(uid);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(categoria));

		CategoriaCliente result = service.get(uid);

		Assertions.assertEquals("category", result.getDescricao());
		Assertions.assertEquals("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8", result.getUid().toString());
	}

	@Test
	public void shouldThrowExceptionWhenGetInexistentUid() {
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Categoria de cliente não encontrada"));
	}

	@Test
	public void shouldCreate() {
		CategoriaCliente created = service.create("category");
		Assertions.assertEquals("category", created.getDescricao());
	}

	@Test
	public void shouldUpdate() {
		CategoriaCliente categoria = new CategoriaCliente("old-category");
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		categoria.setUid(uid);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(categoria));

		CategoriaCliente result = service.update(uid, "new-category");

		Assertions.assertEquals("new-category", result.getDescricao());
		Assertions.assertEquals("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8", result.getUid().toString());
	}

	@Test
	public void shouldThrowExceptionWhenUpdateInexistentUid() {
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> service.update(uid, "new-category"), "should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Categoria de cliente não encontrada"));
	}

	@Test
	public void shouldDelete() {
		CategoriaCliente categoria = new CategoriaCliente("category");
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		categoria.setUid(uid);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(categoria));

		service.delete(uid);

		Mockito.verify(repositoryMock).delete(Mockito.any(CategoriaCliente.class));
	}

	@Test
	public void shouldThrowExceptionWhenDeleteInexistentUid() {
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.delete(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Categoria de cliente não encontrada"));
	}

}
