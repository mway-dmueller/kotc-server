package com.kotc.circuit.match.domain;

import com.kotc.db.validation.Validate;

public class ATPSetStatistics implements Validate {

	private int setsWon;
	private int setsLost;
	private int setsPlayed;

	public Integer getSetsWon() {
		return setsWon;
	}

	public void setSetsWon(final Integer setsWon) {
		this.setsWon = setsWon;
	}

	public Integer getSetsLost() {
		return setsLost;
	}

	public void setSetsLost(final Integer setsLost) {
		this.setsLost = setsLost;
	}

	public Integer getSetsPlayed() {
		return setsPlayed;
	}

	public void setSetsPlayed(final Integer setsPlayed) {
		this.setsPlayed = setsPlayed;
	}

	@Override
	public void validate() {
		final int sets = setsWon + setsLost;
		if (sets != setsPlayed) {
			throw new IllegalStateException("The amount of sets played differs!");
		}
	}
}