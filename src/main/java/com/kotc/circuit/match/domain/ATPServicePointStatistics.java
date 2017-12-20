package com.kotc.circuit.match.domain;

import javax.validation.constraints.Min;

public class ATPServicePointStatistics extends ATPAbstractPointStatistics {

	@Min(0)
	private int aces;
	@Min(0)
	private int doubleFaults;

	@Min(0)
	private int firstServes;
	@Min(0)
	private int firstServesPlayed;

	@Min(0)
	private int secondServes;
	@Min(0)
	private int secondServesPlayed;

	@Min(0)
	private Integer breakPointsSaved;
	@Min(0)
	private Integer breakPointsLost;
	@Min(0)
	private Integer breakPointsSuffered;

	public int getAces() {
		return aces;
	}

	public void setAces(final int aces) {
		this.aces = aces;
	}

	public int getDoubleFaults() {
		return doubleFaults;
	}

	public void setDoubleFaults(final int doubleFaults) {
		this.doubleFaults = doubleFaults;
	}

	public int getFirstServes() {
		return firstServes;
	}

	public void setFirstServes(final int firstServes) {
		this.firstServes = firstServes;
	}

	public int getFirstServesPlayed() {
		return firstServesPlayed;
	}

	public void setFirstServesPlayed(final int firstServesPlayed) {
		this.firstServesPlayed = firstServesPlayed;
	}

	public int getSecondServes() {
		return secondServes;
	}

	public void setSecondServes(final int secondServes) {
		this.secondServes = secondServes;
	}

	public int getSecondServesPlayed() {
		return secondServesPlayed;
	}

	public void setSecondServesPlayed(final int secondServesPlayed) {
		this.secondServesPlayed = secondServesPlayed;
	}

	public Integer getBreakPointsSaved() {
		return breakPointsSaved;
	}

	public void setBreakPointsSaved(final Integer breakPointsSaved) {
		this.breakPointsSaved = breakPointsSaved;
	}

	public Integer getBreakPointsLost() {
		return breakPointsLost;
	}

	public void setBreakPointsLost(final Integer breakPointsLost) {
		this.breakPointsLost = breakPointsLost;
	}

	public Integer getBreakPointsSuffered() {
		return breakPointsSuffered;
	}

	public void setBreakPointsSuffered(final Integer breakPointsSuffered) {
		this.breakPointsSuffered = breakPointsSuffered;
	}

	@Override
	public void validate() {
		super.validate();

		if (firstServes > firstServesPlayed) {
			throw new IllegalStateException("The amount of first serves is invalid!");
		}

		if (secondServes > secondServesPlayed) {
			throw new IllegalStateException("The amount of second serves is invalid!");
		}

		if (breakPointsSaved == null || breakPointsLost == null || breakPointsSuffered == null) {
			return;
		}

		final int breakPoints = breakPointsSaved.intValue() + breakPointsLost.intValue();
		if (breakPoints != breakPointsSuffered) {
			throw new IllegalStateException("The amount of break points suffered differs!");
		}
	}
}