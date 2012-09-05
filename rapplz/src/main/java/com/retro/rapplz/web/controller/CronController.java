package com.retro.rapplz.web.controller;

import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.retro.rapplz.service.AppService;
import com.retro.rapplz.service.CategoryService;
import com.retro.rapplz.web.dto.AppInfo;
import com.retro.rapplz.web.dto.CategoryInfo;

@Controller
@RequestMapping("/cron")
public class CronController
{
	private static final Logger logger = Logger.getLogger(CronController.class.getName());
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("load-apps")
    public void loadAppsHandler(HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("Cron job request from : " + request.getRemoteAddr());
		try
		{
			long start = System.currentTimeMillis();
			Set<AppInfo> appInfo = appService.getAppInfos();
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
			syncCache.put("apps", appInfo);
			logger.info("Retrieved [" + appInfo.size() + "] apps in " + (System.currentTimeMillis() - start) + " milliseconds.");
		}
		catch(Exception e)
		{
			logger.severe("Load apps cron job failed: " + e);
		}
    }
	
	@RequestMapping("load-categories")
    public void loadCategoriesHandler(HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("Cron job request from : " + request.getRemoteAddr());
		try
		{
			long start = System.currentTimeMillis();
			Set<CategoryInfo> categoryInfo = categoryService.getCategoryInfos();
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
			syncCache.put("categories", categoryInfo);
			logger.info("Retrieved [" + categoryInfo.size() + "] categories in " + (System.currentTimeMillis() - start) + " milliseconds.");
		}
		catch(Exception e)
		{
			logger.severe("Load categories cron job failed: " + e);
		}
    }
}