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
import br.com.jitec.aps.data.model.Cliente;
import br.com.jitec.aps.data.repository.ClienteRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class ClienteServiceTest {

	@Inject
	ClienteService clienteService;

	@InjectMock
	ClienteRepository repositoryMock;

	@InjectMock
	CidadeService cidadeServiceMock;

	@InjectMock
	CategoriaClienteService categClienteServiceMock;

	@Test
	public void shouldListAll() {
		Cliente cliente1 = getCliente(123, "Cliente 1", "Contato 1");
		Cliente cliente2 = getCliente(456, "Cliente 2", "Contato 2");

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		Mockito.when(repositoryMock.listAll()).thenReturn(clientes);

		List<Cliente> result = clienteService.getAll();

		Assertions.assertEquals(2, result.size());
	}

	@Test
	public void shouldGet() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(123, "Cliente", "Contato");
		cliente.uid = uid;
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		Cliente result = clienteService.get(uid);

		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.uid.toString());
		Assertions.assertEquals("Cliente", result.nome);
		Assertions.assertEquals("Contato", result.contato);
	}

	@Test
	public void shouldThrowExceptionWhenGetInexistentUid() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> clienteService.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Cliente não encontrado"));
	}

	@Test
	public void shouldCreate() {
		Cliente created = clienteService.create(123, "Nome", "razaoSocial", "contato", Boolean.TRUE, "rua",
				"complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"));

		Assertions.assertEquals("Nome", created.nome);
		Assertions.assertEquals("contato", created.contato);
		Assertions.assertTrue(created.ativo);
	}

	@Test
	public void shouldDelete() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(123, "Cliente", "Contato");
		cliente.uid = uid;
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		clienteService.delete(uid);

		Mockito.verify(repositoryMock).delete(Mockito.any(Cliente.class));
	}

	@Test
	public void shouldThrowExceptionWhenDeleteInexistentUid() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> clienteService.delete(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Cliente não encontrado"));
	}

	private Cliente getCliente(Integer codigo, String nome, String contato) {
		Cliente cliente = new Cliente();
		cliente.codigo = codigo;
		cliente.nome = nome;
		cliente.contato = contato;
		return cliente;

	}

}
