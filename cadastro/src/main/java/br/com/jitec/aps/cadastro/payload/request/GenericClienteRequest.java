package br.com.jitec.aps.cadastro.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.jitec.aps.commons.util.validator.OnlyDigits;

public abstract class GenericClienteRequest {

	@NotBlank
	@Size(max = 60)
	private String nome;

	@NotBlank
	@Size(max = 60)
	private String razaoSocial;

	@Size(max = 60)
	private String contato;

	@Size(max = 60)
	private String rua;

	@Size(max = 30)
	private String complemento;

	@Size(max = 40)
	private String bairro;

	@Size(max = 8)
	@OnlyDigits
	private String cep;

	@Size(max = 80)
	private String homepage;

	@Size(max = 14)
	@OnlyDigits
	private String cnpj;

	@Size(max = 20)
	private String inscricaoEstadual;

	private UUID cidadeUid;
	private UUID categoriaClienteUid;

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

	public UUID getCategoriaClienteUid() {
		return categoriaClienteUid;
	}

	public void setCategoriaClienteUid(UUID categoriaClienteUid) {
		this.categoriaClienteUid = categoriaClienteUid;
	}

}
