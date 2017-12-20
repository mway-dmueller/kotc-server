package com.kotc.rest.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kotc.circuit.tournament.domain.Tournament;
import com.kotc.circuit.tournament.domain.TournamentWrapper;
import com.kotc.circuit.tournament.service.TournamentService;
import com.kotc.db.util.QueryUtils;

@Component
@Path("/tournaments")
@Produces(MediaType.APPLICATION_JSON)
public class TournamentResource extends BasicResource {

	@Autowired
	private TournamentService tournamentService;

	@GET
	public TournamentWrapper getTournaments(
			@QueryParam("filter") final String filter,
			@QueryParam("sortOrder") final String sortOrder,
			@QueryParam("page") @DefaultValue("0") final int page,
			@QueryParam("size") @DefaultValue("30") final int size) {

		return new TournamentWrapper(tournamentService.getTournaments(
				QueryUtils.getQuery(filter, sortOrder, page, size)));
	}

	@GET
	@Path("{tournamentId}")
	public Tournament getTournament(@PathParam("tournamentId") final long tournamentId) {
		return tournamentService.getTournament(tournamentId);
	}
}
