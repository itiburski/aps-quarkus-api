package br.com.jitec.aps.cadastro.business.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import br.com.jitec.aps.cadastro.business.data.ClienteFilter;
import br.com.jitec.aps.cadastro.business.producer.ClienteProducer;
import br.com.jitec.aps.cadastro.data.model.CategoriaCliente;
import br.com.jitec.aps.cadastro.data.model.Cidade;
import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.cadastro.data.model.ClienteEmail;
import br.com.jitec.aps.cadastro.data.model.ClienteTelefone;
import br.com.jitec.aps.cadastro.data.repository.ClienteRepository;
import br.com.jitec.aps.cadastro.payload.mapper.ClienteMapper;
import br.com.jitec.aps.cadastro.payload.request.ClienteCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteEmailCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteEmailUpdateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneUpdateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteUpdateRequest;
import br.com.jitec.aps.commons.business.data.ClienteSaldoDto;
import br.com.jitec.aps.commons.business.exception.DataNotFoundException;
import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.commons.business.util.Paged;
import br.com.jitec.aps.commons.business.util.Pagination;
import br.com.jitec.aps.commons.business.util.QueryBuilder;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class ClienteService {

	private static final Logger LOG = Logger.getLogger(ClienteService.class);

	private static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";
	private static final String CLIENTE_NAO_ENCONTRADO_VERSION = "Cliente não encontrado para versao especificada";
	private static final List<String> SORTABLE_FIELDS = Arrays.asList("codigo", "nome", "razaoSocial");
	private static final String DEFAULT_SORT_FIELD = "id";

	@Inject
	ClienteRepository repository;

	@Inject
	ClienteMapper clienteMapper;

	@Inject
	CidadeService cidadeService;

	@Inject
	CategoriaClienteService categClienteService;

	@Inject
	TipoTelefoneService tipoTelefoneService;

	@Inject
	ClienteProducer clienteProducer;

	public Paged<Cliente> getClientes(Pagination pagination, ClienteFilter filter, final String sort) {
		String field = DEFAULT_SORT_FIELD;

		if (Objects.nonNull(sort)) {
			if (!SORTABLE_FIELDS.contains(sort)) {
				throw new InvalidDataException("Campo para ordenação inválido");
			}
			field = sort;
		}

		QueryBuilder builder = new QueryBuilder();
		builder.setSortBy(field);

		builder.addFilter(Objects.nonNull(filter.getCodigo()), "codigo = :codigo", "codigo", filter.getCodigo());
		builder.addFilter(Objects.nonNull(filter.getAtivo()), "ativo = :ativo", "ativo", filter.getAtivo());
		if (Objects.nonNull(filter.getNomeOuRazaoSocial())) {
			builder.addFilter("(upper(nome) like :nomeOuRazaoSocial OR upper(razaoSocial) like :nomeOuRazaoSocial)",
					"nomeOuRazaoSocial", "%" + filter.getNomeOuRazaoSocial().toUpperCase() + "%");
		}

		PanacheQuery<Cliente> query = repository.find(builder.getQuery(), builder.getParams())
				.page(Page.of(pagination.getPageZeroBased(), pagination.getSize()));
		return new Paged<Cliente>(query.list(), query.pageCount(), query.count());
	}

	public Cliente get(UUID clienteUid) {
		return repository.findByUid(clienteUid).orElseThrow(() -> new DataNotFoundException(CLIENTE_NAO_ENCONTRADO));
	}

	private Cliente get(UUID clienteUid, Integer version) {
		return repository.findByUidVersion(clienteUid, version)
				.orElseThrow(() -> new DataNotFoundException(CLIENTE_NAO_ENCONTRADO_VERSION));
	}

	public Cliente getComplete(UUID clienteUid) {
		return getComplete(clienteUid, null);
	}

	public Cliente getComplete(UUID clienteUid, Integer version) {
		QueryBuilder builder = new QueryBuilder();
		builder.addFilter("c.uid = :clienteUid", "clienteUid", clienteUid);
		builder.addFilter(Objects.nonNull(version), "c.version = :version", "version", version);

		Optional<Cliente> clienteOp = repository.find(
				"from Cliente c left join fetch c.emails left join fetch c.cidade left join fetch c.categoria where "
						+ builder.getQuery(),
				builder.getParams()).singleResultOptional();
		if (clienteOp.isEmpty()) {
			throw new DataNotFoundException(
					Objects.nonNull(version) ? CLIENTE_NAO_ENCONTRADO_VERSION : CLIENTE_NAO_ENCONTRADO);
		}
		Cliente cliente = clienteOp.get();
		cliente.getTelefones(); // solve org.hibernate.LazyInitializationException
		return cliente;
	}

	/**
	 * Create a new Cliente
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	public Cliente create(ClienteCreateRequest request) {
		Cliente cliente = clienteMapper.toCliente(request);

		cliente.setCodigo(repository.getNextCodigoCliente());
		cliente.setAtivo(Boolean.TRUE);
		cliente.setSaldo(BigDecimal.ZERO);

		if (Objects.nonNull(request.getCidadeUid())) {
			cliente.setCidade(cidadeService.get(request.getCidadeUid()));
		}
		if (Objects.nonNull(request.getCategoriaClienteUid())) {
			cliente.setCategoria(categClienteService.get(request.getCategoriaClienteUid()));
		}
		for (ClienteEmailCreateRequest cecr : request.getEmails()) {
			cliente.addEmail(new ClienteEmail(cecr.getEmail()));
		}
		for (ClienteTelefoneCreateRequest ctcr : request.getTelefones()) {
			cliente.addTelefone(buildClienteTelefone(ctcr.getNumero(), ctcr.getTipoTelefoneUid()));
		}

		repository.persist(cliente);
		clienteProducer.sendClienteNovo(cliente);
		return cliente;
	}

	private ClienteTelefone buildClienteTelefone(Integer numero, UUID tipoTelefoneUid) {
		ClienteTelefone telefone = new ClienteTelefone();
		telefone.setNumero(numero);
		if (Objects.nonNull(tipoTelefoneUid)) {
			telefone.setTipoTelefone(tipoTelefoneService.get(tipoTelefoneUid));
		}
		return telefone;
	}

	/**
	 * Update all Cliente's fields with the parameter values. If the parameter value
	 * is empty or null, the field's value will be erased
	 *
	 * @param clienteUid
	 * @param version
	 * @param request
	 * @return the entity with all updated fields
	 */
	@Transactional
	public Cliente updateAll(UUID clienteUid, Integer version, ClienteUpdateRequest request) {
		Cliente cliente = getComplete(clienteUid, version);
		clienteMapper.update(request, cliente);

		cliente.setCidade(Objects.nonNull(request.getCidadeUid()) ? cidadeService.get(request.getCidadeUid()) : null);
		cliente.setCategoria(Objects.nonNull(request.getCategoriaClienteUid())
				? categClienteService.get(request.getCategoriaClienteUid())
				: null);

		mergeEmails(cliente, request.getEmails());
		mergeTelefones(cliente, request.getTelefones());

		repository.persist(cliente);
		clienteProducer.sendClienteAtualizado(cliente);
		return cliente;
	}

	/**
	 * Merge the database email list with the submitted email list<br>
	 * <ul>
	 * <li>Insert emails without uid in the submitted list</li>
	 * <li>Update emails with same uid in both lists</li>
	 * <li>Remove emails with uid exists only in the database list</li>
	 * </ul>
	 * 
	 * @param cliente
	 * @param emails
	 */
	private void mergeEmails(Cliente cliente, List<ClienteEmailUpdateRequest> emails) {
		List<ClienteEmailUpdateRequest> newEmails = emails.stream().filter(upd -> upd.getEmailUid() == null)
				.collect(Collectors.toList());
		List<ClienteEmail> removedEmails = new ArrayList<>();

		for (ClienteEmail existing : cliente.getEmails()) {
			Optional<ClienteEmailUpdateRequest> optUpdated = emails.stream()
					.filter(updated -> existing.getUid().equals(updated.getEmailUid())).findFirst();
			if (optUpdated.isPresent()) {
				existing.setEmail(optUpdated.get().getEmail());
			} else {
				removedEmails.add(existing);
			}
		}

		removedEmails.stream().forEach(removed -> cliente.removeEmail(removed));
		newEmails.stream().forEach(added -> cliente.addEmail(new ClienteEmail(added.getEmail())));
	}

	private void mergeTelefones(Cliente cliente, List<ClienteTelefoneUpdateRequest> telefones) {
		List<ClienteTelefoneUpdateRequest> newTelefones = telefones.stream().filter(upd -> upd.getTelefoneUid() == null)
				.collect(Collectors.toList());
		List<ClienteTelefone> removedTelefones = new ArrayList<>();

		for (ClienteTelefone existing : cliente.getTelefones()) {
			Optional<ClienteTelefoneUpdateRequest> optUpdated = telefones.stream()
					.filter(updated -> existing.getUid().equals(updated.getTelefoneUid())).findFirst();
			if (optUpdated.isPresent()) {
				existing.setNumero(optUpdated.get().getNumero());
				existing.setTipoTelefone(Objects.nonNull(optUpdated.get().getTipoTelefoneUid())
						? tipoTelefoneService.get(optUpdated.get().getTipoTelefoneUid())
						: null);
			} else {
				removedTelefones.add(existing);
			}
		}

		removedTelefones.stream().forEach(removed -> cliente.removeTelefone(removed));
		newTelefones.stream().forEach(
				added -> cliente.addTelefone(buildClienteTelefone(added.getNumero(), added.getTipoTelefoneUid())));
	}

	/**
	 * Update each Cliente's field only when the related parameter has a meaningful
	 * value (is not null). Otherwise, the field value will not be changed
	 *
	 * @param clienteUid
	 * @param nome
	 * @param request
	 * @return the entity with updated fields
	 */
	@Transactional
	public Cliente updateNotNull(UUID clienteUid, Integer version, ClienteUpdateRequest request) {

		Cliente cliente = getComplete(clienteUid, version);

		if (Objects.nonNull(request.getNome())) {
			cliente.setNome(request.getNome());
		}
		if (Objects.nonNull(request.getRazaoSocial())) {
			cliente.setRazaoSocial(request.getRazaoSocial());
		}
		if (Objects.nonNull(request.getContato())) {
			cliente.setContato(request.getContato());
		}
		if (Objects.nonNull(request.getAtivo())) {
			cliente.setAtivo(request.getAtivo());
		}
		if (Objects.nonNull(request.getRua())) {
			cliente.setRua(request.getRua());
		}
		if (Objects.nonNull(request.getComplemento())) {
			cliente.setComplemento(request.getComplemento());
		}
		if (Objects.nonNull(request.getBairro())) {
			cliente.setBairro(request.getBairro());
		}
		if (Objects.nonNull(request.getCep())) {
			cliente.setCep(request.getCep());
		}
		if (Objects.nonNull(request.getHomepage())) {
			cliente.setHomepage(request.getHomepage());
		}
		if (Objects.nonNull(request.getCnpj())) {
			cliente.setCnpj(request.getCnpj());
		}
		if (Objects.nonNull(request.getInscricaoEstadual())) {
			cliente.setInscricaoEstadual(request.getInscricaoEstadual());
		}
		if (Objects.nonNull(request.getCidadeUid())) {
			cliente.setCidade(cidadeService.get(request.getCidadeUid()));
		}
		if (Objects.nonNull(request.getCategoriaClienteUid())) {
			cliente.setCategoria(categClienteService.get(request.getCategoriaClienteUid()));
		}
		if (Objects.nonNull(request.getEmails())) {
			mergeEmails(cliente, request.getEmails());
		}
		if (Objects.nonNull(request.getTelefones())) {
			mergeTelefones(cliente, request.getTelefones());
		}

		repository.persist(cliente);
		clienteProducer.sendClienteAtualizado(cliente);
		return cliente;
	}

	@Transactional
	public void delete(UUID clienteUid, Integer version) {
		Cliente cliente = get(clienteUid, version);
		repository.delete(cliente);
	}

	public boolean existeClienteComCategoriaCliente(CategoriaCliente categoriaCliente) {
		Long qtd = repository.getQtdClientePorCategoria(categoriaCliente.getId());
		return qtd > 0;
	}

	public boolean existeClienteComCidade(Cidade cidade) {
		Long qtd = repository.getQtdClientePorCidade(cidade.getId());
		return qtd > 0;
	}

	@Transactional
	public void handleSaldoUpdate(ClienteSaldoDto dto) {
		Optional<Cliente> opCliente = repository.findByUid(dto.getClienteUid());

		if (opCliente.isPresent()) {
			Cliente cliente = opCliente.get();
			cliente.setSaldo(cliente.getSaldo().add(dto.getVariacaoSaldo()));
			repository.persist(cliente);

			LOG.infof("Saldo do cliente %s atualizado", dto);
		} else {
			LOG.warnf("Cliente %s nao encontrado, nao foi possivel atualizar o saldo", dto.getClienteUid());
		}

	}

}
