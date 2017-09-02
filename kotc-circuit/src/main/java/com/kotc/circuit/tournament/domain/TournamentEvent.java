package com.kotc.circuit.tournament.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.kotc.circuit.common.domain.BasicEntity;
import com.kotc.circuit.common.domain.Surface;
import com.kotc.circuit.common.domain.Surrounding;
import com.kotc.circuit.match.domain.Match;

@Table
@Entity(name = "tournament_event")
public class TournamentEvent extends BasicEntity {
	private static final long serialVersionUID = 5777327544475703936L;

	public static final String PROPERTY_ID = "id";
	public static final String PROPERTY_YEAR = "year";
	public static final String PROPERTY_TOURNAMENT = "tournament";

	public static final String FK_TOURNAMENT_ID = "tournament_id";

	@NotNull
	@Column(name = "year", nullable = false, updatable = false)
	private Integer year;

	@NotNull
	@JoinColumn(name = "tournament_id", nullable = false, updatable = false)
	@ManyToOne(targetEntity = Tournament.class, optional = false)
	private Tournament tournament;

	@NotNull
	@Column(name = "from_date", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromDate;

	@NotNull
	@Column(name = "to_date", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date toDate;

	@NotNull
	@Column(name = "type", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private TournamentType type;

	@NotNull
	@Column(name = "surface", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Surface surface;

	@Column(name = "surrounding")
	@Enumerated(EnumType.STRING)
	private Surrounding surrounding;

	@OneToMany(targetEntity = Match.class, mappedBy = Match.PROPERTY_TOURNAMENT_EVENT)
	private Set<Match> matches;

	public Integer getYear() {
		return year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(final Tournament tournament) {
		this.tournament = tournament;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(final Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(final Date toDate) {
		this.toDate = toDate;
	}

	public TournamentType getType() {
		return type;
	}

	public void setType(final TournamentType type) {
		this.type = type;
	}

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(final Surface surface) {
		this.surface = surface;
	}

	public Surrounding getSurrounding() {
		return surrounding;
	}

	public void setSurrounding(final Surrounding surrounding) {
		this.surrounding = surrounding;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(final Set<Match> matches) {
		this.matches = matches;
	}
}
