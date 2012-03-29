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

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppTag;
import com.retro.rapplz.server.datastore.entity.AppTagIndex;
import com.retro.rapplz.server.datastore.entity.Profile;
import com.retro.rapplz.server.datastore.entity.User;
import com.retro.rapplz.server.datastore.service.AppDBService;
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
	private AppTagDBService appTagDBService = new AppTagDBService();
	private AppTagIndexDBService appTagIndexDBService = new AppTagIndexDBService();
	private UserDBService userDBService = new UserDBService();
	private ProfileDBService profileDBService = new ProfileDBService();
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public App getById(@PathParam("id") Long id)
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
	public String recommend(@Context HttpServletRequest request, @FormParam("userId") String userId, @FormParam("appId") Long appId, @FormParam("name") String name, @FormParam("icon") String icon, @FormParam("link") String link, @FormParam("price") String price)
	{
		logger.info("id=" + appId + " name=" + name + " link=" + link + " price=" + price);
		if(userId != null && !userId.equals("") && appId != null && appId != 0)
		{
			App app = appDBService.getAppById(appId);
			if(app == null)
			{
				app = new App();
				app.setId(appId);
				app.setName(name);
				app.setImage(icon);
				app.setLink(link);
				app.setPrice(price);
			}
			else
			{
				app.setRecommendedCount(app.getRecommendedCount() + 1);
			}
			Key<App> appKey = appDBService.saveApp(app);
			
			
			Key<AppTag> appTagKey = appTagDBService.getAppTagKey("recommended");
			if(appTagKey == null)
			{
				appTagKey = appTagDBService.saveAppTag("recommended");
			}
			
			Key<AppTagIndex> appTagIndexKey = appTagIndexDBService.getAppTagIndexKey(appTagKey);
			if(appTagIndexKey == null)
			{			
				appTagIndexKey = appTagIndexDBService.saveAppTagIndex(appTagKey, appKey);
			}
			
			Key<User> userKey = userDBService.getUserKey(userId);
			
			AppTagIndex appTagIndex = appTagIndexDBService.getAppTagIndex(appTagIndexKey);
			appTagIndex.getApps().add(appKey);			
			appTagIndex.getUsers().add(userKey);
			logger.info("appKey: " + appKey + " | userKey: " + userKey);
			appTagIndexDBService.saveAppTagIndex(appTagIndex);
			
			//need optimize, better put it to a queue
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			Profile profile = profileDBService.getProfileByKey(userDBService.getUser(userId).getProfile());
			StringBuilder sb = new StringBuilder();
			sb.append(userId).append("|");
			sb.append(profile.getFirstName() + " " + profile.getLastName()).append("|");
			sb.append(profile.getAvatar()).append("|");
			sb.append(appId).append("|");
			sb.append(app.getName()).append("|");
			sb.append(app.getImage()).append("|");
			channelService.sendMessage(new ChannelMessage("activity", sb.toString()));
			
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
}