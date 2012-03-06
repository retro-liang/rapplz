package com.retro.rapplz.server.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.retro.rapplz.server.config.RapplzConfig;

@Path("/appService")
public class AppService
{
	@GET
	@Path("search/{os}")
	public String searchApp(@PathParam("os") String os, @QueryParam("term") String term)
	{
		RapplzConfig.getInstance().getAppSearchAPI(os);
		return "not implemented yet";
	}
}