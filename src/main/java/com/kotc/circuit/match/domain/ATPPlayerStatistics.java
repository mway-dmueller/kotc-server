package com.kotc.circuit.match.domain;

import com.kotc.db.validation.Validate;

public class ATPPlayerStatistics implements Validate {

	private String playerId;

	private ATPSetStatistics setStatistics;

	private ATPGameStatistics serviceGameStatistics;
	private ATPGameStatistics returnGameStatistics;
	private ATPGameStatistics totalGameStatistics;

	private ATPServicePointStatistics servicePointStatistics;
	private ATPReturnPointStatistics returnPointStatistics;
	private ATPPointStatistics totalPointStatistics;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(final String playerId) {
		this.playerId = playerId;
	}

	public ATPSetStatistics getSetStatistics() {
		return setStatistics;
	}

	public void setSetStatistics(final ATPSetStatistics setStatistics) {
		this.setStatistics = setStatistics;
	}

	public ATPGameStatistics getServiceGameStatistics() {
		return serviceGameStatistics;
	}

	public void setServiceGameStatistics(final ATPGameStatistics serviceGameStatistics) {
		this.serviceGameStatistics = serviceGameStatistics;
	}

	public ATPGameStatistics getReturnGameStatistics() {
		return returnGameStatistics;
	}

	public void setReturnGameStatistics(final ATPGameStatistics returnGameStatistics) {
		this.returnGameStatistics = returnGameStatistics;
	}

	public ATPGameStatistics getTotalGameStatistics() {
		return totalGameStatistics;
	}

	public void setTotalGameStatistics(final ATPGameStatistics totalGameStatistics) {
		this.totalGameStatistics = totalGameStatistics;
	}

	public ATPServicePointStatistics getServicePointStatistics() {
		return servicePointStatistics;
	}

	public void setServicePointStatistics(final ATPServicePointStatistics servicePointStatistics) {
		this.servicePointStatistics = servicePointStatistics;
	}

	public ATPReturnPointStatistics getReturnPointStatistics() {
		return returnPointStatistics;
	}

	public void setReturnPointStatistics(final ATPReturnPointStatistics returnPointStatistics) {
		this.returnPointStatistics = returnPointStatistics;
	}

	public ATPPointStatistics getTotalPointStatistics() {
		return totalPointStatistics;
	}

	public void setTotalPointStatistics(final ATPPointStatistics totalPointStatistics) {
		this.totalPointStatistics = totalPointStatistics;
	}

	@Override
	public void validate() {
		if (setStatistics != null) {
			setStatistics.validate();
		}

		if (serviceGameStatistics != null) {
			serviceGameStatistics.validate();
		}

		if (returnGameStatistics != null) {
			returnGameStatistics.validate();
		}

		if (totalGameStatistics != null) {
			totalGameStatistics.validate();
		}

		if (serviceGameStatistics != null && returnGameStatistics != null && totalGameStatistics != null) {

			final int games = serviceGameStatistics.getGamesPlayed() + returnGameStatistics.getGamesPlayed();
			if (games != totalGameStatistics.getGamesPlayed()) {
				throw new IllegalStateException("The amount of total games played differs!");
			}
		}
	}
}
