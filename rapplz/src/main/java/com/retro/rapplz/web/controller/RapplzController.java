package com.retro.rapplz.web.controller;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.Set;
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
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.UserService;
import com.retro.rapplz.web.dto.AppInfo;

@Controller
public class RapplzController
{
	private static final Logger logger = Logger.getLogger(RapplzController.class.getName());
	
	@Autowired
	private UserService userService;
	
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
	
	@RequestMapping("/app-action/load-apps")
	@ResponseBody
	public Set<AppInfo> loadAppsHandler()
	{
		logger.info("Loading apps from memcache...");
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		return (Set<AppInfo>)syncCache.get("apps");
	}
	
	@RequestMapping("/app-action/have")
	public @ResponseBody String haveHandler(HttpServletRequest request, 
    												@RequestParam("os") String os,
    												@RequestParam("token") String token,
    												@RequestParam("rawId") String rawId,
    												@RequestParam("name") String name,
    												@RequestParam("icon") String icon,
    												@RequestParam("device") String device,
    												@RequestParam("category") String category)
	{
		logger.info("have request: " + request.getRemoteAddr());
		Long userId = Long.valueOf(EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey()));
		Queue queue = QueueFactory.getQueue("app-action");
		queue.add(withUrl("/task/have-app").param("os", os).param("userId", userId.toString()).param("rawId", rawId).param("name", name).param("icon", icon).param("device", device).param("category", category));
		return "ok";
    }
	
	@RequestMapping("/app-action/recommend")
    public @ResponseBody String recommendHandler(HttpServletRequest request,
    												@RequestParam("os") String os,
    												@RequestParam("fromToken") String fromToken,
    												@RequestParam("toTokens") String toTokens,
    												@RequestParam("rawId") String rawId,
    												@RequestParam("name") String name,
    												@RequestParam("icon") String icon,
    												@RequestParam("device") String device,
    												@RequestParam("category") String category)
	{
		logger.info("recommend request: " + request.getRemoteAddr());
		Long fromUserId = Long.valueOf(EncryptAES.decrypt(fromToken, RapplzConfig.getInstance().getSecurityKey()));
		logger.info("totokens: " + toTokens);
		String toUserIds = "";
		if(toTokens != null && !toTokens.trim().equals(""))
		{
			String[] ids = toTokens.split(",");
			for(int i = 0; i < ids.length; i++)
			{
				if(ids[i] != null && !ids[i].trim().equals(""))
				{
					ids[i] = EncryptAES.decrypt(ids[i], RapplzConfig.getInstance().getSecurityKey());
				}
			}
			toUserIds = ids.toString();
		}
		Queue queue = QueueFactory.getQueue("app-action");
		queue.add(withUrl("/task/recommend-app").param("os", os).param("fromUserId", fromUserId.toString()).param("toUserIds", toUserIds).param("rawId", rawId).param("name", name).param("icon", icon).param("device", device).param("category", category));
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