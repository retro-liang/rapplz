package com.retro.rapplz.server.service;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppTag;
import com.retro.rapplz.server.datastore.service.AppDBService;
import com.retro.rapplz.server.datastore.service.AppTagDBService;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("appService")
public class AppService
{
	private static final Logger logger = Logger.getLogger(AppService.class.getName());
	
	private AppDBService appDBService = new AppDBService();
	private AppTagDBService appTagDBService = new AppTagDBService();
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public App getById(@PathParam("id") Long id)
	{
		return appDBService.searchAppById(id);
	}
	
	@GET
	@Path("allAppsSize")
	@Produces({MediaType.TEXT_HTML})
	public String getAppsSize()
	{
		logger.info("getAppsSize get invoked");
		return String.valueOf(appDBService.getAllAppsSize());
	}
	
	@GET
	@Path("tag/{tagName}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<App> getFeaturedApps(@PathParam("tagName") String tagName)
	{
		logger.info("getFeaturedApps get invoked");
		Key<AppTag> appTagKey = appTagDBService.getAppTagKey(tagName);
		if(appTagKey != null)
		{
			return appDBService.getAppsByTag(appTagKey);
		}
		else
		{
			return null;
		}
	}
	
	@GET
	@Path("all")
	@Produces({MediaType.APPLICATION_JSON})
	public List<App> getAll()
	{
		logger.info("getAll get invoked");
		return appDBService.getAllApps();
	}
	
	@POST
	@Path("recommand")
	public App recommand(@Context HttpServletRequest request)
	{
		logger.info(request.getParameter("id"));
		return null;
	}
	
	@GET
	@Path("deleteAll")
	public String deleteAll()
	{
		appDBService.deleteAllApps();
		return "OK";
	}

}