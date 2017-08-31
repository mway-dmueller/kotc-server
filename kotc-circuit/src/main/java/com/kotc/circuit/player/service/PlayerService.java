package com.kotc.circuit.player.service;

import org.springframework.data.domain.Page;

import com.kotc.circuit.player.domain.Player;
import com.kotc.db.util.Query;

public interface PlayerService {

	Player getPlayer(long playerId);

	Page<Player> getPlayers(Query getQuery);

	Player savePlayer(Player player);

	boolean deletePlayer(long playerId);
}
