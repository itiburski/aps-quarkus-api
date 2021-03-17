package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.data.model.TipoServico;
import br.com.jitec.aps.servico.data.repository.OrdemServicoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class OrdemServicoServiceTest {

	private static final Integer PAGE = 1;
	private static final Integer SIZE = 10;
	private static final Pagination PAGINATION = Pagination.builder().withPage(PAGE).withSize(SIZE).build();

	@Inject
	OrdemServicoService service;

	@InjectMock
	OrdemServicoRepository repositoryMock;

	@InjectMock
	ClienteReplicaService clienteServiceMock;

	@InjectMock
	TipoServicoService tipoServicoServiceMock;

	@InjectMock
	ClienteSaldoProducer clienteSaldoProducerMock;

	@Test
	public void getAll_ShouldListAll() {
		OrdemServico os1 = getOrdemServico(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"),
				new BigInteger("123"));
		OrdemServico os2 = getOrdemServico(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"),
				new BigInteger("456"));
		List<OrdemServico> oss = Arrays.asList(os1, os2);

		String query = "order by numero";
		PanacheQuery<OrdemServico> panacheQuery = mockListPanacheQuery(oss);
		Mockito.when(repositoryMock.find(query)).thenReturn(panacheQuery);

		Paged<OrdemServico> result = service.getAll(PAGINATION);

		Assertions.assertEquals(2, result.getContent().size());
		Mockito.verify(repositoryMock).find(query);
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
				"observacao", OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now());

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
				OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now());

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
						OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now()),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Não é possível cadastrar uma ordem de serviço para um cliente inativo",
				thrown.getMessage());
	}

	@Test
	public void update_WithExistingUidAndVersion_ShouldUpdateData() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, new BigInteger("123"));
		UUID tipoServicoUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		OrdemServico result = service.update(osUid, version, tipoServicoUid, "contato", "descricao", "observacao",
				OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now());

		Assertions.assertEquals("contato", result.getContato());
	}

	@Test
	public void update_WithNonexistingUidAndVersion_ShouldThrowException() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID tipoServicoUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> service.update(osUid, version, tipoServicoUid, "contato", "descricao", "observacao",
						OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now()),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Ordem de Serviço não encontrada para versao especificada", thrown.getMessage());
	}

	@Test
	public void definirConclusao_WithEmptyValorEmptyDtConclusao_ShouldUpdateData() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, new BigInteger("123"));
		os.setCliente(new ClienteReplica());
		UUID clienteUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		os.getCliente().setUid(clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		OrdemServico result = service.definirConclusao(osUid, version,
				OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")), new BigDecimal("123"));

		Assertions.assertEquals(new BigDecimal("123"), result.getValor());
		Assertions.assertEquals(OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")),
				result.getConclusao());

		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, new BigDecimal("-123"));
	}

	@Test
	public void definirConclusao_WithExistingValorEmptyDtConclusao_ShouldUpdateData() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, new BigInteger("123"));
		os.setValor(new BigDecimal("100"));
		os.setCliente(new ClienteReplica());
		UUID clienteUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		os.getCliente().setUid(clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		OrdemServico result = service.definirConclusao(osUid, version,
				OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")), new BigDecimal("123"));

		Assertions.assertEquals(new BigDecimal("123"), result.getValor());
		Assertions.assertEquals(OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")),
				result.getConclusao());

		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, new BigDecimal("-123"));
	}

	@Test
	public void definirConclusao_WithExistingDtConclusao_ShouldUpdateData() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, new BigInteger("123"));
		os.setConclusao(OffsetDateTime.now());
		os.setValor(new BigDecimal("50"));
		os.setCliente(new ClienteReplica());
		UUID clienteUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		os.getCliente().setUid(clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		OrdemServico result = service.definirConclusao(osUid, version,
				OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")), new BigDecimal("123"));

		Assertions.assertEquals(new BigDecimal("123"), result.getValor());
		Assertions.assertEquals(OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")),
				result.getConclusao());

		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, new BigDecimal("-73"));
	}

	@Test
	public void definirConclusao_WithNonexistingUidAndVersion_ShouldThrowException() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, new BigInteger("123"));
		os.setCliente(new ClienteReplica());
		os.getCliente().setUid(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"));

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> service.definirConclusao(osUid, version, OffsetDateTime.now(), new BigDecimal("123")),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Ordem de Serviço não encontrada para versao especificada", thrown.getMessage());

		Mockito.verify(clienteSaldoProducerMock, Mockito.never())
				.sendUpdateSaldoCliente(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BigDecimal.class));
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

	@SuppressWarnings("unchecked")
	private PanacheQuery<OrdemServico> mockListPanacheQuery(List<OrdemServico> clientes) {
		PanacheQuery<OrdemServico> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(clientes);
		return query;
	}

}
