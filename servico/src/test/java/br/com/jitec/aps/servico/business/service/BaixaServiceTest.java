package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.jitec.aps.servico.business.producer.ClienteSaldoProducer;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.TipoBaixa;
import br.com.jitec.aps.servico.data.repository.BaixaRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class BaixaServiceTest {

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
}
