package com.retro.rapplz.web.controller;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.UserService;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.UserDetail;

@Controller
@RequestMapping("/user")
public class UserController extends MultiActionController
{
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("{userName}.html")
	public String userPage(@PathVariable String userName, @RequestParam("token") String token, ModelMap model)
	{
		logger.info("Display user [" + userName + "] page");
		Long userId = Long.valueOf(EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey()));
		UserDetail userDetail;
		try
		{
			userDetail = userService.getUserDetail(userId);
			model.addAttribute("userDetail", userDetail);
			return "user";
		}
		catch (ApplicationServiceException e)
		{
			return "error";
		}
	}
	
	@RequestMapping("have")
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
	
	@RequestMapping("recommend")
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
}