package com.kotc.circuit.player.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table
@Entity(name = "atp_rank_date")
public class AtpRankDate implements Serializable {
	private static final long serialVersionUID = -5901463459197193199L;

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@Temporal(TemporalType.DATE)
	private Date id;

	public Date getId() {
		return id;
	}

	public void setId(final Date id) {
		this.id = id;
	}
}
