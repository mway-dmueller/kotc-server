package com.kotc.circuit.tournament.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.common.service.impl.AbstractServiceImpl;
import com.kotc.circuit.dao.impl.TournamentRepository;
import com.kotc.circuit.tournament.domain.Tournament;
import com.kotc.circuit.tournament.service.TournamentService;
import com.kotc.db.util.Query;

@Component
public class TournamentServiceImpl extends AbstractServiceImpl implements TournamentService {

	@Autowired
	private TournamentRepository repository;

	@Override
	public Tournament getTournament(final long tournamentId) {
		return repository.findOne(tournamentId);
	}

	@Override
	public Page<Tournament> getTournaments(final Query query) {
		return repository.findByQuery(Tournament.class, query);
	}

	@Override
	public Tournament saveTournament(final Tournament tournament) {

		final Long tournamentId = tournament.getId();
		if (tournamentId != null) {
			throw new UnsupportedOperationException("tournaments cannot be updated");
		}

		return repository.save(tournament);
	}

	@Override
	public boolean deleteTournament(final long tournamentId) {
		repository.delete(tournamentId);
		return true;
	}
}
