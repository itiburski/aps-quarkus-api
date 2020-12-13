package br.com.jitec.aps.business.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.business.exception.DataNotFoundException;
import br.com.jitec.aps.business.exception.InvalidDataException;
import br.com.jitec.aps.business.util.QueryBuilder;
import br.com.jitec.aps.business.wrapper.Paged;
import br.com.jitec.aps.data.model.Cliente;
import br.com.jitec.aps.data.repository.ClienteRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class ClienteService {

	private static final List<String> SORTABLE_FIELDS = Arrays.asList("codigo", "nome", "razaoSocial");
	private static final String DEFAULT_SORT_FIELD = "id";

	@Inject
	ClienteRepository repository;

	@Inject
	CidadeService cidadeService;

	@Inject
	CategoriaClienteService categClienteService;

	public Paged<Cliente> getClientes(Integer page, Integer size, Integer codigo, String nomeOuRazaoSocial,
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
				.page(Page.of(page, size));
		return new Paged<Cliente>(query.list(), query.pageCount(), query.count());
	}

	public Cliente get(UUID clienteUid) {
		return repository.findByUid(clienteUid).orElseThrow(() -> new DataNotFoundException("Cliente não encontrado"));
	}

	public Cliente getComplete(UUID clienteUid) {
		Optional<Cliente> clienteOp = repository
				.find("from Cliente c left join fetch c.emails where c.uid = :uid", Parameters.with("uid", clienteUid))
				.singleResultOptional();
		return clienteOp.orElseThrow(() -> new DataNotFoundException("Cliente não encontrado"));
	}

	@Transactional
	public Cliente create(Integer codigo, String nome, String razaoSocial, String contato, String rua,
			String complemento, String bairro, String cep, String homepage, String cnpj, String inscricaoEstadual,
			UUID cidadeUid, UUID categoriaUid) {

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

		repository.persist(cliente);
		return cliente;
	}

	private Integer getCodigo(Integer codigoRequest) {
		if (codigoRequest != null) {
			validateCodigo(codigoRequest);
			return codigoRequest;
		}

		Integer maxCodigoCadastrado = repository.getMaiorCodigoCliente();
		return maxCodigoCadastrado + 1;
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
	 * @param categoriaUid
	 * @return the entity with all updated fields
	 */
	@Transactional
	public Cliente updateAll(UUID clienteUid, String nome, String razaoSocial, String contato, Boolean ativo,
			String rua, String complemento, String bairro, String cep, String homepage, String cnpj,
			String inscricaoEstadual, UUID cidadeUid, UUID categoriaUid) {

		Cliente cliente = get(clienteUid);

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
		cliente.setCategoria(Objects.nonNull(categoriaUid) ? categClienteService.get(categoriaUid) : null);

		repository.persist(cliente);
		return cliente;
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
	 * @return the entity with updated fields
	 */
	@Transactional
	public Cliente updateNotNull(UUID clienteUid, String nome, String razaoSocial, String contato, Boolean ativo,
			String rua, String complemento, String bairro, String cep, String homepage, String cnpj,
			String inscricaoEstadual, UUID cidadeUid, UUID categoriaUid) {

		Cliente cliente = get(clienteUid);

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

		repository.persist(cliente);
		return cliente;
	}

	@Transactional
	public void delete(UUID clienteUid) {
		Cliente cliente = get(clienteUid);
		repository.delete(cliente);
	}

}
