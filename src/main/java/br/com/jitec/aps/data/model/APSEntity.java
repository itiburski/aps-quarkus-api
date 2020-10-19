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
	public Long id;

	@Column
	public UUID uid = UUID.randomUUID();

	// TODO not possible while using manual update
//	@Version
	@Column
	public Integer version = 0;

}
