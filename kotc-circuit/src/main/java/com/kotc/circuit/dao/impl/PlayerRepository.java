package com.kotc.circuit.dao.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kotc.circuit.player.domain.Player;

@Component
public class PlayerRepository extends KotcRepository<Player> {

	@Autowired
	public PlayerRepository(final EntityManager em) {
		super(Player.class, em);
	}
}
