package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
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

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.data.repository.FaturaRepository;
import br.com.jitec.aps.servico.data.repository.OrdemServicoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class FaturaServiceTest {

	private static final Integer PAGE = 1;
	private static final Integer SIZE = 10;
	private static final Pagination PAGINATION = Pagination.builder().withPage(PAGE).withSize(SIZE).build();

	@Inject
	FaturaService service;

	@InjectMock
	OrdemServicoService ordemServicoServiceMock;

	@InjectMock
	FaturaRepository repositoryMock;

	@InjectMock
	OrdemServicoRepository osRepositoryMock;

	@Test
	public void getAll_shouldListAll() {
		Fatura fatura1 = buildFatura(BigInteger.valueOf(1L));
		Fatura fatura2 = buildFatura(BigInteger.valueOf(2L));
		List<Fatura> faturas = Arrays.asList(fatura1, fatura2);

		String query = "order by codigo";
		Map<String, Object> params = new LinkedHashMap<>();
		PanacheQuery<Fatura> panacheQuery = mockListPanacheQuery(faturas);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);
		
		Paged<Fatura> result = service.getAll(PAGINATION);

		Assertions.assertEquals(2, result.getContent().size());
		Assertions.assertEquals(BigInteger.valueOf(1L), result.getContent().get(0).getCodigo());
		Assertions.assertEquals(BigInteger.valueOf(2L), result.getContent().get(1).getCodigo());
	}

	@Test
	public void getComplete_whenValidUid_shouldReturnFaturaWithOrdensServico() {
		UUID faturaUid = UUID.fromString("677a195f-f239-4897-906c-a0a184af3dd9");

		Fatura fatura = buildFatura(BigInteger.valueOf(3L), faturaUid);
		mockFindSingleResultOptional(fatura);

		Fatura result = service.getComplete(faturaUid);

		Assertions.assertEquals("677a195f-f239-4897-906c-a0a184af3dd9", result.getUid().toString());
		Assertions.assertEquals(BigInteger.valueOf(3L), result.getCodigo());
	}

	@Test
	public void getComplete_whenInvalidUid_shouldThrowException() {
		UUID faturaUid = UUID.fromString("677a195f-f239-4897-906c-a0a184af3dd9");
		mockFindSingleResultOptionalEmpty();

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.getComplete(faturaUid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Fatura não encontrada", thrown.getMessage());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void create_WithCorrectData_ShouldReturnFatura() {
		OffsetDateTime dataFatura = OffsetDateTime.now();

		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		List<UUID> uids = Arrays.asList(uid1, uid2);

		UUID clienteUid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		ClienteReplica cliente = new ClienteReplica(clienteUid, "nome", Boolean.TRUE);
		OrdemServico os1 = OrdemServico.builder().withUid(uid1).withCliente(cliente)
				.withLancamento(OffsetDateTime.now()).withValor(BigDecimal.valueOf(70L)).build();
		OrdemServico os2 = OrdemServico.builder().withUid(uid2).withCliente(cliente)
				.withLancamento(OffsetDateTime.now()).withValor(BigDecimal.valueOf(22L)).build();
		List<OrdemServico> ordensServico = Arrays.asList(os1, os2);

		Mockito.when(ordemServicoServiceMock.get(uids)).thenReturn(ordensServico);

		Fatura result = service.create(dataFatura, uids);

		Assertions.assertEquals(BigDecimal.valueOf(92L), result.getValorTotal());
		Assertions.assertEquals(clienteUid, result.getCliente().getUid());
		Assertions.assertEquals(dataFatura, result.getData());

		Mockito.verify(repositoryMock).persist(Mockito.any(Fatura.class));
		Mockito.verify(osRepositoryMock).persist(Mockito.any(List.class));
	}

	@Test
	public void create_WithDistinctCliente_ShouldThrowException() {
		OffsetDateTime dataFatura = OffsetDateTime.now();

		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		List<UUID> uids = Arrays.asList(uid1, uid2);

		UUID clienteUid1 = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		ClienteReplica cliente1 = new ClienteReplica(clienteUid1, "nome", Boolean.TRUE);
		OrdemServico os1 = OrdemServico.builder().withUid(uid1).withCliente(cliente1)
				.withLancamento(OffsetDateTime.now()).withValor(BigDecimal.valueOf(70L)).build();
		UUID clienteUid2 = UUID.fromString("e1b4f9c0-6ab4-4040-b3a6-b7089da42be8");
		ClienteReplica cliente2 = new ClienteReplica(clienteUid2, "nome", Boolean.TRUE);
		OrdemServico os2 = OrdemServico.builder().withUid(uid2).withCliente(cliente2)
				.withLancamento(OffsetDateTime.now()).withValor(BigDecimal.valueOf(22L)).build();
		List<OrdemServico> ordensServico = Arrays.asList(os1, os2);

		Mockito.when(ordemServicoServiceMock.get(uids)).thenReturn(ordensServico);

		Exception thrown = Assertions.assertThrows(InvalidDataException.class, () -> service.create(dataFatura, uids),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("As ordens de serviço informadas devem ser do mesmo cliente", thrown.getMessage());
	}

	@Test
	public void create_WithoutDataLancamento_ShouldThrowException() {
		OffsetDateTime dataFatura = OffsetDateTime.now();

		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		List<UUID> uids = Arrays.asList(uid1, uid2);

		UUID clienteUid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		ClienteReplica cliente = new ClienteReplica(clienteUid, "nome", Boolean.TRUE);
		OrdemServico os1 = OrdemServico.builder().withUid(uid1).withCliente(cliente).withLancamento(null)
				.withValor(BigDecimal.valueOf(70L)).build();
		OrdemServico os2 = OrdemServico.builder().withUid(uid2).withCliente(cliente)
				.withLancamento(OffsetDateTime.now()).withValor(BigDecimal.valueOf(22L)).build();
		List<OrdemServico> ordensServico = Arrays.asList(os1, os2);

		Mockito.when(ordemServicoServiceMock.get(uids)).thenReturn(ordensServico);

		Exception thrown = Assertions.assertThrows(InvalidDataException.class, () -> service.create(dataFatura, uids),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Uma ou mais ordens de serviço informadas ainda não foram lançadas",
				thrown.getMessage());
	}

	@Test
	public void create_WithFaturaAlreadyAssigned_ShouldThrowException() {
		OffsetDateTime dataFatura = OffsetDateTime.now();

		UUID uid1 = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID uid2 = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		List<UUID> uids = Arrays.asList(uid1, uid2);

		UUID clienteUid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		ClienteReplica cliente = new ClienteReplica(clienteUid, "nome", Boolean.TRUE);
		OrdemServico os1 = OrdemServico.builder().withUid(uid1).withCliente(cliente)
				.withLancamento(OffsetDateTime.now()).withValor(BigDecimal.valueOf(70L)).build();
		OrdemServico os2 = OrdemServico.builder().withUid(uid2).withCliente(cliente)
				.withLancamento(OffsetDateTime.now()).withValor(BigDecimal.valueOf(22L)).withFatura(new Fatura())
				.build();
		List<OrdemServico> ordensServico = Arrays.asList(os1, os2);

		Mockito.when(ordemServicoServiceMock.get(uids)).thenReturn(ordensServico);

		Exception thrown = Assertions.assertThrows(InvalidDataException.class, () -> service.create(dataFatura, uids),
				"should have thrown InvalidDataException");

		Assertions.assertEquals("Uma ou mais ordens de serviço informadas já foram faturadas", thrown.getMessage());
	}

	private Fatura buildFatura(BigInteger codigo) {
		Fatura fatura = new Fatura();
		fatura.setCodigo(codigo);
		return fatura;
	}

	private Fatura buildFatura(BigInteger codigo, UUID uid) {
		Fatura fatura = buildFatura(codigo);
		fatura.setUid(uid);
		return fatura;
	}

	@SuppressWarnings("unchecked")
	private void mockFindSingleResultOptional(Fatura fatura) {
		PanacheQuery<Fatura> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(repositoryMock.find(Mockito.anyString(), Mockito.any(Map.class))).thenReturn(query);
		Mockito.when(query.singleResultOptional()).thenReturn(Optional.of(fatura));
	}

	@SuppressWarnings("unchecked")
	private void mockFindSingleResultOptionalEmpty() {
		PanacheQuery<Fatura> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(repositoryMock.find(Mockito.anyString(), Mockito.any(Map.class))).thenReturn(query);
		Mockito.when(query.singleResultOptional()).thenReturn(Optional.empty());
	}

	@SuppressWarnings("unchecked")
	private PanacheQuery<Fatura> mockListPanacheQuery(List<Fatura> faturas) {
		PanacheQuery<Fatura> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(faturas);
		return query;
	}

}
