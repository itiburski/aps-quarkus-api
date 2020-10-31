package br.com.jitec.aps.business.service;

import java.math.BigDecimal;
import java.util.List;
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
	public Cliente create(Integer codigo, String nome, String razaoSocial, String contato, Boolean ativo, String rua,
			String complemento, String bairro, String cep, String homepage, String cnpj, String inscricaoEstadual,
			UUID cidadeUid, UUID categoriaUid) {

		Cliente cliente = new Cliente();
		cliente.setCodigo(getCodigo(codigo));
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

	@Transactional
	public Cliente update(UUID clienteUid, String nome, String razaoSocial, String contato, String rua,
			String complemento, String bairro, String cep, String homepage, String cnpj, String inscricaoEstadual,
			UUID cidadeUid, UUID categoriaUid) {

		Cliente cliente = get(clienteUid);

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

		repository.persist(cliente);
		return cliente;
	}

	@Transactional
	public void delete(UUID clienteUid) {
		Cliente cliente = get(clienteUid);
		repository.delete(cliente);
	}

}
