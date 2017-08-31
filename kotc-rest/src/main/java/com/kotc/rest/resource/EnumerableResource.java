package com.kotc.rest.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.enumerable.domain.KeyValuePair;
import com.kotc.circuit.enumerable.service.EnumerableService;
import com.kotc.db.util.QueryUtils;

@Component
@Path("/enumerables")
@Produces(MediaType.APPLICATION_JSON)
public class EnumerableResource extends BasicResource {

	@Autowired
	private EnumerableService enumerableService;

	@GET
	@Path("keyValuePairs/{enumerable}")
	public Page<KeyValuePair> getKeyValuePairs(@PathParam("enumerable") final String enumerable,
			@QueryParam("filter") final String filter,
			@QueryParam("sortOrder") final String sortOrder,
			@QueryParam("page") @DefaultValue("0") final int page,
			@QueryParam("size") @DefaultValue("30") final int size) {

		return enumerableService.getKeyValuePairs(enumerable,
				QueryUtils.getQuery(filter, sortOrder, page, size));
	}
}
