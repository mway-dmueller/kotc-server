package com.kotc.circuit.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasicEntity implements Serializable {
	private static final long serialVersionUID = 2304789357163856845L;

	public static final String PROPERTY_ID = "id";

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "id", nullable = false, updatable = false)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}
}
