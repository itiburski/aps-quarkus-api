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
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.servico.business.data.BaixaFilter;
import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.TipoBaixa;
import br.com.jitec.aps.servico.data.repository.BaixaRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class BaixaServiceTest {

	private static final Integer PAGE = 1;
	private static final Integer SIZE = 10;
	private static final Pagination PAGINATION = Pagination.builder().withPage(PAGE).withSize(SIZE).build();
	private static final ZoneOffset OFFSET = OffsetDateTime.now().getOffset();

	@Inject
	BaixaService service;

	@InjectMock
	ClienteReplicaService clienteServiceMock;

	@InjectMock
	TipoBaixaService tipoBaixaServiceMock;

	@InjectMock
	BaixaRepository repositoryMock;

	@InjectMock
	ClienteSaldoProducer clienteSaldoProducerMock;

	public void getAll_WithPagination_ShouldReturnList() {
		Baixa baixa1 = buildBaixa(UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f"));
		Baixa baixa2 = buildBaixa(UUID.fromString("95c3a27b-af00-43b5-9b71-6f09b0e98867"));
		List<Baixa> baixas = Arrays.asList(baixa1, baixa2);

		String query = "order by id";
		Map<String, Object> params = new LinkedHashMap<>();
		PanacheQuery<Baixa> panacheQuery = mockListPanacheQuery(baixas);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<Baixa> result = service.getAll(PAGINATION, new BaixaFilter());

		Assertions.assertEquals(2, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);
	}

	@Test
	public void getAll_WhenUsingSomeFilter_ShouldListUsingFilter() {
		UUID clienteUid = UUID.fromString("92bd0555-93e3-4ee7-86c7-7ed6dd39c5da");
		UUID tipoBaixaUid = UUID.fromString("02bc413d-83b9-41ec-9bfe-26b56a6d2732");
		LocalDate dataFrom = LocalDate.of(2021, 2, 1);
		LocalDate dataTo = LocalDate.of(2021, 2, 28);
		BaixaFilter filter = new BaixaFilter(clienteUid, tipoBaixaUid, dataFrom, dataTo);

		Baixa baixa1 = buildBaixa(UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f"));
		List<Baixa> baixas = Arrays.asList(baixa1);

		String query = "cliente.uid = :clienteUid and tipoBaixa.uid = :tipoBaixaUid and data >= :dataFrom and data <= :dataTo order by id";
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("clienteUid", clienteUid);
		params.put("tipoBaixaUid", tipoBaixaUid);
		params.put("dataFrom", OffsetDateTime.of(dataFrom, LocalTime.MIN, OFFSET));
		params.put("dataTo", OffsetDateTime.of(dataTo, LocalTime.MAX, OFFSET));
		PanacheQuery<Baixa> panacheQuery = mockListPanacheQuery(baixas);
		Mockito.when(repositoryMock.find(query, params)).thenReturn(panacheQuery);

		Paged<Baixa> result = service.getAll(PAGINATION, filter);

		Assertions.assertEquals(1, result.getContent().size());
		Mockito.verify(repositoryMock).find(query, params);		
	}
	
	@Test
	public void get_WithExistingUUID_ShouldReturnBaixa() {
		UUID uid = UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f");
		Baixa baixa1 = buildBaixa(uid);
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.of(baixa1));

		Baixa result = service.get(uid);

		Assertions.assertEquals("00b2d407-85b0-4b07-be1f-b08d7909126f", result.getUid().toString());
	}

	@Test
	public void get_WithNonexistingUUID_ShouldThrowException() {
		UUID uid = UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f");
		Mockito.when(repositoryMock.findByUid(uid)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.get(uid),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Baixa não encontrada", thrown.getMessage());
	}

	@Test
	public void create_WithCorrectData_ShouldReturnBaixa() {
		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID tipoBaixaUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");

		Mockito.when(clienteServiceMock.get(clienteUid)).thenReturn(new ClienteReplica());
		Mockito.when(tipoBaixaServiceMock.get(tipoBaixaUid)).thenReturn(new TipoBaixa());

		Baixa result = service.create(tipoBaixaUid, OffsetDateTime.now(), BigDecimal.TEN, "observacao", clienteUid);

		Assertions.assertNotNull(result.getUid());
		Assertions.assertNotNull(result.getCliente());
		Assertions.assertNotNull(result.getTipoBaixa());
		Assertions.assertEquals(BigDecimal.TEN, result.getValor());
		Assertions.assertEquals("observacao", result.getObservacao());

		Mockito.verify(repositoryMock).persist(result);
		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, BigDecimal.TEN);
	}

	@Test
	public void create_WithNullAllFields_ShouldCreateWithNullValues() {
		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID tipoBaixaUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");

		Mockito.when(clienteServiceMock.get(clienteUid)).thenReturn(new ClienteReplica());
		Mockito.when(tipoBaixaServiceMock.get(tipoBaixaUid)).thenReturn(new TipoBaixa());

		Baixa result = service.create(tipoBaixaUid, OffsetDateTime.now(), BigDecimal.TEN, null, clienteUid);

		Assertions.assertNotNull(result.getUid());
		Assertions.assertNotNull(result.getCliente());
		Assertions.assertNotNull(result.getTipoBaixa());
		Assertions.assertEquals(BigDecimal.TEN, result.getValor());
		Assertions.assertNull(result.getObservacao());

		Mockito.verify(repositoryMock).persist(result);
		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, BigDecimal.TEN);
	}

	@Test
	public void update_WithExistingUidAndVersion_ShouldUpdateData() {
		UUID baixaUid = UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f");
		Integer version = 3;
		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		UUID tipoBaixaUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");
		Baixa baixa = buildBaixa(baixaUid, BigDecimal.TEN, clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(baixaUid, version)).thenReturn(Optional.of(baixa));
		Mockito.when(clienteServiceMock.get(clienteUid)).thenReturn(new ClienteReplica());
		Mockito.when(tipoBaixaServiceMock.get(tipoBaixaUid)).thenReturn(new TipoBaixa());

		Baixa result = service.update(baixaUid, version, tipoBaixaUid, OffsetDateTime.now(), BigDecimal.ONE,
				"observacao-alterada");

		Assertions.assertNotNull(result.getUid());
		Assertions.assertNotNull(result.getCliente());
		Assertions.assertNotNull(result.getTipoBaixa());
		Assertions.assertEquals(BigDecimal.ONE, result.getValor());
		Assertions.assertEquals("observacao-alterada", result.getObservacao());

		Mockito.verify(repositoryMock).persist(result);
		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, BigDecimal.valueOf(9).negate());
		Mockito.verify(clienteServiceMock, Mockito.never()).get(clienteUid);
	}

	@Test
	public void update_WithNonexistingUidAndVersion_ShouldThrowException() {
		UUID baixaUid = UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f");
		Integer version = 3;
		UUID tipoBaixaUid = UUID.fromString("66a1f5d6-f838-450e-b186-542f52413e4b");

		Mockito.when(repositoryMock.findByUidVersion(baixaUid, version)).thenReturn(Optional.empty());

		Exception thrown = Assertions
				.assertThrows(
						DataNotFoundException.class, () -> service.update(baixaUid, version, tipoBaixaUid,
								OffsetDateTime.now(), BigDecimal.ONE, "observacao-alterada"),
						"should have thrown DataNotFoundException");

		Assertions.assertEquals("Baixa não encontrada para versao especificada", thrown.getMessage());

		Mockito.verify(repositoryMock, Mockito.never()).persist(ArgumentMatchers.any(Baixa.class));
		Mockito.verify(clienteSaldoProducerMock, Mockito.never())
				.sendUpdateSaldoCliente(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BigDecimal.class));
		Mockito.verify(clienteServiceMock, Mockito.never()).get(ArgumentMatchers.any(UUID.class));
	}

	@Test
	public void delete_WithExistingUidAndVersion_ShouldDelete() {
		UUID baixaUid = UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f");
		Integer version = 3;
		UUID clienteUid = UUID.fromString("e08394a0-324c-428b-9ee8-47d1d9c4eb3c");
		Baixa baixa = buildBaixa(baixaUid, BigDecimal.TEN, clienteUid);

		Mockito.when(repositoryMock.findByUidVersion(baixaUid, version)).thenReturn(Optional.of(baixa));

		service.delete(baixaUid, version);

		Mockito.verify(repositoryMock).delete(baixa);
		Mockito.verify(clienteSaldoProducerMock).sendUpdateSaldoCliente(clienteUid, BigDecimal.TEN.negate());
		Mockito.verify(clienteServiceMock, Mockito.never()).get(clienteUid);
	}

	@Test
	public void delete_WithNonexistingUidAndVersion_ShouldThrowException() {
		UUID baixaUid = UUID.fromString("00b2d407-85b0-4b07-be1f-b08d7909126f");
		Integer version = 3;
		Mockito.when(repositoryMock.findByUidVersion(baixaUid, version)).thenReturn(Optional.empty());

		Exception thrown = Assertions.assertThrows(DataNotFoundException.class, () -> service.delete(baixaUid, version),
				"should have thrown DataNotFoundException");

		Assertions.assertEquals("Baixa não encontrada para versao especificada", thrown.getMessage());

		Mockito.verify(repositoryMock, Mockito.never()).delete(ArgumentMatchers.any(Baixa.class));
		Mockito.verify(clienteSaldoProducerMock, Mockito.never())
				.sendUpdateSaldoCliente(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BigDecimal.class));
	}

	private Baixa buildBaixa(UUID uid) {
		Baixa baixa = new Baixa();
		baixa.setUid(uid);
		return baixa;
	}

	private Baixa buildBaixa(UUID uid, BigDecimal valor, UUID clienteUid) {
		Baixa baixa = buildBaixa(uid);
		baixa.setValor(valor);
		baixa.setCliente(new ClienteReplica());
		baixa.getCliente().setUid(clienteUid);
		return baixa;
	}

	@SuppressWarnings("unchecked")
	private PanacheQuery<Baixa> mockListPanacheQuery(List<Baixa> baixas) {
		PanacheQuery<Baixa> query = Mockito.mock(PanacheQuery.class);
		Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(baixas);
		return query;
	}
}
