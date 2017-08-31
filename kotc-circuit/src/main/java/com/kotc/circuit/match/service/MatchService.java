package com.kotc.circuit.match.service;

import org.springframework.data.domain.Page;

import com.kotc.circuit.match.domain.Match;
import com.kotc.db.util.Query;

public interface MatchService {

	Match getMatch(long matchId);

	Page<Match> getMatches(Query query);

	Match saveMatch(Match match);

	boolean deleteMatch(long matchId);
}
