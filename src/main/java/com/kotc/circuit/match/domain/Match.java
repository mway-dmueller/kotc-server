package com.kotc.circuit.match.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.kotc.circuit.common.domain.BasicEntity;
import com.kotc.circuit.common.domain.Mode;
import com.kotc.circuit.common.domain.Surface;
import com.kotc.circuit.player.domain.Player;
import com.kotc.circuit.tournament.domain.TournamentEvent;

@Table
@Entity(name = "match")
public class Match extends BasicEntity {
	private static final long serialVersionUID = 6646706638618341457L;

	public static final String PROPERTY_WINNER = "winner";
	public static final String PROPERTY_LOSER = "loser";
	public static final String PROPERTY_TOURNAMENT_EVENT = "tournamentEvent";

	private static final String FK_MATCH_ID = "match_id";

	@NotNull
	@Column(name = "surface", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Surface surface;

	@NotNull
	@Column(name = "mode", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Mode mode;

	@NotNull
	@JoinColumn(name = "winner_id", referencedColumnName = BasicEntity.PROPERTY_ID, nullable = false, updatable = false)
	@ManyToOne(targetEntity = Player.class, optional = false)
	private Player winner;

	@NotNull
	@JoinColumn(name = "loser_id", referencedColumnName = BasicEntity.PROPERTY_ID, nullable = false, updatable = false)
	@ManyToOne(targetEntity = Player.class, optional = false)
	private Player loser;

	@NotNull
	@JoinColumn(name = "tournament_event_id", referencedColumnName = TournamentEvent.PROPERTY_ID, nullable = false,
			updatable = false)
	@ManyToOne(targetEntity = TournamentEvent.class, optional = false)
	private TournamentEvent tournamentEvent;

	@NotNull
	@Column(name = "date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@ElementCollection
	@CollectionTable(name = "match_set", joinColumns = @JoinColumn(name = FK_MATCH_ID))
	private Set<MatchSet> matchSets;

	@Column(name = "walkover", nullable = false, updatable = false)
	private boolean walkover;

	@NotNull
	@ManyToMany(targetEntity = Player.class, mappedBy = Player.PROPERTY_MATCHES)
	private List<Player> players;

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(final Surface surface) {
		this.surface = surface;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(final Mode mode) {
		this.mode = mode;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(final Player winner) {
		this.winner = winner;
	}

	public Player getLoser() {
		return loser;
	}

	public void setLoser(final Player loser) {
		this.loser = loser;
	}

	public TournamentEvent getTournamentEvent() {
		return tournamentEvent;
	}

	public void setTournamentEvent(final TournamentEvent tournamentEvent) {
		this.tournamentEvent = tournamentEvent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public Set<MatchSet> getMatchSets() {
		return matchSets;
	}

	public void setMatchSets(final Set<MatchSet> matchSets) {
		this.matchSets = matchSets;
	}

	public boolean isWalkover() {
		return walkover;
	}

	public void setWalkover(final boolean walkover) {
		this.walkover = walkover;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(final List<Player> players) {
		this.players = players;
	}
}
