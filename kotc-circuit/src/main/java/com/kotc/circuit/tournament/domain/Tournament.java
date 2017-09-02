package com.kotc.circuit.tournament.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.kotc.circuit.common.domain.BasicEntity;
import com.kotc.circuit.common.domain.Location;

@Table
@Entity(name = "tournament")
public class Tournament extends BasicEntity {
	private static final long serialVersionUID = -4714989317477231287L;

	public static final String PROPERTY_ATP_CODE = "atpCode";
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_CIRCUIT = "circuit";

	@NotNull
	@Column(name = "atp_code", nullable = false, updatable = false)
	private String atpCode;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "circuit", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Circuit circuit = Circuit.ATP;

	@NotNull
	@Embedded
	private Location location;

	@OneToMany(targetEntity = TournamentEvent.class, mappedBy = BasicEntity.PROPERTY_ID,
			orphanRemoval = true, cascade = CascadeType.ALL)
	private List<TournamentEvent> tournamentEvents = new ArrayList<>();

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

	public Circuit getCircuit() {
		return circuit;
	}

	public void setCircuit(final Circuit circuit) {
		this.circuit = circuit;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public List<TournamentEvent> getTournamentEvents() {
		return tournamentEvents;
	}

	public void setTournamentEvents(final List<TournamentEvent> tournamentEvents) {
		this.tournamentEvents = tournamentEvents;
	}
}
