package com.kotc.circuit.tournament.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.kotc.circuit.tournament.domain.TournamentEvent;
import com.kotc.db.util.Query;

public interface TournamentEventService {

	TournamentEvent getTournamentEvent(long tournamentEventId);

	Page<TournamentEvent> getTournamentEvents(Query query);

	TournamentEvent saveTournamentEvent(TournamentEvent tournamentEvent);

	List<Integer> getYears();
}
