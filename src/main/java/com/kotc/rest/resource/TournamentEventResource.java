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

import com.kotc.circuit.tournament.domain.TournamentEvent;
import com.kotc.circuit.tournament.domain.TournamentEventWrapper;
import com.kotc.circuit.tournament.service.TournamentEventService;
import com.kotc.db.util.QueryUtils;

@Component
@Path("/tournamentEvents")
@Produces(MediaType.APPLICATION_JSON)
public class TournamentEventResource extends BasicResource {

	@Autowired
	private TournamentEventService tournamentEventService;

	@GET
	@Path("{uuid}")
	public TournamentEvent getTournamentEvent(@PathParam("uuid") final long tournamentEventId) {
		return tournamentEventService.getTournamentEvent(tournamentEventId);
	}

	@GET
	public TournamentEventWrapper getTournamentEvents(
			@QueryParam("filter") final String filter,
			@QueryParam("sortOrder") final String sortOrder,
			@QueryParam("page") @DefaultValue("0") final int page,
			@QueryParam("size") @DefaultValue("30") final int size) {

		return new TournamentEventWrapper(tournamentEventService.getTournamentEvents(
				QueryUtils.getQuery(filter, sortOrder, page, size)));
	}
}
