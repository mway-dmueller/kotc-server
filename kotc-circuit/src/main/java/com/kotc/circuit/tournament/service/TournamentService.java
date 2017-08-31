package com.kotc.circuit.tournament.service;

import org.springframework.data.domain.Page;

import com.kotc.circuit.tournament.domain.Tournament;
import com.kotc.db.util.Query;

public interface TournamentService {

	Tournament getTournament(long tournamentId);

	Page<Tournament> getTournaments(Query query);

	Tournament saveTournament(Tournament tournament);

	boolean deleteTournament(long tournamentId);
}
