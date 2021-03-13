package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.data.model.TipoServico;
import br.com.jitec.aps.servico.data.repository.OrdemServicoRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class OrdemServicoServiceTest {

	@Inject
	OrdemServicoService service;

	@InjectMock
	OrdemServicoRepository repositoryMock;

	@InjectMock
	ClienteReplicaService clienteServiceMock;

	@InjectMock
	TipoServicoService tipoServicoServiceMock;

	@Test
	public void getAll_ShouldListAll() {
		OrdemServico os1 = getOrdemServico(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"),
				new BigInteger("123"));
		OrdemServico os2 = getOrdemServico(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"),
				new BigInteger("456"));
		List<OrdemServico> oss = Arrays.asList(os1, os2);
		Mockito.when(repositoryMock.list("order by numero")).thenReturn(oss);

		List<OrdemServico> result = service.getAll();

		Assertions.assertEquals(2, result.size());
	}

	@Test
	public void get_WithExistingUUID_ShouldReturnOS() {
		UUID uid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Ordem de Serviço não encontrada", thrown.getMessage());
	}

	@Test
	public void get_WithNonexistingUUID_ShouldThrowException() {
		UUID uid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		OrdemServico os1 = getOrdemServico(uid, new BigInteger("123"));
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(os1));

		OrdemServico result = service.get(uid);

		Assertions.assertEquals("66a1f5d6-f838-450e-b186-542f52413e4b", result.getUid().toString());
		Assertions.assertEquals(new BigInteger("123"), result.getNumero());
	}

	@Test
	public void create_WithAllFieldsInformed_ShouldPopulateMatchingAttributes() {
		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID tipoServicoUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");

		Mockito.when(repositoryMock.getNextNumeroOS()).thenReturn(new BigInteger("7"));

		Mockito.when(clienteServiceMock.get(clienteUid)).thenReturn(getCliente(Boolean.TRUE));
		Mockito.when(tipoServicoServiceMock.get(tipoServicoUid)).thenReturn(new TipoServico());

		OrdemServico result = service.create(clienteUid, tipoServicoUid, BigDecimal.ONE, "contato", "descricao",
				"observacao", OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now());

		Assertions.assertNotNull(result.getUid());
		Assertions.assertEquals(new BigInteger("7"), result.getNumero());
		Assertions.assertEquals("descricao", result.getDescricao());
		Assertions.assertNotNull(result.getCliente());
		Assertions.assertNotNull(result.getTipoServico());
	}

	@Test
	public void create_WithNullAllFields_ShouldCreateWithNullValues() {
		Mockito.when(repositoryMock.getNextNumeroOS()).thenReturn(new BigInteger("7"));

		OrdemServico result = service.create(null, null, BigDecimal.ONE, "contato", "descricao", "observacao",
				OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now());

		Assertions.assertNotNull(result.getUid());
		Assertions.assertEquals(new BigInteger("7"), result.getNumero());
		Assertions.assertEquals("descricao", result.getDescricao());
		Assertions.assertNull(result.getCliente());
		Assertions.assertNull(result.getTipoServico());
	}

	@Test
	public void create_WithClienteInativo_ShouldThrowException() {
		Mockito.when(repositoryMock.getNextNumeroOS()).thenReturn(new BigInteger("7"));

		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Mockito.when(clienteServiceMock.get(clienteUid)).thenReturn(getCliente(Boolean.FALSE));

		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> service.create(clienteUid, null, BigDecimal.ONE, "contato", "descricao", "observacao",
						OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now()),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Não é possível cadastrar uma ordem de serviço para um cliente inativo",
				thrown.getMessage());
	}

	private ClienteReplica getCliente(Boolean ativo) {
		ClienteReplica cliente = new ClienteReplica();
		cliente.setAtivo(ativo);
		return cliente;
	}

	private OrdemServico getOrdemServico(UUID uid, BigInteger numero) {
		OrdemServico os = new OrdemServico();
		os.setUid(uid);
		os.setNumero(numero);
		return os;
	}

}
