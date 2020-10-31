package br.com.jitec.aps.data.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class APSEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private UUID uid = UUID.randomUUID();

	// TODO not possible while using manual update
//	@Version
	@Column
	private Integer version = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
