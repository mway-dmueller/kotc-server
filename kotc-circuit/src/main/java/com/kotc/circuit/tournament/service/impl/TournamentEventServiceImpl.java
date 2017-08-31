package com.kotc.circuit.tournament.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.dao.impl.TournamentEventRepository;
import com.kotc.circuit.tournament.domain.TournamentEvent;
import com.kotc.circuit.tournament.service.TournamentEventService;
import com.kotc.db.util.Query;

@Component
public class TournamentEventServiceImpl implements TournamentEventService {

	@Autowired
	private TournamentEventRepository repository;

	@Override
	public TournamentEvent getTournamentEvent(final long tournamentEventId) {
		return repository.findOne(tournamentEventId);
	}

	@Override
	public Page<TournamentEvent> getTournamentEvents(final Query query) {
		return repository.findByQuery(TournamentEvent.class, query);
	}

	@Override
	public TournamentEvent saveTournamentEvent(final TournamentEvent tournamentEvent) {

		final Long tournamentEventId = tournamentEvent.getId();
		if (tournamentEventId == null) {
			// create new entity
		} else {
			// update existing entity
			throw new UnsupportedOperationException();
		}

		return repository.save(tournamentEvent);
	}

	@Override
	public List<Integer> getYears() {
		final Page<TournamentEvent> tournamentEvents = getTournamentEvents(Query.createBuilder()
				.build());

		final List<Integer> years = new ArrayList<>();
		for (final TournamentEvent tournamentEvent : tournamentEvents.getContent()) {
			final Integer year = tournamentEvent.getYear();
			if (!years.contains(year)) {
				years.add(year);
			}
		}

		return years;
	}
}
