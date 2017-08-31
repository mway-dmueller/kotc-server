package com.kotc.circuit.player.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Ranking implements Serializable {
	private static final long serialVersionUID = -8569085960511114808L;

	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "rank")
	private String rank;

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(final String rank) {
		this.rank = rank;
	}
}
