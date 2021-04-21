package br.com.jitec.aps.servico.data.model.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.data.model.OrdemServico;

public class OrdemServicoBuilder {

	private OrdemServico instance;

	public OrdemServicoBuilder() {
		instance = new OrdemServico();
	}

	public static OrdemServicoBuilder create() {
		return new OrdemServicoBuilder();
	}

	public OrdemServicoBuilder withUid(UUID uid) {
		instance.setUid(uid);
		return this;
	}

	public OrdemServicoBuilder withCliente(ClienteReplica cliente) {
		instance.setCliente(cliente);
		return this;
	}

	public OrdemServicoBuilder withLancamento(OffsetDateTime lancamento) {
		instance.setLancamento(lancamento);
		return this;
	}

	public OrdemServicoBuilder withValor(BigDecimal valor) {
		instance.setValor(valor);
		return this;
	}

	public OrdemServicoBuilder withFatura(Fatura fatura) {
		instance.setFatura(fatura);
		return this;
	}

	public OrdemServico build() {
		return instance;
	}
}
