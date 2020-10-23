package br.com.jitec.aps.business.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.jitec.aps.business.exception.DataNotFoundException;
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
			String complemento, String bairro, String cep, String homepage, String cnpj, String inscricaEstadual,
			UUID cidadeUid, UUID categoriaUid) {

		Cliente cliente = new Cliente();
		cliente.codigo = getCodigo(codigo);
		cliente.nome = nome;
		cliente.razaoSocial = razaoSocial;
		cliente.contato = contato;
		cliente.ativo = ativo;
		cliente.rua = rua;
		cliente.complemento = complemento;
		cliente.bairro = bairro;
		cliente.cep = cep;
		cliente.homepage = homepage;
		cliente.cnpj = cnpj;
		cliente.inscricaoEstadual = inscricaEstadual;
		cliente.cidade = cidadeService.get(cidadeUid);
		cliente.categoria = categClienteService.get(categoriaUid);
		cliente.saldo = BigDecimal.ZERO;

		repository.persist(cliente);
		return cliente;
	}

	private Integer getCodigo(Integer codigoRequest) {
		if (codigoRequest != null) {
			return codigoRequest;
		}
		// TODO
//		Optional<Cliente> opMax = repository.find("max(codigo)").firstResultOptional();
//		if (opMax.isPresent()) {
//			return opMax.get().codigo + 1;
//		}
		return 1;
	}

	@Transactional
	public void delete(UUID clienteUid) {
		Cliente cliente = get(clienteUid);
		repository.delete(cliente);
	}

}
