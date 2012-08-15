package com.retro.rapplz.web.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.retro.rapplz.service.AppService;
import com.retro.rapplz.web.dto.AppInfo;

@Controller
@RequestMapping("/cron")
public class CronController
{
	private static final Logger logger = Logger.getLogger(CronController.class.getName());
	
	@Autowired
	private AppService appService;
	
	@RequestMapping("load-apps")
    public void loadAppsHandler(HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("Cron job request from : " + request.getRemoteAddr());
		try
		{
			long start = System.currentTimeMillis();
			List<AppInfo> appInfo = appService.loadAppInfos();
			logger.info("test category1: " + appInfo.get(0).getCategoryNames());
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
			syncCache.put("apps", appInfo);
			logger.info("test category2: " + appInfo.get(0).getCategoryNames());
			logger.info("Retrieved [" + appInfo.size() + "] apps in " + (System.currentTimeMillis() - start) + " milliseconds.");
		}
		catch(Exception e)
		{
			logger.severe("Load apps cron job failed: " + e);
		}
    }
}