package br.com.jitec.aps.data.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTE")
public class Cliente extends APSEntity {

	@Column(name = "CODIGO")
	public Integer codigo;

	@Column(name = "NOME")
	public String nome;

	@Column(name = "RAZAO")
	public String razaoSocial;

	@Column(name = "CONTATO")
	public String contato;

	@Column(name = "ATIVO")
	public Boolean ativo;

	@Column(name = "SALDO")
	public BigDecimal saldo;

	@Column(name = "RUA")
	public String rua;

	@Column(name = "COMPL")
	public String complemento;

	@Column(name = "BAIRRO")
	public String bairro;

	@Column(name = "CEP")
	public String cep;

	@Column(name = "HOMEPAGE")
	public String homepage;

	@Column(name = "CNPJ")
	public String cnpj;

	@Column(name = "IE")
	public String inscricaoEstadual;

	@ManyToOne
	@JoinColumn(name = "CIDADE_ID")
	public Cidade cidade;

	@ManyToOne
	@JoinColumn(name = "CATEGORIA_CLIENTE_ID")
	public CategoriaCliente categoria;

}
