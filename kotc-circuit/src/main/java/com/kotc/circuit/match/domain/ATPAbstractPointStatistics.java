package com.kotc.circuit.match.domain;

import javax.validation.constraints.Min;

public abstract class ATPAbstractPointStatistics extends ATPPointStatistics {

	@Min(0)
	private Integer firstServePointsWon;
	@Min(0)
	private Integer firstServePointsLost;
	@Min(0)
	private Integer firstServePointsPlayed;

	@Min(0)
	private Integer secondServePointsWon;
	@Min(0)
	private Integer secondServePointsLost;
	@Min(0)
	private Integer secondServePointsPlayed;

	public Integer getFirstServePointsWon() {
		return firstServePointsWon;
	}

	public void setFirstServePointsWon(final Integer firstServePointsWon) {
		this.firstServePointsWon = firstServePointsWon;
	}

	public Integer getFirstServePointsLost() {
		return firstServePointsLost;
	}

	public void setFirstServePointsLost(final Integer firstServePointsLost) {
		this.firstServePointsLost = firstServePointsLost;
	}

	public Integer getFirstServePointsPlayed() {
		return firstServePointsPlayed;
	}

	public void setFirstServePointsPlayed(final Integer firstServePointsPlayed) {
		this.firstServePointsPlayed = firstServePointsPlayed;
	}

	public Integer getSecondServePointsWon() {
		return secondServePointsWon;
	}

	public void setSecondServePointsWon(final Integer secondServePointsWon) {
		this.secondServePointsWon = secondServePointsWon;
	}

	public Integer getSecondServePointsLost() {
		return secondServePointsLost;
	}

	public void setSecondServePointsLost(final Integer secondServePointsLost) {
		this.secondServePointsLost = secondServePointsLost;
	}

	public Integer getSecondServePointsPlayed() {
		return secondServePointsPlayed;
	}

	public void setSecondServePointsPlayed(final Integer secondServePointsPlayed) {
		this.secondServePointsPlayed = secondServePointsPlayed;
	}

	@Override
	public void validate() {
		super.validate();

		validateFirstServePoints();
		validateSecondServePoints();
	}

	private void validateFirstServePoints() {
		if (firstServePointsWon == null || firstServePointsLost == null || firstServePointsPlayed == null) {
			return;
		}

		final int firstServePoints = firstServePointsWon + firstServePointsLost;
		if (firstServePoints != firstServePointsPlayed) {
			throw new IllegalStateException("The amount of first serve points differs!");
		}
	}

	private void validateSecondServePoints() {

	}
}