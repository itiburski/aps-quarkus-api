package br.com.jitec.aps.servico.business.service;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.jitec.aps.commons.business.data.ClienteResumidoDto;
import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.repository.ClienteReplicaRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class ClienteReplicaServiceTest {

	@Inject
	ClienteReplicaService service;

	@InjectMock
	ClienteReplicaRepository repositoryMock;

	@Test
	public void get_UsingExistentUid_ShouldReturnEntity() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");

		ClienteReplica cliente = new ClienteReplica();
		cliente.setUid(uid);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		ClienteReplica result = service.get(uid);

		Assertions.assertEquals(uid, result.getUid());
	}

	@Test
	public void get_UsingUnexistentUid_ShouldThrowException() {
		UUID uid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Cliente não encontrado", thrown.getMessage());
	}

	@Test
	public void handleClienteNovo_ShouldWork() {
		ClienteResumidoDto dto = new ClienteResumidoDto(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"), "nome",
				Boolean.TRUE);
		service.handleClienteNovo(dto);

		Mockito.verify(repositoryMock).persist(Mockito.any(ClienteReplica.class));
	}

	@Test
	public void handleClienteAtualizado_WhenExist_ShouldUpdate() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		ClienteResumidoDto dto = new ClienteResumidoDto(uid, "nome", Boolean.TRUE);

		ClienteReplica cliente = new ClienteReplica(uid, "nome", Boolean.TRUE);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		service.handleClienteAtualizado(dto);

		Mockito.verify(repositoryMock).persist(Mockito.any(ClienteReplica.class));
	}

	@Test
	public void handleClienteAtualizado_WhenDoesNotExist_ShouldSkipPersist() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		ClienteResumidoDto dto = new ClienteResumidoDto(uid, "nome", Boolean.TRUE);

		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		service.handleClienteAtualizado(dto);

		Mockito.verify(repositoryMock, Mockito.never()).persist(Mockito.any(ClienteReplica.class));
	}

}
