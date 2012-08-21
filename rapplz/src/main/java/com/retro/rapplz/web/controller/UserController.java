package com.retro.rapplz.web.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

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
}