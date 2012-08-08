package com.retro.rapplz.web.controller;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.UserService;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.AppInfo;
import com.retro.rapplz.web.util.AppInfoAssembler;

@Controller
public class RapplzController
{
	private static final Logger logger = Logger.getLogger(RapplzController.class.getName());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppInfoAssembler appInfoAssembler;
	
	@RequestMapping("/")
    public String homepage(HttpServletRequest request)
	{
		return "homepage";
    }
	
	@RequestMapping("/index.html")
    public String index(HttpServletRequest request)
	{
		return "homepage";
    }
	
	@RequestMapping("/load-apps")
	@ResponseBody
	public List<AppInfo> loadAppsHandler()
	{
		List<AppInfo> apps = new ArrayList<AppInfo>();
		logger.info("Loading apps from memcache...");
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		List<App> cachedApps = (List<App>)syncCache.get("apps");
		if(cachedApps != null)
		{
			for(App app : cachedApps)
			{
				apps.add(appInfoAssembler.buildAppInfoFromApp(app));
			}
			logger.info("Loaded [" + apps.size() + "] apps from memcache...");
		}
		return apps;
	}
	
	@RequestMapping("/have")
	public @ResponseBody String haveHandler(HttpServletRequest request, 
    												@RequestParam("os") String os,
    												@RequestParam("token") String token,
    												@RequestParam("rawId") String rawId,
    												@RequestParam("name") String name,
    												@RequestParam("icon") String icon,
    												@RequestParam("storeUrl") String storeUrl)
	{
		logger.info("have request: " + request.getRemoteAddr());
		Long userId = Long.valueOf(EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey()));
		Queue queue = QueueFactory.getQueue("have-app");
		queue.add(withUrl("/task/have-app").param("os", os).param("userId", userId.toString()).param("rawId", rawId).param("name", name).param("icon", icon).param("storeUrl", storeUrl));
		return "ok";
    }
	
	@RequestMapping("/recommend")
    public @ResponseBody String recommendHandler(HttpServletRequest request,
    												@RequestParam("os") String os,
    												@RequestParam("fromToken") String fromToken,
    												@RequestParam("toTokens") String[] toTokens,
    												@RequestParam("rawId") String rawId,
    												@RequestParam("name") String name,
    												@RequestParam("icon") String icon,
    												@RequestParam("storeUrl") String storeUrl)
	{
		logger.info("recommend request: " + request.getRemoteAddr());
		Long fromUserId = Long.valueOf(EncryptAES.decrypt(fromToken, RapplzConfig.getInstance().getSecurityKey()));
		Long[] toUserIds = new Long[toTokens.length];
		for(int i = 0; i < toTokens.length; i++)
		{
			toUserIds[i] = Long.valueOf(EncryptAES.decrypt(toTokens[i], RapplzConfig.getInstance().getSecurityKey()));
		}
		Queue queue = QueueFactory.getQueue("recommend-app");
		queue.add(withUrl("/task/recommend-app").param("os", os).param("fromUserId", fromUserId.toString()).param("toUserIds", toUserIds.toString()).param("rawId", rawId).param("name", name).param("icon", icon).param("storeUrl", storeUrl));
		return "ok";
    }
	
	@RequestMapping("/site-map.html")
    public String siteMap(HttpServletRequest request)
	{
		return "site-map";
    }
	
	@RequestMapping("/advertise.html")
    public String advertise(HttpServletRequest request)
	{
		return "advertise";
    }
	
	@RequestMapping("/terms-of-service.html")
    public String termsOfService(HttpServletRequest request)
	{
		return "terms-of-service";
    }
	
	@RequestMapping("/privacy-policy.html")
    public String privacyPolicy(HttpServletRequest request)
	{
		return "privacy-policy";
    }
	
	@RequestMapping("/about-us.html")
    public String aboutUs(HttpServletRequest request)
	{
		return "about-us";
    }
	
	@RequestMapping("/contact-us.html")
    public String contactUs(HttpServletRequest request)
	{
		return "contact-us";
    }
}