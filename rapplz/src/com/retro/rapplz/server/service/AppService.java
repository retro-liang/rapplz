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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppIndex;
import com.retro.rapplz.server.datastore.entity.AppTag;
import com.retro.rapplz.server.datastore.entity.AppTagIndex;
import com.retro.rapplz.server.datastore.entity.Profile;
import com.retro.rapplz.server.datastore.entity.User;
import com.retro.rapplz.server.datastore.service.AppDBService;
import com.retro.rapplz.server.datastore.service.AppIndexDBService;
import com.retro.rapplz.server.datastore.service.AppTagDBService;
import com.retro.rapplz.server.datastore.service.AppTagIndexDBService;
import com.retro.rapplz.server.datastore.service.ProfileDBService;
import com.retro.rapplz.server.datastore.service.UserDBService;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("appService")
public class AppService
{
	private static final Logger logger = Logger.getLogger(AppService.class.getName());
	
	private AppDBService appDBService = new AppDBService();
	private AppIndexDBService appIndexDBService = new AppIndexDBService();
	private AppTagDBService appTagDBService = new AppTagDBService();
	private AppTagIndexDBService appTagIndexDBService = new AppTagIndexDBService();
	private UserDBService userDBService = new UserDBService();
	private ProfileDBService profileDBService = new ProfileDBService();
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public App getById(@PathParam("id") String id)
	{
		return appDBService.getAppById(id);
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
		logger.info("getTaggedApps get invoked: " + tagName);
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
	@Path("recommend")
	@Consumes("application/x-www-form-urlencoded")
	public String recommend(@Context HttpServletRequest request, @FormParam("userId") String userId, @FormParam("appId") String appId, @FormParam("os") String os, @FormParam("name") String name, @FormParam("icon") String icon, @FormParam("link") String link, @FormParam("price") String price)
	{
		logger.info("User [id=" + userId + "] is trying to recommend an app [id=" + appId + " name=" + name + "] from ip [" + request.getRemoteAddr() + "]...");
		if(userId != null && !userId.equals("") && appId != null && !appId.equals(""))
		{
			Key<AppTag> appTagKey = appTagDBService.getAppTagKey("recommended");
			if(appTagKey == null)
			{
				appTagKey = appTagDBService.saveAppTag("recommended");
			}
			
			App app = appDBService.getAppById(appId);
			if(app == null)
			{
				app = new App();
				app.setId(appId);
				app.setOs(os);
				app.setName(name);
				app.setImage(icon);
				app.setLink(link);
				app.setPrice(price);
				appDBService.saveApp(app);
			}
			
			Key<App> appKey = appDBService.getAppKeyById(appId);
			Key<User> userKey = userDBService.getUserKey(userId);
			
			AppIndex appIndex = appIndexDBService.getAppIndexByUseKey(userKey);
			if(appIndex == null)
			{
				appIndex = new AppIndex();
				appIndex.setUser(userKey);
				appIndex.getApps().add(appKey);
				appIndexDBService.saveAppIndex(appIndex);
			}
			else if(!appIndex.getApps().contains(appKey))
			{
				appIndex.getApps().add(appKey);
				appIndexDBService.saveAppIndex(appIndex);
			}
			
			Key<AppTagIndex> appTagIndexKey = appTagIndexDBService.getAppTagIndexKey(appTagKey);
			if(appTagIndexKey == null)
			{			
				appTagIndexKey = appTagIndexDBService.saveAppTagIndex(appTagKey, appKey);
			}
			
			
			
			AppTagIndex appTagIndex = appTagIndexDBService.getAppTagIndex(appTagIndexKey);
			if(!appTagIndex.getApps().contains(appKey) || !appTagIndex.getUsers().contains(userKey))
			{
				app.setRecommendedCount(app.getRecommendedCount() + 1);
				appDBService.saveApp(app);
				appTagIndex.getApps().add(appKey);			
				appTagIndex.getUsers().add(userKey);
				logger.info("User [" + userKey + "] recommends a new app [" + appKey + "] successfully.");
				appTagIndexDBService.saveAppTagIndex(appTagIndex);
			}
			else
			{
				logger.info("User [" + userKey + "] has already recommended app [" + appKey + "] once.");
			}
			
			//need optimize, better put it to a queue
			logger.info("Broadcasting new recommendation...");
			ChannelService channelService = ChannelServiceFactory.getChannelService();					
			Profile profile = profileDBService.getProfileByKey(userDBService.getUser(userId).getProfile());			
			JSONObject obj = new JSONObject();
			obj.put("t", "ar");
			obj.put("uid", userId);
			obj.put("un", profile.getFirstName() + " " + profile.getLastName());
			obj.put("ua", profile.getAvatar());
			obj.put("aid", appId);
			obj.put("an", app.getName());
			obj.put("ai", app.getImage().trim());
			channelService.sendMessage(new ChannelMessage("activity", obj.toString()));
			logger.info("Broadcasting new recommendation done. message: " + obj.toString());
			
			return appId.toString();
		}
		else
		{
			logger.warning("User id: " + userId + " app id: " + appId);
			return null;
		}
	}
	
	@GET
	@Path("deleteAll")
	public String deleteAll()
	{
		appDBService.deleteAllApps();
		return "OK";
	}
	
	@GET
	@Path("test")
	public String test(@QueryParam("id") String id)
	{
		App app = appDBService.getAppById(id);
		return appTagIndexDBService.getAppTagIndexByApp(app).toString();		
	}
}