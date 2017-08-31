package com.kotc.circuit.dao.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kotc.circuit.tournament.domain.Tournament;

@Component
public class TournamentRepository extends KotcRepository<Tournament> {

	@Autowired
	public TournamentRepository(final EntityManager em) {
		super(Tournament.class, em);
	}
}
