package com.retro.rapplz.web.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

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
}