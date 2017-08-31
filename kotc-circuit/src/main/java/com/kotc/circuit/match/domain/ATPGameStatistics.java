package com.kotc.circuit.match.domain;

import com.kotc.db.validation.Validate;

public class ATPGameStatistics implements Validate {

	private int gamesWon;
	private int gamesLost;
	private int gamesPlayed;

	public Integer getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(final int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(final int gamesLost) {
		this.gamesLost = gamesLost;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(final int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	@Override
	public void validate() {
		final int games = gamesWon + gamesLost;
		if (games != gamesPlayed) {
			throw new IllegalStateException("The amount of games played differs!");
		}
	}
}