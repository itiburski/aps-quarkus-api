package br.com.jitec.aps.rest.payload.response;

import java.math.BigDecimal;
import java.util.UUID;

public class ClienteResponse {

	private UUID uid;
	private Integer codigo;
	private String nome;
	private String razaoSocial;
	private String contato;
	private Boolean ativo;
	private BigDecimal saldo;
	private String rua;
	private String complemento;
	private String bairro;
	private String cep;
	private String homepage;
	private String cnpj;
	private String inscricaoEstadual;
	private CidadeResponse cidade;
	private CategoriaClienteResponse categoriaCliente;

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public CidadeResponse getCidade() {
		return cidade;
	}

	public void setCidade(CidadeResponse cidade) {
		this.cidade = cidade;
	}

	public CategoriaClienteResponse getCategoriaCliente() {
		return categoriaCliente;
	}

	public void setCategoriaCliente(CategoriaClienteResponse categoriaCliente) {
		this.categoriaCliente = categoriaCliente;
	}

}