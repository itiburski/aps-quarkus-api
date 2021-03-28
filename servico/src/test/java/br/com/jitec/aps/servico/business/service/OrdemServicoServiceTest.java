package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import br.com.jitec.aps.servico.business.data.OrdemServicoFilter;
import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.Fatura;
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
	private static final ZoneOffset OFFSET = OffsetDateTime.now().getOffset();

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
	public void getAll_WhenUsingNoFilter_ShouldListAll() {
		OrdemServico os1 = getOrdemServico(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"), 123);
		OrdemServico os2 = getOrdemServico(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"), 456);
		List<OrdemServico> oss = Arrays.asList(os1, os2);

		String query = "order by numero";
		Map<String, Object> params = new LinkedHashMap<>();
		PanacheQuery<OrdemServico> panacheQuery = mockListPanacheQuery(oss);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<OrdemServico> result = service.getAll(PAGINATION, new OrdemServicoFilter());

		Assertions.assertEquals(2, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);
	}

	@Test
	public void getAll_WhenUsingSomeFilter_ShouldListUsingFilter() {
		UUID clienteUid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		LocalDate entradaFrom = LocalDate.of(2021, 2, 1);
		LocalDate entradaTo = LocalDate.of(2021, 2, 28);
		OrdemServicoFilter filter = OrdemServicoFilter.builder().withClienteUid(clienteUid).withEntradaFrom(entradaFrom)
				.withEntradaTo(entradaTo).withLancado(Boolean.TRUE).withEntregue(Boolean.TRUE)
				.withFaturado(Boolean.TRUE).build();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("clienteUid", clienteUid);
		params.put("entradaFrom", OffsetDateTime.of(entradaFrom, LocalTime.MIN, OFFSET));
		params.put("entradaTo", OffsetDateTime.of(entradaTo, LocalTime.MAX, OFFSET));

		String query = "cliente.uid = :clienteUid and entrada >= :entradaFrom and entrada <= :entradaTo and entrega is not null and lancamento is not null and fatura is not null order by numero";

		OrdemServico os1 = getOrdemServico(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"), 123);
		List<OrdemServico> oss = Arrays.asList(os1);

		PanacheQuery<OrdemServico> panacheQuery = mockListPanacheQuery(oss);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<OrdemServico> result = service.getAll(PAGINATION, filter);

		Assertions.assertEquals(1, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);
	}

	@Test
	public void getAll_WhenUsingFilterEntregueFalse_ShouldListUsingThisFilter() {
		UUID clienteUid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		OrdemServicoFilter filter = OrdemServicoFilter.builder().withClienteUid(clienteUid).withLancado(Boolean.FALSE)
				.withEntregue(Boolean.FALSE).withFaturado(Boolean.FALSE).build();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("clienteUid", clienteUid);

		String query = "cliente.uid = :clienteUid and entrega is null and lancamento is null and fatura is null order by numero";

		OrdemServico os1 = getOrdemServico(UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c"), 123);
		List<OrdemServico> oss = Arrays.asList(os1);

		PanacheQuery<OrdemServico> panacheQuery = mockListPanacheQuery(oss);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<OrdemServico> result = service.getAll(PAGINATION, filter);

		Assertions.assertEquals(1, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);
	}

	@Test
	public void get_WithExistingUUID_ShouldReturnOS() {
		UUID uid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		OrdemServico os1 = getOrdemServico(uid, 123);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(os1));

		OrdemServico result = service.get(uid);

		Assertions.assertEquals("66a1f5d6-f838-450e-b186-542f52413e4b", result.getUid().toString());
		Assertions.assertEquals(123, result.getNumero());
	}

	@Test
	public void get_WithNonexistingUUID_ShouldThrowException() {
		UUID uid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Ordem de Serviço não encontrada", thrown.getMessage());
	}

	@Test
	public void get_WithListOfUUIDs_ShouldReturnListOfOS() {
		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		List<UUID> uids = Arrays.asList(uid1, uid2);
		OrdemServico os1 = getOrdemServico(uid1, 123);
		OrdemServico os2 = getOrdemServico(uid2, 456);
		List<OrdemServico> ordensServico = Arrays.asList(os1, os2);

		Mockito.when(repositoryMock.findByUids(uids)).thenReturn(ordensServico);

		List<OrdemServico> result = service.get(uids);

		Assertions.assertEquals(2, result.size());
	}

	@Test
	public void get_WithListOfUUIDsWhichOneIsInvalid_ShouldThrowException() {
		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		List<UUID> uids = Arrays.asList(uid1, uid2);
		OrdemServico os1 = getOrdemServico(uid1, 123);
		List<OrdemServico> ordensServico = Arrays.asList(os1);

		Mockito.when(repositoryMock.findByUids(uids)).thenReturn(ordensServico);

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uids),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Uma ou mais ordens de serviço não foram encontradas para os parâmetros informados",
				thrown.getMessage());
	}

	@Test
	public void create_WithAllFieldsInformed_ShouldPopulateMatchingAttributes() {
		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID tipoServicoUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");

		Mockito.when(repositoryMock.getNextNumeroOS()).thenReturn(7);

		Mockito.when(clienteServiceMock.get(clienteUid)).thenReturn(getCliente(Boolean.TRUE));
		Mockito.when(tipoServicoServiceMock.get(tipoServicoUid)).thenReturn(new TipoServico());

		OrdemServico result = service.create(clienteUid, tipoServicoUid, BigDecimal.ONE, "contato", "descricao",
				"observacao", OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now());

		Assertions.assertNotNull(result.getUid());
		Assertions.assertEquals(7, result.getNumero());
		Assertions.assertEquals("descricao", result.getDescricao());
		Assertions.assertNotNull(result.getCliente());
		Assertions.assertNotNull(result.getTipoServico());
	}

	@Test
	public void create_WithNullAllFields_ShouldCreateWithNullValues() {
		Mockito.when(repositoryMock.getNextNumeroOS()).thenReturn(7);

		OrdemServico result = service.create(null, null, BigDecimal.ONE, "contato", "descricao", "observacao",
				OffsetDateTime.now(), OffsetDateTime.now(), OffsetDateTime.now());

		Assertions.assertNotNull(result.getUid());
		Assertions.assertEquals(7, result.getNumero());
		Assertions.assertEquals("descricao", result.getDescricao());
		Assertions.assertNull(result.getCliente());
		Assertions.assertNull(result.getTipoServico());
	}

	@Test
	public void create_WithClienteInativo_ShouldThrowException() {
		Mockito.when(repositoryMock.getNextNumeroOS()).thenReturn(7);

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
		OrdemServico os = getOrdemServico(osUid, 123);
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
	public void definirLancamento_WithEmptyValorEmptyDtLancamento_ShouldUpdateData() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, 123);
		os.setCliente(new ClienteReplica());
		UUID clienteUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		os.getCliente().setUid(clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		OrdemServico result = service.definirLancamento(osUid, version,
				OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")), new BigDecimal("123"));

		Assertions.assertEquals(new BigDecimal("123"), result.getValor());
		Assertions.assertEquals(OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")),
				result.getLancamento());

		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, new BigDecimal("-123"));
	}

	@Test
	public void definirLancamento_WithExistingValorEmptyDtLancamento_ShouldUpdateData() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, 123);
		os.setValor(new BigDecimal("100"));
		os.setCliente(new ClienteReplica());
		UUID clienteUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		os.getCliente().setUid(clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		OrdemServico result = service.definirLancamento(osUid, version,
				OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")), new BigDecimal("123"));

		Assertions.assertEquals(new BigDecimal("123"), result.getValor());
		Assertions.assertEquals(OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")),
				result.getLancamento());

		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, new BigDecimal("-123"));
	}

	@Test
	public void definirLancamento_WithExistingDtLancamento_ShouldUpdateData() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, 123);
		os.setLancamento(OffsetDateTime.now());
		os.setValor(new BigDecimal("50"));
		os.setCliente(new ClienteReplica());
		UUID clienteUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		os.getCliente().setUid(clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		OrdemServico result = service.definirLancamento(osUid, version,
				OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")), new BigDecimal("123"));

		Assertions.assertEquals(new BigDecimal("123"), result.getValor());
		Assertions.assertEquals(OffsetDateTime.of(2021, 3, 14, 0, 0, 0, 0, ZoneOffset.of("-03")),
				result.getLancamento());

		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, new BigDecimal("-73"));
	}

	@Test
	public void definirLancamento_WithNonexistingUidAndVersion_ShouldThrowException() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, 123);
		os.setCliente(new ClienteReplica());
		os.getCliente().setUid(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"));

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class,
				() -> service.definirLancamento(osUid, version, OffsetDateTime.now(), new BigDecimal("123")),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Ordem de Serviço não encontrada para versao especificada", thrown.getMessage());

		Mockito.verify(clienteSaldoProducerMock, Mockito.never())
				.sendUpdateSaldoCliente(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BigDecimal.class));
	}

	@Test
	public void definirLancamento_WithOrdemServicoJaFaturada_ShouldThrowException() {
		Integer version = 1;
		UUID osUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		OrdemServico os = getOrdemServico(osUid, 123);
		os.setCliente(new ClienteReplica());
		os.setFatura(new Fatura());
		os.getCliente().setUid(UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b"));

		Mockito.when(repositoryMock.findByUidVersion(osUid, version)).thenReturn(Optional.of(os));

		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> service.definirLancamento(osUid, version, OffsetDateTime.now(), new BigDecimal("123")),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Ordem de serviço já foi faturada", thrown.getMessage());

		Mockito.verify(repositoryMock, Mockito.never()).persist(os);
		Mockito.verify(clienteSaldoProducerMock, Mockito.never())
				.sendUpdateSaldoCliente(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BigDecimal.class));
	}

	private ClienteReplica getCliente(Boolean ativo) {
		ClienteReplica cliente = new ClienteReplica();
		cliente.setAtivo(ativo);
		return cliente;
	}

	private OrdemServico getOrdemServico(UUID uid, Integer numero) {
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
