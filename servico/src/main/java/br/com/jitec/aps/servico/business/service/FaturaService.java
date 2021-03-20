package br.com.jitec.aps.servico.business.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.data.repository.FaturaRepository;
import br.com.jitec.aps.servico.data.repository.OrdemServicoRepository;

@ApplicationScoped
public class FaturaService {

	@Inject
	OrdemServicoService ordemServicoService;

	@Inject
	FaturaRepository repository;

	@Inject
	OrdemServicoRepository osRepository;

	public List<Fatura> getAll() {
		return repository.list("order by codigo");
	}

	@Transactional
	public Fatura create(OffsetDateTime data, List<UUID> ordensServicoUid) {
		List<OrdemServico> ordensServico = ordemServicoService.get(ordensServicoUid);

		long clientesDistintos = ordensServico.stream().map(os -> os.getCliente().getUid()).distinct().count();
		if (clientesDistintos != 1) {
			throw new InvalidDataException("As ordens de serviço informadas devem ser do mesmo cliente");
		}

		long ordensServicoNaoLancadas = ordensServico.stream().map(OrdemServico::getLancamento).filter(Objects::isNull)
				.count();
		if (ordensServicoNaoLancadas > 0) {
			throw new InvalidDataException("Uma ou mais ordens de serviço informadas ainda não foram lançadas");
		}

		long ordensServicoJaFaturadas = ordensServico.stream().map(OrdemServico::getFatura).filter(Objects::nonNull)
				.count();
		if (ordensServicoJaFaturadas > 0) {
			throw new InvalidDataException("Uma ou mais ordens de serviço informadas já foram faturadas");
		}

		BigDecimal totalFatura = ordensServico.stream().map(OrdemServico::getValor).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		Fatura fatura = new Fatura();
		fatura.setCliente(ordensServico.get(0).getCliente());
		fatura.setValorTotal(totalFatura);
		fatura.setCodigo(repository.getNextNumeroFatura());
		fatura.setData(data);
		repository.persist(fatura);

		ordensServico.forEach(os -> os.setFatura(fatura));
		osRepository.persist(ordensServico);

		return fatura;
	}

}
