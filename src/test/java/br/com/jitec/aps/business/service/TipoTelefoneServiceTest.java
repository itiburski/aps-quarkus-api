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
import br.com.jitec.aps.data.model.TipoTelefone;
import br.com.jitec.aps.data.repository.TipoTelefoneRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class TipoTelefoneServiceTest {

	@Inject
	TipoTelefoneService service;

	@InjectMock
	TipoTelefoneRepository repositoryMock;

	@Test
	public void shouldListAll() {
		TipoTelefone tipo1 = new TipoTelefone("tipo1");
		TipoTelefone tipo2 = new TipoTelefone("tipo2");
		TipoTelefone tipo3 = new TipoTelefone("tipo3");
		List<TipoTelefone> tipos = Arrays.asList(tipo1, tipo2, tipo3);

		Mockito.when(repositoryMock.list("order by descricao")).thenReturn(tipos);

		List<TipoTelefone> all = service.getAll();
		Assertions.assertEquals(3, all.size());
	}

	@Test
	public void shouldGet() {
		TipoTelefone tipo = new TipoTelefone("tipoTelefone");
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		tipo.setUid(uid);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(tipo));

		TipoTelefone result = service.get(uid);

		Assertions.assertEquals("tipoTelefone", result.getDescricao());
		Assertions.assertEquals("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8", result.getUid().toString());
	}

	@Test
	public void shouldThrowExceptionWhenGetInexistentUid() {
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Tipo de telefone não encontrado", thrown.getMessage());
	}

	@Test
	public void shouldCreate() {
		TipoTelefone created = service.create("tipoTelefone");
		Assertions.assertEquals("tipoTelefone", created.getDescricao());
	}

	@Test
	public void shouldUpdate() {
		TipoTelefone tipoTelefone = new TipoTelefone("old-tipoTelefone");
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		tipoTelefone.setUid(uid);
		Integer version = 0;
		Mockito.when(repositoryMock.findByUidVersion(uid, version)).thenReturn(Optional.of(tipoTelefone));

		TipoTelefone result = service.update(uid, version, "new-tipoTelefone");

		Assertions.assertEquals("new-tipoTelefone", result.getDescricao());
		Assertions.assertEquals("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8", result.getUid().toString());
	}

	@Test
	public void shouldThrowExceptionWhenUpdateInexistentUid() {
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		Integer version = 0;

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> service.update(uid, version, "new-tipoTelefone"), "should have thrown DataNotFoundException");

		Assertions.assertEquals("Tipo de telefone não encontrado para versao especificada", thrown.getMessage());
	}

	@Test
	public void shouldDelete() {
		TipoTelefone tipoTelefone = new TipoTelefone("tipoTelefone");
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		tipoTelefone.setUid(uid);
		Integer version = 0;
		Mockito.when(repositoryMock.findByUidVersion(uid, version)).thenReturn(Optional.of(tipoTelefone));

		service.delete(uid, version);

		Mockito.verify(repositoryMock).delete(Mockito.any(TipoTelefone.class));
	}

	@Test
	public void shouldThrowExceptionWhenDeleteInexistentUid() {
		UUID uid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		Integer version = 0;

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.delete(uid, version),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Tipo de telefone não encontrado para versao especificada", thrown.getMessage());
	}

}
