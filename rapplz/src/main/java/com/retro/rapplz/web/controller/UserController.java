package com.retro.rapplz.web.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/user")
public class UserController extends MultiActionController
{
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	
	@RequestMapping("{userName}.html")
	public String userPage(@PathVariable String userName, ModelMap model)
	{
		logger.info("Display user [" + userName + "] page");
		return "user";
	}
}