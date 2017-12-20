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
import com.kotc.circuit.tournament.service.TournamentEventService;
import com.kotc.db.util.Query;

@Component
@EnumerableValue("years")
public class YearEnumerable implements Enumerable {

	@Autowired
	private TournamentEventService tournamentEventService;

	@Override
	public Page<KeyValuePair> getKeyValuePairs(final Query query) {
		final List<KeyValuePair> keyValuePairs = new ArrayList<>();

		final List<Integer> years = tournamentEventService.getYears();
		for (final Integer year : years) {
			keyValuePairs.add(new KeyValuePair(String.valueOf(year)));
		}

		return PaginationUtils.paginate(keyValuePairs, query.getPageable());
	}
}
