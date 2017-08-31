package com.kotc.circuit.match.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class MatchSet implements Serializable {
	private static final long serialVersionUID = -2388743465542502240L;

	@Size(min = 0)
	@Column(name = "first", nullable = false, updatable = false)
	private Integer first;

	@Size(min = 0)
	@Column(name = "second")
	private String second;

	public int getFirst() {
		return first;
	}

	public void setFirst(final int first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(final String second) {
		this.second = second;
	}
}
