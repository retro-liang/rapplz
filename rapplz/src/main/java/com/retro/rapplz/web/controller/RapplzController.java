package com.retro.rapplz.web.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.UserService;
import com.retro.rapplz.service.exception.ApplicationServiceException;

@Controller
public class RapplzController
{
	private static final Logger logger = Logger.getLogger(RapplzController.class.getName());
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
    public String homepage(HttpServletRequest request)
	{
		logger.info("homepage request: " + request.getRemoteAddr());
		return "homepage";
    }
	
	@RequestMapping("/index.html")
    public String index(HttpServletRequest request)
	{
		logger.info("index request: " + request.getRemoteAddr());
		return "homepage";
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
		try
		{
			userService.have(os, userId, rawId, name, icon, storeUrl);
			return "ok";
		}
		catch (ApplicationServiceException e)
		{
			return "error: " + e;
		}
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
		try
		{
			userService.recommend(os, fromUserId, toUserIds, rawId, name, icon, storeUrl);
			return "ok";
		}
		catch (ApplicationServiceException e)
		{
			return "error: " + e;
		}
    }
}