package com.kotc.circuit.dao.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kotc.circuit.tournament.domain.TournamentEvent;

@Component
public class TournamentEventRepository extends KotcRepository<TournamentEvent> {

	@Autowired
	public TournamentEventRepository(final EntityManager em) {
		super(TournamentEvent.class, em);
	}
}
