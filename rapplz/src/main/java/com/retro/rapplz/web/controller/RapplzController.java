package com.retro.rapplz.web.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.retro.rapplz.service.UserService;

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