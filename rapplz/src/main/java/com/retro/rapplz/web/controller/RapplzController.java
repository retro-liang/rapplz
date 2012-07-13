package com.retro.rapplz.web.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RapplzController
{
	private static final Logger logger = Logger.getLogger(RapplzController.class.getName());
	
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
}