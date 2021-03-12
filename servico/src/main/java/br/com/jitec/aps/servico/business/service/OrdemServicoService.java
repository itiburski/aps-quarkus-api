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

	@Transactional
	public OrdemServico create(UUID clienteUid, UUID tipoServicoUid, BigDecimal valor, String contato, String descricao,
			String observacao, OffsetDateTime entrada, OffsetDateTime agendadoPara, OffsetDateTime conclusao,
			OffsetDateTime entrega) {
		OrdemServico os = new OrdemServico();

		os.setValor(valor);
		os.setContato(contato);
		os.setDescricao(descricao);
		os.setObservacao(observacao);
		os.setEntrada(entrada);
		os.setAgendadoPara(agendadoPara);
		os.setConclusao(conclusao);
		os.setEntrega(entrega);
		os.setNumero(repository.getNextNumeroOS());

		if (Objects.nonNull(clienteUid)) {
			os.setCliente(clienteService.get(clienteUid));
		}
		if (Objects.nonNull(tipoServicoUid)) {
			os.setTipoServico(tipoServicoService.get(tipoServicoUid));
		}

		repository.persist(os);

		return os;
	}

}
