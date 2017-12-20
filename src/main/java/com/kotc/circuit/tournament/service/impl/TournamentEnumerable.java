package com.kotc.circuit.tournament.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.enumerable.domain.KeyValuePair;
import com.kotc.circuit.enumerable.service.Enumerable;
import com.kotc.circuit.enumerable.util.EnumerableValue;
import com.kotc.circuit.enumerable.util.PaginationUtils;
import com.kotc.circuit.tournament.domain.Tournament;
import com.kotc.circuit.tournament.service.TournamentService;
import com.kotc.db.util.Query;

@Component
@EnumerableValue("tournaments")
public class TournamentEnumerable implements Enumerable {

	@Autowired
	private TournamentService tournamentService;

	@Override
	public Page<KeyValuePair> getKeyValuePairs(final Query query) {
		final List<KeyValuePair> keyValuePairs = new ArrayList<>();

		final Page<Tournament> tournaments = tournamentService.getTournaments(query);
		for (final Tournament tournament : tournaments.getContent()) {
			keyValuePairs.add(new KeyValuePair(tournament.getId().toString(), tournament.getName()));
		}

		return PaginationUtils.paginate(keyValuePairs, query.getPageable());
	}
}
