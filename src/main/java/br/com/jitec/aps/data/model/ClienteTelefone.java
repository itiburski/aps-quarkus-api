package br.com.jitec.aps.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTE_FONE")
public class ClienteTelefone extends APSEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENTE_ID")
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_FONE_ID")
	private TipoTelefone tipoTelefone;

	@Column(name = "NUMERO")
	private Integer numero;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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
		if (!(obj instanceof ClienteTelefone)) {
			return false;
		}
		return getUid() != null && getUid().equals(((ClienteTelefone) obj).getUid());
	}

	@Override
	public String toString() {
		return "ClienteTelefone [tipoTelefone=" + tipoTelefone + ", numero=" + numero + ", id=" + getId() + ", UUID="
				+ getUid() + "]";
	}

}
