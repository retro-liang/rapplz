package com.retro.rapplz.server.service;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import com.retro.rapplz.server.datastore.entity.AppTagIndex;
import com.retro.rapplz.server.datastore.service.AppDBService;
import com.retro.rapplz.server.datastore.service.AppTagDBService;
import com.retro.rapplz.server.datastore.service.AppTagIndexDBService;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("appService")
public class AppService
{
	private static final Logger logger = Logger.getLogger(AppService.class.getName());
	
	private AppDBService appDBService = new AppDBService();
	private AppTagDBService appTagDBService = new AppTagDBService();
	private AppTagIndexDBService appTagIndexDBService = new AppTagIndexDBService();
	
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
	public List<App> getTaggedApps(@PathParam("tagName") String tagName)
	{
		logger.info("getFeaturedApps get invoked");
		AppTag appTag = appTagDBService.getAppTag(tagName);
		if(appTag != null)
		{
			return appTagIndexDBService.getAppsByAppTag(appTag);
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
	@Consumes("application/x-www-form-urlencoded")
	public App recommand(@Context HttpServletRequest request, @FormParam("id") Long id, @FormParam("name") String name, @FormParam("icon") String icon, @FormParam("link") String link, @FormParam("price") String price)
	{
		logger.info("id=" + id + " name=" + name + " link=" + link + " price=" + price);
		App app = new App();
		app.setId(id);
		app.setName(name);
		app.setImage(icon);
		app.setLink(link);
		app.setPrice(price);
		Key<App> appKey = appDBService.saveApp(app);
		Key<AppTag> appTagKey = appTagDBService.getAppTagKey("recommanded");
		if(appTagKey == null)
		{
			appTagKey = appTagDBService.saveAppTag("recommanded");
		}
		
		Key<AppTagIndex> appTagIndexKey = appTagIndexDBService.getAppTagIndexKey(appTagKey);
		if(appTagIndexKey == null)
		{			
			appTagIndexKey = appTagIndexDBService.saveAppTagIndex(appTagKey, appKey);
		}
		else
		{
			AppTagIndex appTagIndex = appTagIndexDBService.getAppTagIndex(appTagIndexKey);
			appTagIndex.getApps().add(appKey);
			appTagIndexDBService.saveAppTagIndex(appTagIndex);
		}		
		
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