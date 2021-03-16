package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.data.repository.OrdemServicoRepository;

@ApplicationScoped
public class OrdemServicoService {

	@Inject
	OrdemServicoRepository repository;

	@Inject
	ClienteReplicaService clienteService;

	@Inject
	TipoServicoService tipoServicoService;

	public List<OrdemServico> getAll() {
		return repository.list("order by numero");
	}

	public OrdemServico get(UUID ordemServicoUid) {
		return repository.findByUid(ordemServicoUid)
				.orElseThrow(() -> new DataNotFoundException("Ordem de Serviço não encontrada"));
	}

	private OrdemServico get(UUID ordemServicoUid, Integer version) {
		return repository.findByUidVersion(ordemServicoUid, version).orElseThrow(
				() -> new DataNotFoundException("Ordem de Serviço não encontrada para versao especificada"));
	}

	@Transactional
	public OrdemServico create(UUID clienteUid, UUID tipoServicoUid, BigDecimal valor, String contato, String descricao,
			String observacao, OffsetDateTime entrada, OffsetDateTime agendadoPara, OffsetDateTime entrega) {
		OrdemServico os = new OrdemServico();

		os.setValor(valor);
		os.setContato(contato);
		os.setDescricao(descricao);
		os.setObservacao(observacao);
		os.setEntrada(entrada);
		os.setAgendadoPara(agendadoPara);
		os.setEntrega(entrega);
		os.setNumero(repository.getNextNumeroOS());

		if (Objects.nonNull(clienteUid)) {
			os.setCliente(getCliente(clienteUid));
		}
		if (Objects.nonNull(tipoServicoUid)) {
			os.setTipoServico(tipoServicoService.get(tipoServicoUid));
		}

		repository.persist(os);

		return os;
	}

	private ClienteReplica getCliente(UUID clienteUid) {
		ClienteReplica cliente = clienteService.get(clienteUid);
		if (!cliente.getAtivo()) {
			throw new InvalidDataException("Não é possível cadastrar uma ordem de serviço para um cliente inativo");
		}
		return cliente;
	}

	@Transactional
	public OrdemServico update(UUID ordemServicoUid, Integer version, UUID tipoServicoUid, String contato,
			String descricao, String observacao, OffsetDateTime entrada, OffsetDateTime agendadoPara,
			OffsetDateTime entrega) {
		OrdemServico os = get(ordemServicoUid, version);

		os.setContato(contato);
		os.setDescricao(descricao);
		os.setObservacao(observacao);
		os.setEntrada(entrada);
		os.setAgendadoPara(agendadoPara);
		os.setEntrega(entrega);

		if (Objects.nonNull(tipoServicoUid)) {
			os.setTipoServico(tipoServicoService.get(tipoServicoUid));
		}

		repository.persist(os);

		return os;
	}

	@Transactional
	public OrdemServico definirConclusao(UUID ordemServicoUid, Integer version, OffsetDateTime conclusao,
			BigDecimal valor) {
		OrdemServico os = get(ordemServicoUid, version);

		BigDecimal vlAnterior = os.getConclusao() != null ? os.getValor() : BigDecimal.ZERO;
		BigDecimal saldoARefletirNoCliente = vlAnterior.subtract(valor);

		os.setValor(valor);
		os.setConclusao(conclusao);
		repository.persist(os);

		// send saldoARefletirNoCliente to a kafka topic

		return os;
	}

}
