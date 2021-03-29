package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
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
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
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

		Paged<Baixa> result = service.getAll(PAGINATION);

		Assertions.assertEquals(2, result.getContent().size());
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

	private Baixa buildBaixa(UUID uid) {
		Baixa baixa = new Baixa();
		baixa.setUid(uid);
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
