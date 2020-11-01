package br.com.jitec.aps.business.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.business.exception.DataNotFoundException;
import br.com.jitec.aps.business.exception.InvalidDataException;
import br.com.jitec.aps.data.model.Cliente;
import br.com.jitec.aps.data.repository.ClienteRepository;

@ApplicationScoped
public class ClienteService {

	@Inject
	ClienteRepository repository;

	@Inject
	CidadeService cidadeService;

	@Inject
	CategoriaClienteService categClienteService;

	public List<Cliente> getAll() {
		return repository.listAll();
	}

	public Cliente get(UUID clienteUid) {
		return repository.findByUid(clienteUid).orElseThrow(() -> new DataNotFoundException("Cliente não encontrado"));
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
		cliente.setCidade(cidadeService.get(cidadeUid));
		cliente.setCategoria(categClienteService.get(categoriaUid));
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
	 * Update all Cliente's fields with the parameter's values
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
		cliente.setAtivo(ativo);
		cliente.setRua(rua);
		cliente.setComplemento(complemento);
		cliente.setBairro(bairro);
		cliente.setCep(cep);
		cliente.setHomepage(homepage);
		cliente.setCnpj(cnpj);
		cliente.setInscricaoEstadual(inscricaoEstadual);
		cliente.setCidade(cidadeService.get(cidadeUid));
		cliente.setCategoria(categClienteService.get(categoriaUid));

		repository.persist(cliente);
		return cliente;
	}

	/**
	 * Update each Cliente's field only when the related parameter has a meaningful
	 * value (is not null)
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
