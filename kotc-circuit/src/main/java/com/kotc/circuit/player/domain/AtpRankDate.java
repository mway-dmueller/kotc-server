package com.kotc.circuit.player.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "atp_rank_date")
public class AtpRankDate implements Serializable {
	private static final long serialVersionUID = -5901463459197193199L;

	@Id
	@Column(name = "date", nullable = false, updatable = false)
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}
}
