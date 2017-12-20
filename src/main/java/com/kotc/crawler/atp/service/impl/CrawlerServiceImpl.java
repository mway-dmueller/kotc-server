package com.kotc.crawler.atp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.kotc.circuit.common.domain.Location;
import com.kotc.circuit.player.domain.Gender;
import com.kotc.circuit.player.domain.Player;
import com.kotc.circuit.player.service.AtpRankingService;
import com.kotc.circuit.player.service.PlayerService;
import com.kotc.circuit.tournament.domain.Circuit;
import com.kotc.circuit.tournament.domain.Tournament;
import com.kotc.circuit.tournament.service.TournamentService;
import com.kotc.crawler.atp.domain.player.ATPPlayerRanking;
import com.kotc.crawler.atp.domain.tournament.ATPTournament;
import com.kotc.crawler.atp.domain.tournament.ATPTournamentEvent;
import com.kotc.crawler.atp.service.CrawlerService;
import com.kotc.db.filter.domain.StringFilter;
import com.kotc.db.util.Query;

@Service
public class CrawlerServiceImpl implements CrawlerService {

	private final ATPPlayersCrawler atpPlayersCrawler = new ATPPlayersCrawler();
	private final ATPTournamentsCrawler atpTournamentsCrawler = new ATPTournamentsCrawler();

	@Autowired
	private PlayerService playerService;
	@Autowired
	private AtpRankingService atpRankingService;
	@Autowired
	private TournamentService tournamentService;

	@Override
	public boolean synchronizeATPPlayers() {

		final Page<Player> players = playerService.getPlayers(Query.createBuilder().build());

		final Map<String, Player> atpPlayerMap = Maps.uniqueIndex(players.getContent(),
				(Function<Player, String>) player -> player.getAtpCode());

		final List<Date> persistedRankDates = atpRankingService.getRankDates();

		final List<Date> rankDates = atpPlayersCrawler.getRankDates();
		for (final Date rankDate : rankDates) {
			if (!persistedRankDates.contains(rankDate)) {
				atpRankingService.createRankDate(rankDate);
				continue;
			}

			final Set<ATPPlayerRanking> atpPlayerRankings = atpPlayersCrawler.getPlayerRankings(rankDate);
			for (final ATPPlayerRanking atpPlayerRanking : atpPlayerRankings) {
				final String atpPlayerCode = atpPlayerRanking.getCode();

				if (!atpPlayerMap.containsKey(atpPlayerCode)) {
					final Player player = new Player();
					player.setAtpCode(atpPlayerCode);
					player.setName(atpPlayerRanking.getName());
					player.setGender(Gender.MALE); // all ATP players are males

					atpPlayerMap.get(player);
				}

				// update the ATP rankings of the existing player
				final Player player = atpPlayerMap.get(atpPlayerCode);
				player.getAtpRankings().put(atpPlayerRanking.getRankDate(), atpPlayerRanking.getRanking());

				playerService.savePlayer(player);
			}
		}

		return true;
	}

	@Override
	public boolean synchronizeATPTournamentEvents() {

		final Page<Tournament> tournaments = tournamentService.getTournaments(Query.createBuilder()
				.filter(new StringFilter(Tournament.PROPERTY_CIRCUIT, Circuit.ATP.name()))
				.build());

		final Map<String, Tournament> atpTournamentMap = Maps.uniqueIndex(tournaments.getContent(),
				(Function<Tournament, String>) tournament -> tournament.getAtpCode());

		final List<ATPTournamentEvent> atpTournamentEvents = atpTournamentsCrawler.getAllTournamentEvents();
		for (final ATPTournamentEvent atpTournamentEvent : atpTournamentEvents) {
			final ATPTournament atpTournament = atpTournamentEvent.getTournament();
			final String atpTournamentCode = atpTournament.getCode();

			if (!atpTournamentMap.containsKey(atpTournamentCode)) {
				final Tournament tournament = new Tournament();
				tournament.setAtpCode(atpTournamentCode);
				tournament.setName(atpTournament.getName());
				tournament.setCircuit(Circuit.ATP);
				tournament.setLocation(new Location(atpTournament.getCity(), atpTournament.getCountry()));

				atpTournamentMap.put(atpTournamentCode, tournament);
			}

			final Tournament tournament = atpTournamentMap.get(atpTournamentCode);

		}

		return true;
	}
}
