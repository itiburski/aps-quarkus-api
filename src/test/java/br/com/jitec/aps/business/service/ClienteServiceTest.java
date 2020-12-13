package br.com.jitec.aps.business.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.jitec.aps.business.exception.DataNotFoundException;
import br.com.jitec.aps.business.exception.InvalidDataException;
import br.com.jitec.aps.business.wrapper.Paged;
import br.com.jitec.aps.data.model.CategoriaCliente;
import br.com.jitec.aps.data.model.Cidade;
import br.com.jitec.aps.data.model.Cliente;
import br.com.jitec.aps.data.repository.ClienteRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class ClienteServiceTest {

	private static final Integer PAGE = 1;
	private static final Integer SIZE = 10;

	@Inject
	ClienteService clienteService;

	@InjectMock
	ClienteRepository repositoryMock;

	@InjectMock
	CidadeService cidadeServiceMock;

	@InjectMock
	CategoriaClienteService categClienteServiceMock;

	@Test
	public void getClientes_WhenUsingNullFilters_ShouldListAll() {
		Map<String, Object> params = new LinkedHashMap<>();

		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		Cliente cliente1 = getCliente(uid1, 123, "Cliente 1", "Contato 1");
		Cliente cliente2 = getCliente(uid2, 456, "Cliente 2", "Contato 2");

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		String query = "order by id";

		PanacheQuery<Cliente> panacheQuery = mockListPanacheQuery(clientes);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<Cliente> result = clienteService.getClientes(PAGE, SIZE, null, null, null, null);

		Assertions.assertEquals(2, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);
	}

	@Test
	public void getClientes_WhenUsingSomeFilters_ShouldListUsingFilter() {
		int codigo = 123;
		String nomeOuRazaoSocial = "cliente";
		Boolean ativo = Boolean.TRUE;

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("codigo", codigo);
		params.put("nomeOuRazaoSocial", "%" + nomeOuRazaoSocial.toUpperCase() + "%");
		params.put("ativo", ativo);

		String query = "codigo = :codigo and ativo = :ativo and (upper(nome) like :nomeOuRazaoSocial OR upper(razaoSocial) like :nomeOuRazaoSocial) order by id";

		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente1 = getClienteAtivo(uid1, codigo, "Cliente 1", "Contato 1");

		List<Cliente> clientes = Arrays.asList(cliente1);
		PanacheQuery<Cliente> panacheQuery = mockListPanacheQuery(clientes);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<Cliente> result = clienteService.getClientes(PAGE, SIZE, codigo, nomeOuRazaoSocial, ativo, null);

		Assertions.assertEquals(1, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);
	}

	@Test
	public void getClientes_WhenOrderIsInformed_ShouldConsiderFieldInOrderBy() {
		Map<String, Object> params = new LinkedHashMap<>();

		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		Cliente cliente1 = getCliente(uid1, 123, "Cliente 1", "Contato 1");
		Cliente cliente2 = getCliente(uid2, 456, "Cliente 2", "Contato 2");

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		String query = "order by nome";
		PanacheQuery<Cliente> panacheQuery = mockListPanacheQuery(clientes);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<Cliente> result = clienteService.getClientes(PAGE, SIZE, null, null, null, "nome");

		Assertions.assertEquals(2, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);
	}

	@Test
	public void getClientes_WhenOrderIsInvalid_ShouldThrowException() {
		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> clienteService.getClientes(PAGE, SIZE, null, null, null, "campoInexistente"),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Campo para ordenação inválido", thrown.getMessage());
	}

	@Test
	public void shouldGet() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		Cliente result = clienteService.get(uid);

		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
		Assertions.assertEquals("Cliente", result.getNome());
		Assertions.assertEquals("Contato", result.getContato());
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
	public void getComplete_WhenValidUid_ShouldReturnCompleteCliente() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");

		PanacheQuery<Cliente> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(repositoryMock.find(Mockito.anyString(), Mockito.any(Parameters.class))).thenReturn(query);
		Mockito.when(query.singleResultOptional()).thenReturn(Optional.of(cliente));

		Cliente result = clienteService.getComplete(uid);

		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
		Assertions.assertEquals("Cliente", result.getNome());
	}

	@Test
	public void getComplete_WhenInexistentUid_ShouldThrowException() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");

		PanacheQuery<Cliente> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(repositoryMock.find(Mockito.anyString(), Mockito.any(Parameters.class))).thenReturn(query);
		Mockito.when(query.singleResultOptional()).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> clienteService.getComplete(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Cliente não encontrado"));
	}

	@Test
	public void create_WhenCodigoInformed_ShouldCreateUsingCodigo() {
		Cliente created = clienteService.create(123, "Nome", "razaoSocial", "contato", "rua",
				"complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"));

		Assertions.assertEquals("Nome", created.getNome());
		Assertions.assertEquals("contato", created.getContato());
		Assertions.assertEquals(123, created.getCodigo());
		Assertions.assertTrue(created.getAtivo());
	}

	@Test
	public void create_WhenCodigoNotInformed_ShouldCreateWithNextCodigoAvailable() {
		Mockito.when(repositoryMock.getMaiorCodigoCliente()).thenReturn(25);

		Cliente created = clienteService.create(null, "Nome", "razaoSocial", "contato", "rua",
				"complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"));

		Assertions.assertEquals("Nome", created.getNome());
		Assertions.assertEquals("contato", created.getContato());
		Assertions.assertEquals(26, created.getCodigo());
		Assertions.assertTrue(created.getAtivo());
	}

	@Test
	public void create_WhenFieldsNull_ShouldCreateWithNullValues() {
		Mockito.when(repositoryMock.getMaiorCodigoCliente()).thenReturn(25);

		Cliente created = clienteService.create(null, "Nome", "razaoSocial", "contato", "rua", "complemento", "bairro",
				"cep", "homepage", "cnpj", "inscricaEstadual", null, null);

		Assertions.assertEquals("Nome", created.getNome());
		Assertions.assertEquals("contato", created.getContato());
		Assertions.assertEquals(26, created.getCodigo());
		Assertions.assertTrue(created.getAtivo());
		Assertions.assertNull(created.getCategoria());
		Assertions.assertNull(created.getCidade());
	}

	@Test
	public void create_WhenCodigoZeroInformed_ShouldThrowException() {
		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> clienteService.create(0, "Nome", "razaoSocial", "contato", "rua", "complemento",
						"bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
						UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
						UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("O código deve ser maior que 0", thrown.getMessage());
	}

	@Test
	public void create_WhenCreateWithCodigoAlreadyAssigned_ShouldThrowException() {
		Mockito.when(repositoryMock.findByCodigo(Mockito.anyInt())).thenReturn(Optional.of(new Cliente()));

		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> clienteService.create(15, "Nome", "razaoSocial", "contato", "rua", "complemento",
						"bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
						UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
						UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Já existe um cliente associado ao código informado", thrown.getMessage());
	}

	@Test
	public void updateAll_WhenAllParametersInformed_ShouldUpdateAllFields() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		Cliente result = clienteService.updateAll(uid, "Nome-updated", "razaoSocial", "contato-updated", Boolean.FALSE,
				"rua", "complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"));

		Assertions.assertEquals("Nome-updated", result.getNome());
		Assertions.assertEquals("contato-updated", result.getContato());
		Assertions.assertFalse(result.getAtivo());
		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
	}

	@Test
	public void updateAll_WhenSomeParameterNull_ShouldUpdateFieldToNull() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		Cliente result = clienteService.updateAll(uid, "Nome-updated", "razaoSocial", null, null, "rua", "complemento",
				"bairro", "cep", "homepage", "cnpj", "inscricaEstadual", null, null);

		Assertions.assertEquals("Nome-updated", result.getNome());
		Assertions.assertNull(result.getContato());
		Assertions.assertFalse(result.getAtivo());
		Assertions.assertNull(result.getCategoria());
		Assertions.assertNull(result.getCidade());
		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
	}

	@Test
	public void updateAll_WhenUpdateInexistentUid_ShouldThrowException() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> clienteService.updateAll(uid, "Nome-updated", "razaoSocial", "contato-updated", Boolean.TRUE, "rua",
						"complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
						UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
						UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")),
				"should have thrown DataNotFoundException");

		Assertions.assertTrue(thrown.getMessage().contains("Cliente não encontrado"));
	}

	@Test
	public void updateNotNull_WhenValuesNull_ShouldNotUpdateFields() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getClienteWithDefaultValues(uid, 123);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		Cliente result = clienteService.updateNotNull(uid, null, null, null, null, null, null, null, null, null, null,
				null, null, null);

		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
		Assertions.assertEquals("nome", result.getNome());
		Assertions.assertEquals("contato", result.getContato());
		Assertions.assertEquals("88111222", result.getCep());
		Assertions.assertTrue(result.getAtivo());
		Assertions.assertEquals("razaoSocial", result.getRazaoSocial());
		Assertions.assertEquals("rua", result.getRua());
		Assertions.assertEquals("complemento", result.getComplemento());
		Assertions.assertEquals("bairro", result.getBairro());
		Assertions.assertEquals("homepage", result.getHomepage());
		Assertions.assertEquals("cnpj", result.getCnpj());
	}

	@Test
	public void updateNotNull_WhenValuesInformed_ShouldUpdateFields() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getClienteWithDefaultValues(uid, 123);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(cliente));

		Mockito.when(categClienteServiceMock.get(Mockito.any(UUID.class))).thenReturn(new CategoriaCliente("categoria-updated"));
		Mockito.when(cidadeServiceMock.get(Mockito.any(UUID.class))).thenReturn(new Cidade("cidade-alterada", "FF"));

		Cliente result = clienteService.updateNotNull(uid, "nome-updated", "razaoSocial-updated", "contato-updated",
				Boolean.FALSE, "rua-updated", "complemento-updated", "bairro-updated", "cep-updated",
				"homepage-updated", "cnpj-updated", "inscricaoEstadual-updated",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"));

		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
		Assertions.assertEquals("nome-updated", result.getNome());
		Assertions.assertEquals("contato-updated", result.getContato());
		Assertions.assertEquals("cep-updated", result.getCep());
		Assertions.assertFalse(result.getAtivo());
		Assertions.assertEquals("razaoSocial-updated", result.getRazaoSocial());
		Assertions.assertEquals("rua-updated", result.getRua());
		Assertions.assertEquals("complemento-updated", result.getComplemento());
		Assertions.assertEquals("bairro-updated", result.getBairro());
		Assertions.assertEquals("homepage-updated", result.getHomepage());
		Assertions.assertEquals("cnpj-updated", result.getCnpj());
		Assertions.assertEquals("categoria-updated", cliente.getCategoria().getDescricao());
		Assertions.assertEquals("cidade-alterada", cliente.getCidade().getNome());
	}

	@Test
	public void shouldDelete() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");
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

	private Cliente getCliente(UUID uid, Integer codigo, String nome, String contato) {
		Cliente cliente = new Cliente();
		cliente.setUid(uid);
		cliente.setCodigo(codigo);
		cliente.setNome(nome);
		cliente.setContato(contato);
		return cliente;
	}

	private Cliente getClienteAtivo(UUID uid, Integer codigo, String nome, String contato) {
		Cliente cliente = getCliente(uid, codigo, nome, contato);
		cliente.setAtivo(Boolean.TRUE);
		return cliente;
	}

	private Cliente getClienteWithDefaultValues(UUID uid, Integer codigo) {
		Cliente cliente = new Cliente();
		cliente.setUid(uid);
		cliente.setCodigo(codigo);
		cliente.setNome("nome");
		cliente.setContato("contato");
		cliente.setRazaoSocial("razaoSocial");
		cliente.setRua("rua");
		cliente.setComplemento("complemento");
		cliente.setBairro("bairro");
		cliente.setCep("88111222");
		cliente.setHomepage("homepage");
		cliente.setCnpj("cnpj");
		cliente.setInscricaoEstadual("inscricaoEstadual");
		cliente.setCidade(new Cidade("nome", "uf"));
		cliente.setCategoria(new CategoriaCliente("categoria"));
		cliente.setAtivo(Boolean.TRUE);
		cliente.setSaldo(BigDecimal.ZERO);

		return cliente;
	}

	@SuppressWarnings("unchecked")
	private PanacheQuery<Cliente> mockListPanacheQuery(List<Cliente> clientes) {
		PanacheQuery<Cliente> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(clientes);
		return query;
	}

}
