package com.kotc.circuit.player.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyTemporal;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.kotc.circuit.common.domain.BasicEntity;
import com.kotc.circuit.common.domain.Location;
import com.kotc.circuit.match.domain.Match;

@Table
@Entity(name = "player")
public class Player extends BasicEntity {
	private static final long serialVersionUID = -1147451643017572164L;

	public static final String PROPERTY_MATCHES = "matches";

	private static final String FK_PLAYER_ID = "player_id";

	@NotNull
	@Column(name = "atp_code", nullable = false, updatable = false)
	private String atpCode;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "gender", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender = Gender.MALE;

	@Column(name = "birth_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthDate;

	@Embedded
	private Location location;

	@Column(name = "elo", nullable = false)
	private int elo = 1000;

	@ElementCollection
	@CollectionTable(name = "player_atp_ranking", joinColumns = {
			@JoinColumn(name = FK_PLAYER_ID, referencedColumnName = BasicEntity.PROPERTY_ID, nullable = false,
					updatable = false) })
	@MapKeyTemporal(TemporalType.TIMESTAMP)
	@MapKeyColumn(name = "date", nullable = false, updatable = false)
	@Column(name = "rank", nullable = false, updatable = false)
	private Map<Date, Integer> atpRankings;

	@ManyToMany(targetEntity = Match.class)
	@JoinTable(name = "match_player", joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "match_id", referencedColumnName = "id"))
	private List<Match> matches;

	@OneToMany(targetEntity = Match.class, mappedBy = Match.PROPERTY_WINNER)
	private Set<Match> wonMatches;

	@OneToMany(targetEntity = Match.class, mappedBy = Match.PROPERTY_LOSER)
	private Set<Match> lostMatches;

	public String getAtpCode() {
		return atpCode;
	}

	public void setAtpCode(final String atpCode) {
		this.atpCode = atpCode;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public int getElo() {
		return elo;
	}

	public void setElo(final int elo) {
		this.elo = elo;
	}

	public Map<Date, Integer> getAtpRankings() {
		return atpRankings;
	}

	public void setAtpRankings(final Map<Date, Integer> atpRankings) {
		this.atpRankings = atpRankings;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(final List<Match> matches) {
		this.matches = matches;
	}

	public Set<Match> getWonMatches() {
		return wonMatches;
	}

	public void setWonMatches(final Set<Match> wonMatches) {
		this.wonMatches = wonMatches;
	}

	public Set<Match> getLostMatches() {
		return lostMatches;
	}

	public void setLostMatches(final Set<Match> lostMatches) {
		this.lostMatches = lostMatches;
	}
}
