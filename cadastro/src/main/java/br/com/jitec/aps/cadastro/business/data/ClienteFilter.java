package br.com.jitec.aps.cadastro.business.data;

public class ClienteFilter {

	private Integer codigo;
	private String nomeOuRazaoSocial;
	private Boolean ativo;

	public ClienteFilter() {
		// Default constructor
	}

	public ClienteFilter(Integer codigo, String nomeOuRazaoSocial, Boolean ativo) {
		super();
		this.codigo = codigo;
		this.nomeOuRazaoSocial = nomeOuRazaoSocial;
		this.ativo = ativo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNomeOuRazaoSocial() {
		return nomeOuRazaoSocial;
	}

	public void setNomeOuRazaoSocial(String nomeOuRazaoSocial) {
		this.nomeOuRazaoSocial = nomeOuRazaoSocial;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
