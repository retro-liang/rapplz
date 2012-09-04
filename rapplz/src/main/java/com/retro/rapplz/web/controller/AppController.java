package com.retro.rapplz.web.controller;

import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.web.dto.AppInfo;

@Controller
@RequestMapping("/app")
public class AppController extends MultiActionController
{
	private static final Logger logger = Logger.getLogger(AppController.class.getName());
	
	@RequestMapping("{appName}.html")
	public String appPage(@PathVariable String appName, ModelMap model)
	{
		logger.info("Display app [" + appName + "] page");
		return "app";
	}
	
	@RequestMapping("load-apps")
	@ResponseBody
	public Set<AppInfo> loadAppsHandler()
	{
		logger.info("Loading apps from memcache...");
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		return (Set<AppInfo>)syncCache.get("apps");
	}
	
	@RequestMapping("load-categories")
	@ResponseBody
	public Set<Category> loadCategoriesHandler()
	{
		logger.info("Loading categories from memcache...");
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		return (Set<Category>)syncCache.get("categories");
	}
}