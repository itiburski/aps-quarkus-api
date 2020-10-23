package br.com.jitec.aps.rest.payload.request;

import java.util.UUID;

public class ClienteCreateRequest {

	private Integer codigo;
	private String nome;
	private String razaoSocial;
	private String contato;
	private Boolean ativo;
	private String rua;
	private String complemento;
	private String bairro;
	private String cep;
	private String homepage;
	private String cnpj;
	private String inscricaoEstadual;
	private UUID cidadeUid;
	private UUID categoriaUid;

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

	public UUID getCidadeUid() {
		return cidadeUid;
	}

	public void setCidadeUid(UUID cidadeUid) {
		this.cidadeUid = cidadeUid;
	}

	public UUID getCategoriaUid() {
		return categoriaUid;
	}

	public void setCategoriaUid(UUID categoriaUid) {
		this.categoriaUid = categoriaUid;
	}

}
