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

import br.com.jitec.aps.cadastro.business.data.ClienteEmailDTO;
import br.com.jitec.aps.cadastro.business.data.ClienteTelefoneDTO;
import br.com.jitec.aps.cadastro.business.producer.ClienteProducer;
import br.com.jitec.aps.cadastro.data.model.CategoriaCliente;
import br.com.jitec.aps.cadastro.data.model.Cidade;
import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.cadastro.data.model.ClienteEmail;
import br.com.jitec.aps.cadastro.data.model.ClienteTelefone;
import br.com.jitec.aps.cadastro.data.repository.ClienteRepository;
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
	CidadeService cidadeService;

	@Inject
	CategoriaClienteService categClienteService;

	@Inject
	TipoTelefoneService tipoTelefoneService;

	@Inject
	ClienteProducer clienteProducer;

	public Paged<Cliente> getClientes(Pagination pagination, Integer codigo, String nomeOuRazaoSocial,
			Boolean ativo, final String sort) {
		String field = DEFAULT_SORT_FIELD;

		if (Objects.nonNull(sort)) {
			if (!SORTABLE_FIELDS.contains(sort)) {
				throw new InvalidDataException("Campo para ordenação inválido");
			}
			field = sort;
		}

		QueryBuilder builder = new QueryBuilder();
		builder.setSortBy(field);

		builder.addFilter(Objects.nonNull(codigo), "codigo = :codigo", "codigo", codigo);
		builder.addFilter(Objects.nonNull(ativo), "ativo = :ativo", "ativo", ativo);
		if (Objects.nonNull(nomeOuRazaoSocial)) {
			builder.addFilter("(upper(nome) like :nomeOuRazaoSocial OR upper(razaoSocial) like :nomeOuRazaoSocial)",
					"nomeOuRazaoSocial", "%" + nomeOuRazaoSocial.toUpperCase() + "%");
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

	@Transactional
	public Cliente create(Integer codigo, String nome, String razaoSocial, String contato, String rua,
			String complemento, String bairro, String cep, String homepage, String cnpj, String inscricaoEstadual,
			UUID cidadeUid, UUID categoriaUid, List<ClienteEmailDTO> emails, List<ClienteTelefoneDTO> telefones) {

		Cliente cliente = new Cliente();
		cliente.setCodigo(getCodigo(codigo));
		cliente.setNome(nome);
		cliente.setRazaoSocial(razaoSocial);
		cliente.setContato(contato);
		cliente.setRua(rua);
		cliente.setComplemento(complemento);
		cliente.setBairro(bairro);
		cliente.setCep(cep);
		cliente.setHomepage(homepage);
		cliente.setCnpj(cnpj);
		cliente.setInscricaoEstadual(inscricaoEstadual);
		if (Objects.nonNull(cidadeUid)) {
			cliente.setCidade(cidadeService.get(cidadeUid));
		}
		if (Objects.nonNull(categoriaUid)) {
			cliente.setCategoria(categClienteService.get(categoriaUid));
		}
		cliente.setAtivo(Boolean.TRUE);
		cliente.setSaldo(BigDecimal.ZERO);

		for (ClienteEmailDTO dto : emails) {
			cliente.addEmail(new ClienteEmail(dto.getEmail()));
		}

		for (ClienteTelefoneDTO dto : telefones) {
			cliente.addTelefone(buildClienteTelefone(dto));
		}

		repository.persist(cliente);
		clienteProducer.sendClienteNovo(cliente);
		return cliente;
	}

	private ClienteTelefone buildClienteTelefone(ClienteTelefoneDTO dto) {
		ClienteTelefone telefone = new ClienteTelefone();
		telefone.setNumero(dto.getNumero());
		if (Objects.nonNull(dto.getTipoTelefoneUid())) {
			telefone.setTipoTelefone(tipoTelefoneService.get(dto.getTipoTelefoneUid()));
		}
		return telefone;
	}

	private Integer getCodigo(Integer codigoRequest) {
		if (codigoRequest != null) {
			validateCodigo(codigoRequest);
			return codigoRequest;
		}

		Optional<Integer> opMaxCodigoCadastrado = repository.getMaiorCodigoCliente();
		if (opMaxCodigoCadastrado.isPresent()) {
			return opMaxCodigoCadastrado.get() + 1;
		} else {
			return 1;
		}
	}

	private void validateCodigo(Integer codigoRequest) {
		if (codigoRequest <= 0) {
			throw new InvalidDataException("O código deve ser maior que 0");
		}
		Optional<Cliente> op = repository.findByCodigo(codigoRequest);
		if (op.isPresent()) {
			throw new InvalidDataException("Já existe um cliente associado ao código informado");
		}
	}

	/**
	 * Update all Cliente's fields with the parameter values. If the parameter value
	 * is empty or null, the field's value will be erased
	 *
	 * @param clienteUid
	 * @param nome
	 * @param razaoSocial
	 * @param contato
	 * @param ativo
	 * @param rua
	 * @param complemento
	 * @param bairro
	 * @param cep
	 * @param homepage
	 * @param cnpj
	 * @param inscricaoEstadual
	 * @param cidadeUid
	 * @param categoriaClienteUid
	 * @param emails
	 * @param telefones
	 * @return the entity with all updated fields
	 */
	@Transactional
	public Cliente updateAll(UUID clienteUid, Integer version, String nome, String razaoSocial, String contato,
			Boolean ativo, String rua, String complemento, String bairro, String cep, String homepage, String cnpj,
			String inscricaoEstadual, UUID cidadeUid, UUID categoriaClienteUid, List<ClienteEmailDTO> emails,
			List<ClienteTelefoneDTO> telefones) {

		Cliente cliente = getComplete(clienteUid, version);

		cliente.setNome(nome);
		cliente.setRazaoSocial(razaoSocial);
		cliente.setContato(contato);
		cliente.setAtivo(Objects.nonNull(ativo) ? ativo : Boolean.FALSE);
		cliente.setRua(rua);
		cliente.setComplemento(complemento);
		cliente.setBairro(bairro);
		cliente.setCep(cep);
		cliente.setHomepage(homepage);
		cliente.setCnpj(cnpj);
		cliente.setInscricaoEstadual(inscricaoEstadual);
		cliente.setCidade(Objects.nonNull(cidadeUid) ? cidadeService.get(cidadeUid) : null);
		cliente.setCategoria(
				Objects.nonNull(categoriaClienteUid) ? categClienteService.get(categoriaClienteUid) : null);

		mergeEmails(cliente, emails);
		mergeTelefones(cliente, telefones);

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
	private void mergeEmails(Cliente cliente, List<ClienteEmailDTO> emails) {
		List<ClienteEmailDTO> newEmails = emails.stream().filter(upd -> upd.getEmailUid() == null)
				.collect(Collectors.toList());
		List<ClienteEmail> removedEmails = new ArrayList<>();

		for (ClienteEmail existing : cliente.getEmails()) {
			Optional<ClienteEmailDTO> optUpdated = emails.stream()
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

	private void mergeTelefones(Cliente cliente, List<ClienteTelefoneDTO> telefones) {
		List<ClienteTelefoneDTO> newTelefones = telefones.stream().filter(upd -> upd.getTelefoneUid() == null)
				.collect(Collectors.toList());
		List<ClienteTelefone> removedTelefones = new ArrayList<>();

		for (ClienteTelefone existing : cliente.getTelefones()) {
			Optional<ClienteTelefoneDTO> optUpdated = telefones.stream()
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
		newTelefones.stream().forEach(added -> cliente.addTelefone(buildClienteTelefone(added)));
	}

	/**
	 * Update each Cliente's field only when the related parameter has a meaningful
	 * value (is not null). Otherwise, the field value will not be changed
	 *
	 * @param clienteUid
	 * @param nome
	 * @param razaoSocial
	 * @param contato
	 * @param ativo
	 * @param rua
	 * @param complemento
	 * @param bairro
	 * @param cep
	 * @param homepage
	 * @param cnpj
	 * @param inscricaoEstadual
	 * @param cidadeUid
	 * @param categoriaUid
	 * @param emails
	 * @param telefones
	 * @return the entity with updated fields
	 */
	@Transactional
	public Cliente updateNotNull(UUID clienteUid, Integer version, String nome, String razaoSocial, String contato,
			Boolean ativo, String rua, String complemento, String bairro, String cep, String homepage, String cnpj,
			String inscricaoEstadual, UUID cidadeUid, UUID categoriaUid, List<ClienteEmailDTO> emails,
			List<ClienteTelefoneDTO> telefones) {

		Cliente cliente = getComplete(clienteUid, version);

		if (Objects.nonNull(nome)) {
			cliente.setNome(nome);
		}
		if (Objects.nonNull(razaoSocial)) {
			cliente.setRazaoSocial(razaoSocial);
		}
		if (Objects.nonNull(contato)) {
			cliente.setContato(contato);
		}
		if (Objects.nonNull(ativo)) {
			cliente.setAtivo(ativo);
		}
		if (Objects.nonNull(rua)) {
			cliente.setRua(rua);
		}
		if (Objects.nonNull(complemento)) {
			cliente.setComplemento(complemento);
		}
		if (Objects.nonNull(bairro)) {
			cliente.setBairro(bairro);
		}
		if (Objects.nonNull(cep)) {
			cliente.setCep(cep);
		}
		if (Objects.nonNull(homepage)) {
			cliente.setHomepage(homepage);
		}
		if (Objects.nonNull(cnpj)) {
			cliente.setCnpj(cnpj);
		}
		if (Objects.nonNull(inscricaoEstadual)) {
			cliente.setInscricaoEstadual(inscricaoEstadual);
		}
		if (Objects.nonNull(cidadeUid)) {
			cliente.setCidade(cidadeService.get(cidadeUid));
		}
		if (Objects.nonNull(categoriaUid)) {
			cliente.setCategoria(categClienteService.get(categoriaUid));
		}
		if (Objects.nonNull(emails)) {
			mergeEmails(cliente, emails);
		}
		if (Objects.nonNull(telefones)) {
			mergeTelefones(cliente, telefones);
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
