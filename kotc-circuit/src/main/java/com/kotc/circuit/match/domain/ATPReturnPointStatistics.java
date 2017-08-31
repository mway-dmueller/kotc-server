package com.kotc.circuit.match.domain;

public class ATPReturnPointStatistics extends ATPAbstractPointStatistics {

	private Integer breakPointsConverted;
	private Integer breakPointsLost;
	private Integer breakPointOpportunities;

	public Integer getBreakPointsConverted() {
		return breakPointsConverted;
	}

	public void setBreakPointsConverted(final Integer breakPointsConverted) {
		this.breakPointsConverted = breakPointsConverted;
	}

	public Integer getBreakPointsLost() {
		return breakPointsLost;
	}

	public void setBreakPointsLost(final Integer breakPointsLost) {
		this.breakPointsLost = breakPointsLost;
	}

	public Integer getBreakPointOpportunities() {
		return breakPointOpportunities;
	}

	public void setBreakPointOpportunities(final Integer breakPointOpportunities) {
		this.breakPointOpportunities = breakPointOpportunities;
	}

	@Override
	public void validate() {
		super.validate();

		if (breakPointsConverted == null || breakPointsLost == null || breakPointOpportunities == null) {
			return;
		}

		final int breakPoints = breakPointsConverted.intValue() + breakPointsLost.intValue();
		if (breakPoints != breakPointOpportunities.intValue()) {
			throw new IllegalStateException("The amount of break point opportunities differs!");
		}
	}
}