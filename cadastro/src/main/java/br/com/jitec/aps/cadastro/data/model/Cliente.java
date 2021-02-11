package br.com.jitec.aps.cadastro.data.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTE")
public class Cliente extends APSEntity {

	@Column(name = "CODIGO")
	private Integer codigo;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "RAZAO")
	private String razaoSocial;

	@Column(name = "CONTATO")
	private String contato;

	@Column(name = "ATIVO")
	private Boolean ativo;

	@Column(name = "SALDO")
	private BigDecimal saldo;

	@Column(name = "RUA")
	private String rua;

	@Column(name = "COMPL")
	private String complemento;

	@Column(name = "BAIRRO")
	private String bairro;

	@Column(name = "CEP")
	private String cep;

	@Column(name = "HOMEPAGE")
	private String homepage;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "IE")
	private String inscricaoEstadual;

	@ManyToOne
	@JoinColumn(name = "CIDADE_ID")
	private Cidade cidade;

	@ManyToOne
	@JoinColumn(name = "CATEGORIA_CLIENTE_ID")
	private CategoriaCliente categoria;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClienteEmail> emails = new ArrayList<>();

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClienteTelefone> telefones = new ArrayList<>();

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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public CategoriaCliente getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaCliente categoria) {
		this.categoria = categoria;
	}

	public List<ClienteEmail> getEmails() {
		return emails;
	}

	public void setEmails(List<ClienteEmail> emails) {
		this.emails = emails;
	}

	public void addEmail(ClienteEmail clienteEmail) {
		emails.add(clienteEmail);
		clienteEmail.setCliente(this);
	}

	public void removeEmail(ClienteEmail clienteEmail) {
		emails.remove(clienteEmail);
		clienteEmail.setCliente(null);
	}

	public List<ClienteTelefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<ClienteTelefone> telefones) {
		this.telefones = telefones;
	}

	public void addTelefone(ClienteTelefone clienteTelefone) {
		telefones.add(clienteTelefone);
		clienteTelefone.setCliente(this);
	}

	public void removeTelefone(ClienteTelefone clienteTelefone) {
		telefones.remove(clienteTelefone);
		clienteTelefone.setCliente(null);
	}

}
