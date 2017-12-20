package com.kotc.circuit.match.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.common.service.impl.AbstractServiceImpl;
import com.kotc.circuit.dao.impl.MatchRepository;
import com.kotc.circuit.match.domain.Match;
import com.kotc.circuit.match.service.MatchService;
import com.kotc.db.util.Query;

@Component
public class MatchServiceImpl extends AbstractServiceImpl implements MatchService {

	@Autowired
	private MatchRepository repository;

	@Override
	public Match getMatch(final long matchId) {
		return repository.findOne(matchId);
	}

	@Override
	public Page<Match> getMatches(final Query query) {
		return repository.findByQuery(Match.class, query);
	}

	@Override
	public Match saveMatch(final Match match) {

		if (match.getId() == null) {
			// create new match
		} else {
			// update existing match
		}

		return repository.save(match);
	}

	@Override
	public boolean deleteMatch(final long matchId) {
		repository.delete(matchId);
		return true;
	}
}
