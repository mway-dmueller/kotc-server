package com.kotc.circuit.dao.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kotc.circuit.match.domain.Match;

@Component
public class MatchRepository extends KotcRepository<Match> {

	@Autowired
	public MatchRepository(final EntityManager em) {
		super(Match.class, em);
	}
}
