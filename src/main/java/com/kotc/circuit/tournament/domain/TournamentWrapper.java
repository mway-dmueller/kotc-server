package com.kotc.circuit.tournament.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

public class TournamentWrapper implements Serializable {
	private static final long serialVersionUID = -620319330323790481L;

	private final List<Tournament> tournaments;
	private final Long total;

	public TournamentWrapper(final List<Tournament> tournaments) {
		this.tournaments = tournaments;
		total = Integer.valueOf(tournaments.size()).longValue();
	}

	public TournamentWrapper(final Page<Tournament> tournaments) {
		this.tournaments = tournaments.getContent();
		total = tournaments.getTotalElements();
	}

	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public Long getTotal() {
		return total;
	}
}
