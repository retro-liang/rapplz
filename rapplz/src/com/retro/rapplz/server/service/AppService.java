package com.retro.rapplz.server.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.service.AppDBService;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("appService")
public class AppService
{
	private static final Logger logger = Logger.getLogger(AppService.class.getName());
	
	private AppDBService appDBService = new AppDBService();
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public App getById(@PathParam("id") Long id)
	{
		return appDBService.searchAppById(id);
	}
	
	@GET
	@Path("getAll")
	@Produces({MediaType.APPLICATION_JSON})
	public List<App> getAll()
	{
		logger.info("getAll get invoked");
		return appDBService.getAllApps();
	}
	
	@GET
	@Path("deleteAll")
	public String deleteAll()
	{
		appDBService.deleteAllApps();
		return "OK";
	}

}