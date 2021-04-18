package br.com.jitec.aps.cadastro.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.jitec.aps.commons.data.model.APSEntity;

@Entity
@Table(name = "CLIENTE_EMAIL")
public class ClienteEmail extends APSEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENTE_ID")
	private Cliente cliente;

	@Column(name = "EMAIL")
	private String email;

	public ClienteEmail() {
		// default constructor
	}

	public ClienteEmail(String email) {
		super();
		this.setEmail(email);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ClienteEmail)) {
			return false;
		}
		return getUid() != null && getUid().equals(((ClienteEmail) obj).getUid());
	}

	@Override
	public String toString() {
		return "ClienteEmail [email=" + email + ", id=" + getId() + ", UUID=" + getUid() + "]";
	}

}
