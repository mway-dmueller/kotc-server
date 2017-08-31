package com.kotc.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kotc.crawler.atp.service.CrawlerService;

@Component
@Path("/crawler")
@Produces(MediaType.APPLICATION_JSON)
public class CrawlerResource extends BasicResource {

	@Autowired
	private CrawlerService crawlerService;

	@GET
	public Response start() {
		return Response.ok().build();
	}

	@PUT
	@Path("synchronizePlayers")
	public Response synchronizePlayers() {
		crawlerService.synchronizeATPPlayers();

		return Response.ok().build();
	}

	@PUT
	@Path("synchronizeTournaments")
	public Response synchronizeTournaments() {
		crawlerService.synchronizeATPTournamentEvents();

		return Response.ok().build();
	}
}
