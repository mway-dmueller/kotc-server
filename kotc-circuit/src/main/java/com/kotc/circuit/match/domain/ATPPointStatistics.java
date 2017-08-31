package com.kotc.circuit.match.domain;

import com.kotc.db.validation.Validate;

public class ATPPointStatistics implements Validate {

	private Integer pointsWon;
	private Integer pointsLost;
	private Integer pointsPlayed;

	public Integer getPointsWon() {
		return pointsWon;
	}

	public void setPointsWon(final Integer pointsWon) {
		this.pointsWon = pointsWon;
	}

	public Integer getPointsLost() {
		return pointsLost;
	}

	public void setPointsLost(final Integer pointsLost) {
		this.pointsLost = pointsLost;
	}

	public Integer getPointsPlayed() {
		return pointsPlayed;
	}

	public void setPointsPlayed(final Integer pointsPlayed) {
		this.pointsPlayed = pointsPlayed;
	}

	@Override
	public void validate() {
		if (pointsWon == null || pointsLost == null || pointsPlayed == null) {
			return;
		}

		final int points = pointsWon.intValue() + pointsLost.intValue();
		if (points != pointsPlayed) {
			throw new IllegalStateException("Amount of points played differs!");
		}
	}
}