package br.com.jitec.aps.cadastro.business.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import br.com.jitec.aps.cadastro.business.data.ClienteEmailDTO;
import br.com.jitec.aps.cadastro.business.data.ClienteTelefoneDTO;
import br.com.jitec.aps.cadastro.business.exception.DataNotFoundException;
import br.com.jitec.aps.cadastro.business.exception.InvalidDataException;
import br.com.jitec.aps.cadastro.business.wrapper.Paged;
import br.com.jitec.aps.cadastro.data.model.CategoriaCliente;
import br.com.jitec.aps.cadastro.data.model.Cidade;
import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.cadastro.data.model.ClienteEmail;
import br.com.jitec.aps.cadastro.data.model.ClienteTelefone;
import br.com.jitec.aps.cadastro.data.model.TipoTelefone;
import br.com.jitec.aps.cadastro.data.repository.ClienteRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
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

	@InjectMock
	TipoTelefoneService tipoTelefoneServiceMock;

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
		mockFindSingleResultOptional(cliente);

		Cliente result = clienteService.getComplete(uid);

		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
		Assertions.assertEquals("Cliente", result.getNome());
	}

	@Test
	public void getComplete_WhenInexistentUid_ShouldThrowException() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		mockFindSingleResultOptionalEmpty();

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> clienteService.getComplete(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Cliente não encontrado", thrown.getMessage());
	}

	@Test
	public void create_WhenCodigoInformed_ShouldCreateUsingCodigo() {
		List<ClienteEmailDTO> emails = new ArrayList<>();
		emails.add(ClienteEmailDTO.builder().withEmail("email@email.com").build());

		List<ClienteTelefoneDTO> telefones = new ArrayList<>();
		UUID tipoTelefoneUid = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		telefones.add(ClienteTelefoneDTO.builder().withNumero(111222333).withTipoTelefoneUid(tipoTelefoneUid).build());

		Mockito.when(tipoTelefoneServiceMock.get(Mockito.any(UUID.class))).thenReturn(new TipoTelefone("mock"));

		Cliente created = clienteService.create(123, "Nome", "razaoSocial", "contato", "rua", "complemento", "bairro",
				"cep", "homepage", "cnpj", "inscricaEstadual", UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), emails, telefones);

		Assertions.assertEquals("Nome", created.getNome());
		Assertions.assertEquals("contato", created.getContato());
		Assertions.assertEquals(123, created.getCodigo());
		Assertions.assertTrue(created.getAtivo());
		Assertions.assertEquals(1, created.getEmails().size());
		Assertions.assertEquals("email@email.com", created.getEmails().get(0).getEmail());
		Assertions.assertEquals(1, created.getTelefones().size());
		Assertions.assertEquals(111222333, created.getTelefones().get(0).getNumero());
		Assertions.assertEquals("mock", created.getTelefones().get(0).getTipoTelefone().getDescricao());
	}

	@Test
	public void create_WhenCodigoNotInformed_ShouldCreateWithNextCodigoAvailable() {
		Mockito.when(repositoryMock.getMaiorCodigoCliente()).thenReturn(25);

		List<ClienteEmailDTO> emails = new ArrayList<>();
		List<ClienteTelefoneDTO> telefones = new ArrayList<>();
		telefones.add(ClienteTelefoneDTO.builder().withNumero(111222333).build());

		Cliente created = clienteService.create(null, "Nome", "razaoSocial", "contato", "rua", "complemento", "bairro",
				"cep", "homepage", "cnpj", "inscricaEstadual", UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), emails, telefones);

		Assertions.assertEquals("Nome", created.getNome());
		Assertions.assertEquals("contato", created.getContato());
		Assertions.assertEquals(26, created.getCodigo());
		Assertions.assertTrue(created.getAtivo());
	}

	@Test
	public void create_WhenFieldsNull_ShouldCreateWithNullValues() {
		Mockito.when(repositoryMock.getMaiorCodigoCliente()).thenReturn(25);

		List<ClienteEmailDTO> emails = new ArrayList<>();
		List<ClienteTelefoneDTO> telefones = new ArrayList<>();

		Cliente created = clienteService.create(null, "Nome", "razaoSocial", "contato", "rua", "complemento", "bairro",
				"cep", "homepage", "cnpj", "inscricaEstadual", null, null, emails, telefones);

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
				() -> clienteService.create(0, "Nome", "razaoSocial", "contato", "rua", "complemento", "bairro", "cep",
						"homepage", "cnpj", "inscricaEstadual", UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
						UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), null, null),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("O código deve ser maior que 0", thrown.getMessage());
	}

	@Test
	public void create_WhenCreateWithCodigoAlreadyAssigned_ShouldThrowException() {
		Mockito.when(repositoryMock.findByCodigo(Mockito.anyInt())).thenReturn(Optional.of(new Cliente()));

		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> clienteService.create(15, "Nome", "razaoSocial", "contato", "rua", "complemento", "bairro", "cep",
						"homepage", "cnpj", "inscricaEstadual", UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
						UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), null, null),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Já existe um cliente associado ao código informado", thrown.getMessage());
	}

	@Test
	public void updateAll_WhenAllParametersInformed_ShouldUpdateAllFields() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");
		Integer version = 0;
		mockFindSingleResultOptional(cliente);

		List<ClienteEmailDTO> emails = new ArrayList<>();
		List<ClienteTelefoneDTO> telefones = new ArrayList<>();

		Cliente result = clienteService.updateAll(uid, version, "Nome-updated", "razaoSocial", "contato-updated",
				Boolean.FALSE, "rua", "complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), emails, telefones);

		Assertions.assertEquals("Nome-updated", result.getNome());
		Assertions.assertEquals("contato-updated", result.getContato());
		Assertions.assertFalse(result.getAtivo());
		Assertions.assertEquals("e08394a0-324c-428b-9ee8-47d1d9c4eb3c", result.getUid().toString());
	}

	@Test
	public void updateAll_WhenChangingEmails_ShouldUpdateEmailsList() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");
		Integer version = 0;
		cliente.addEmail(ClienteEmail.builder().withEmail("email-one@domain.com")
				.withUid(UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da")).build());
		cliente.addEmail(ClienteEmail.builder().withEmail("email-two@domain.com")
				.withUid(UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8")).build());
		mockFindSingleResultOptional(cliente);

		List<ClienteEmailDTO> emails = new ArrayList<>();
		emails.add(ClienteEmailDTO.builder().withEmail("changed-one@email.com")
				.withEmailUid(UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da")).build());
		emails.add(ClienteEmailDTO.builder().withEmail("added@email.com").build());

		List<ClienteTelefoneDTO> telefones = new ArrayList<>();

		Cliente result = clienteService.updateAll(uid, version, "Nome", "razaoSocial", "contato", Boolean.FALSE, "rua",
				"complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), emails, telefones);

		Assertions.assertEquals(2, result.getEmails().size());
		Assertions.assertEquals("changed-one@email.com", result.getEmails().get(0).getEmail());
		Assertions.assertEquals("added@email.com", result.getEmails().get(1).getEmail());
	}

	@Test
	public void updateAll_WhenChangingTelefone_ShouldUpdateTelefoneList() {
		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");

		UUID foneUid1 = UUID.fromString("53846f5e-2216-46e6-8ff6-868bb5b32c82");
		UUID foneUid2 = UUID.fromString("d43cba7f-110c-4818-a013-5eda8a7122d9");
		UUID foneUid3 = UUID.fromString("0586d129-e102-456e-b47a-9dabaa30e06c");

		UUID tipoFoneUid1 = UUID.fromString("7f437e6f-6e20-49cd-9914-36311484a291");
		UUID tipoFoneUid2 = UUID.fromString("57b45020-20e6-484f-9f6c-e1f4d6bf9f3b");
		UUID tipoFoneUid3 = UUID.fromString("c04ed766-f6e5-42ea-8339-6d2098e0101f");

		Cliente cliente = getCliente(clienteUid, 123, "Cliente", "Contato");
		Integer version = 0;
		cliente.addTelefone(getTelefone(foneUid1, 222222, tipoFoneUid1, "mock-1"));
		cliente.addTelefone(getTelefone(foneUid2, 999999, tipoFoneUid1, "mock-2"));
		cliente.addTelefone(getTelefone(foneUid3, 555555, tipoFoneUid1, "mock-3"));

		mockFindSingleResultOptional(cliente);

		List<ClienteEmailDTO> emails = new ArrayList<>();

		List<ClienteTelefoneDTO> telefones = new ArrayList<>();
		telefones.add(ClienteTelefoneDTO.builder().withTelefoneUid(foneUid1).withNumero(333333)
				.withTipoTelefoneUid(null).build()); // alterou numero e removeu tipoTelefoneUid
		telefones.add(ClienteTelefoneDTO.builder().withTelefoneUid(foneUid2).withNumero(999999)
				.withTipoTelefoneUid(tipoFoneUid1).build()); // manteve
		telefones.add(ClienteTelefoneDTO.builder().withNumero(888888).withTipoTelefoneUid(tipoFoneUid3).build()); // novo

		Mockito.when(tipoTelefoneServiceMock.get(tipoFoneUid1)).thenReturn(getTipoTelefone(tipoFoneUid1, "mock-1"));
		Mockito.when(tipoTelefoneServiceMock.get(tipoFoneUid2)).thenReturn(getTipoTelefone(tipoFoneUid2, "mock-2"));
		Mockito.when(tipoTelefoneServiceMock.get(tipoFoneUid3)).thenReturn(getTipoTelefone(tipoFoneUid3, "mock-3"));

		Cliente result = clienteService.updateAll(clienteUid, version, "Nome", "razaoSocial", "contato", Boolean.FALSE,
				"rua", "complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), emails, telefones);

		Assertions.assertEquals(3, result.getTelefones().size());
		Assertions.assertEquals(333333, result.getTelefones().get(0).getNumero());
		Assertions.assertEquals(999999, result.getTelefones().get(1).getNumero());
		Assertions.assertEquals(888888, result.getTelefones().get(2).getNumero());
		Assertions.assertNull(result.getTelefones().get(0).getTipoTelefone());
		Assertions.assertEquals(tipoFoneUid1, result.getTelefones().get(1).getTipoTelefone().getUid());
		Assertions.assertEquals(tipoFoneUid3, result.getTelefones().get(2).getTipoTelefone().getUid());
	}

	@Test
	public void updateAll_WhenSomeParameterNull_ShouldUpdateFieldToNull() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getCliente(uid, 123, "Cliente", "Contato");
		Integer version = 0;
		mockFindSingleResultOptional(cliente);

		List<ClienteEmailDTO> emails = new ArrayList<>();
		List<ClienteTelefoneDTO> telefones = new ArrayList<>();
		Cliente result = clienteService.updateAll(uid, version, "Nome-updated", "razaoSocial", null, null, "rua",
				"complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual", null, null, emails, telefones);

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
		Integer version = 0;
		mockFindSingleResultOptionalEmpty();

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> clienteService.updateAll(uid, version, "Nome-updated", "razaoSocial", "contato-updated",
						Boolean.TRUE, "rua", "complemento", "bairro", "cep", "homepage", "cnpj", "inscricaEstadual",
						UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
						UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), null, null),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Cliente não encontrado para versao especificada", thrown.getMessage());
	}

	@Test
	public void updateNotNull_WhenValuesNull_ShouldNotUpdateFields() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Cliente cliente = getClienteWithDefaultValues(uid, 123);
		Integer version = 0;
		mockFindSingleResultOptional(cliente);

		Cliente result = clienteService.updateNotNull(uid, version, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null);

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
		Integer version = 0;
		mockFindSingleResultOptional(cliente);

		Mockito.when(categClienteServiceMock.get(Mockito.any(UUID.class)))
				.thenReturn(new CategoriaCliente("categoria-updated"));
		Mockito.when(cidadeServiceMock.get(Mockito.any(UUID.class))).thenReturn(new Cidade("cidade-alterada", "FF"));

		List<ClienteEmailDTO> emails = new ArrayList<>();
		List<ClienteTelefoneDTO> telefones = new ArrayList<>();

		Cliente result = clienteService.updateNotNull(uid, version, "nome-updated", "razaoSocial-updated",
				"contato-updated", Boolean.FALSE, "rua-updated", "complemento-updated", "bairro-updated", "cep-updated",
				"homepage-updated", "cnpj-updated", "inscricaoEstadual-updated",
				UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da"),
				UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8"), emails, telefones);

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
		Integer version = 0;
		Mockito.when(repositoryMock.findByUidVersion(uid, version)).thenReturn(Optional.of(cliente));

		clienteService.delete(uid, version);

		Mockito.verify(repositoryMock).delete(Mockito.any(Cliente.class));
	}

	@Test
	public void shouldThrowExceptionWhenDeleteInexistentUid() {
		UUID uid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Integer version = 0;
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> clienteService.delete(uid, version), "should have thrown DataNotFoundException");

		Assertions.assertEquals("Cliente não encontrado para versao especificada", thrown.getMessage());
	}

	@Test
	public void existeClienteComCategoriaCliente_WhenQtdClientePorCategoriaIs0_ShouldReturnFalse() {
		CategoriaCliente categoriaCliente = new CategoriaCliente();
		categoriaCliente.setId(2L);
		Mockito.when(repositoryMock.getQtdClientePorCategoria(Mockito.anyLong())).thenReturn(0L);

		boolean result = clienteService.existeClienteComCategoriaCliente(categoriaCliente);

		Assertions.assertFalse(result);
	}

	@Test
	public void existeClienteComCategoriaCliente_WhenQtdClientePorCategoriaIs1_ShouldReturnTrue() {
		CategoriaCliente categoriaCliente = new CategoriaCliente();
		categoriaCliente.setId(2L);
		Mockito.when(repositoryMock.getQtdClientePorCategoria(Mockito.anyLong())).thenReturn(1L);

		boolean result = clienteService.existeClienteComCategoriaCliente(categoriaCliente);

		Assertions.assertTrue(result);
	}

	@Test
	public void existeClienteComCidade_WhenQtdClientePorCidadeIs0_ShouldReturnFalse() {
		Cidade cidade = new Cidade();
		cidade.setId(3L);
		Mockito.when(repositoryMock.getQtdClientePorCidade(Mockito.anyLong())).thenReturn(0L);

		boolean result = clienteService.existeClienteComCidade(cidade);

		Assertions.assertFalse(result);
	}

	@Test
	public void existeClienteComCidade_WhenQtdClientePorCidadeIs1_ShouldReturnTrue() {
		Cidade cidade = new Cidade();
		cidade.setId(3L);
		Mockito.when(repositoryMock.getQtdClientePorCidade(Mockito.anyLong())).thenReturn(1L);

		boolean result = clienteService.existeClienteComCidade(cidade);

		Assertions.assertTrue(result);
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

	private TipoTelefone getTipoTelefone(UUID tipoTelefoneUid, String descricao) {
		TipoTelefone tipoTelefone = new TipoTelefone(descricao);
		tipoTelefone.setUid(tipoTelefoneUid);
		return tipoTelefone;
	}

	private ClienteTelefone getTelefone(UUID telefoneUid, Integer numero, UUID tipoTelefoneUid,
			String descricaoTipoTelefone) {
		ClienteTelefone clienteTelefone = new ClienteTelefone();
		clienteTelefone.setUid(telefoneUid);
		clienteTelefone.setNumero(numero);
		clienteTelefone.setTipoTelefone(getTipoTelefone(tipoTelefoneUid, descricaoTipoTelefone));
		return clienteTelefone;
	}

	@SuppressWarnings("unchecked")
	private PanacheQuery<Cliente> mockListPanacheQuery(List<Cliente> clientes) {
		PanacheQuery<Cliente> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(clientes);
		return query;
	}

	@SuppressWarnings("unchecked")
	private void mockFindSingleResultOptional(Cliente cliente) {
		PanacheQuery<Cliente> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(repositoryMock.find(Mockito.anyString(), Mockito.any(Map.class))).thenReturn(query);
		Mockito.when(query.singleResultOptional()).thenReturn(Optional.of(cliente));
	}

	@SuppressWarnings("unchecked")
	private void mockFindSingleResultOptionalEmpty() {
		PanacheQuery<Cliente> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(repositoryMock.find(Mockito.anyString(), Mockito.any(Map.class))).thenReturn(query);
		Mockito.when(query.singleResultOptional()).thenReturn(Optional.empty());
	}

}
