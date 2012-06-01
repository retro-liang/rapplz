package com.retro.rapplz.server.cron;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppTag;
import com.retro.rapplz.server.datastore.service.AppTagDBService;
import com.retro.rapplz.server.datastore.service.AppTagIndexDBService;
import com.retro.rapplz.server.feed.FeedProcessor;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("cronService")
public class CronJobs
{
	private static final Logger logger = Logger.getLogger(FeedProcessor.class.getName());
	
	private AppTagDBService appTagDBService = new AppTagDBService();
	private AppTagIndexDBService appTagIndexDBService = new AppTagIndexDBService();
	
	public static final String DEFAULT_TAG_NAME = "recommended";
	
	@GET
	@Path("loadApps")
	public List<App> loadApps()
	{
		logger.info("Start loading apps...");
		Date start = new Date();
		AppTag appTag = appTagDBService.getAppTag(DEFAULT_TAG_NAME);
		if(appTag != null)
		{
			 List<App> apps =  appTagIndexDBService.getAppsByAppTag(appTag);
			 MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
			 syncCache.put("apps", apps);
			 logger.info("Retrieved [" + apps.size() + "] apps in " + (new Date().getTime() - start.getTime()));
			 return apps;
		}
		else
		{
			return null;
		}
	}
}