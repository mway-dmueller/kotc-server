package com.kotc.circuit.tournament.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

public class TournamentEventWrapper implements Serializable {
	private static final long serialVersionUID = 1029002235028013704L;

	private final List<TournamentEvent> tournamentEvents;
	private final Long total;

	public TournamentEventWrapper(final List<TournamentEvent> tournamentEvents) {
		this.tournamentEvents = tournamentEvents;
		total = Integer.valueOf(tournamentEvents.size()).longValue();
	}

	public TournamentEventWrapper(final Page<TournamentEvent> result) {
		tournamentEvents = result.getContent();
		total = result.getTotalElements();
	}

	public List<TournamentEvent> getTournamentEvents() {
		return tournamentEvents;
	}

	public Long getTotal() {
		return total;
	}
}
