package com.kotc.circuit.player.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.common.service.impl.AbstractServiceImpl;
import com.kotc.circuit.dao.impl.PlayerRepository;
import com.kotc.circuit.player.domain.Player;
import com.kotc.circuit.player.service.PlayerService;
import com.kotc.db.util.Query;

@Component
public class PlayerServiceImpl extends AbstractServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository repository;

	@Override
	public Player getPlayer(final long playerId) {
		return repository.findOne(playerId);
	}

	@Override
	public Page<Player> getPlayers(final Query query) {
		return repository.findByQuery(Player.class, query);
	}

	@Override
	public Player savePlayer(final Player player) {

		if (player.getId() == null) {
			// create new player
		} else {
			// update existing player
		}

		return repository.save(player);
	}

	@Override
	public boolean deletePlayer(final long playerId) {
		repository.delete(playerId);
		return true;
	}
}
