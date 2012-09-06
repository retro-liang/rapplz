package com.retro.rapplz.web.controller;

import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.service.AppService;
import com.retro.rapplz.service.CategoryService;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.AppInfo;
import com.retro.rapplz.web.dto.CategoryInfo;

@Controller
@RequestMapping("/app")
public class AppController extends MultiActionController
{
	private static final Logger logger = Logger.getLogger(AppController.class.getName());
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private CategoryService categoryService;
	
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
	public Set<CategoryInfo> loadCategoriesHandler()
	{
		logger.info("Loading categories from memcache...");
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		return (Set<CategoryInfo>)syncCache.get("categories");
	}
	
	@RequestMapping("category/{category}/index.html")
	public String categorizedAppsPage(@PathParam("category") String categoryName, @RequestParam("c") String categoryId, ModelMap model)
	{
		logger.info("Loading apps by category [" + categoryId + "] from memcache...");
		
		try
		{
			model.addAttribute("categoryName", categoryName.replaceAll("-", " "));
			Set<AppInfo> appInfos = appService.getAppInfosByCategory(Long.valueOf(categoryId));
			logger.info("loaded [" + appInfos.size() + "] apps with category [" + categoryName + "]");
			model.addAttribute("categorizedApps", appInfos);
		}
		catch (ApplicationServiceException e)
		{
			
		}
		return "categorized-apps";
	}
}