package br.com.jitec.aps.rest.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.jitec.aps.rest.validation.ValidationMessages;

public abstract class GenericClienteRequest {

	@NotBlank(message = ValidationMessages.NOME_NOT_BLANK)
	@Size(max = 60, message = ValidationMessages.NOME_SIZE)
	private String nome;

	@NotBlank(message = ValidationMessages.RAZAO_SOCIAL_NOT_BLANK)
	@Size(max = 60, message = ValidationMessages.RAZAO_SOCIAL_SIZE)
	private String razaoSocial;

	@Size(max = 60, message = ValidationMessages.CONTATO_SIZE)
	private String contato;

	@Size(max = 60, message = ValidationMessages.RUA_SIZE)
	private String rua;

	@Size(max = 30, message = ValidationMessages.COMPLEMENTO_SIZE)
	private String complemento;

	@Size(max = 40, message = ValidationMessages.BAIRRO_SIZE)
	private String bairro;

	@Size(max = 8, message = ValidationMessages.CEP_SIZE)
	@Pattern(regexp = "^[0-9]*$", message = ValidationMessages.CEP_FORMAT)
	private String cep;

	@Size(max = 80, message = ValidationMessages.HOMEPAGE_SIZE)
	private String homepage;

	@Size(max = 14, message = ValidationMessages.CNPJ_SIZE)
	@Pattern(regexp = "^[0-9]*$", message = ValidationMessages.CNPJ_FORMAT)
	private String cnpj;

	@Size(max = 20, message = ValidationMessages.IE_SIZE)
	private String inscricaoEstadual;

	private UUID cidadeUid;
	private UUID categoriaUid;

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

	public UUID getCategoriaUid() {
		return categoriaUid;
	}

	public void setCategoriaUid(UUID categoriaUid) {
		this.categoriaUid = categoriaUid;
	}

}
