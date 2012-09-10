package com.retro.rapplz.web.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.NewsletterService;

@Controller
@RequestMapping("/newsletter")
public class NewsletterController
{
	private static final Logger logger = Logger.getLogger(RapplzController.class.getName());
	
	@Autowired
	private NewsletterService newsletterService;
	
	@RequestMapping("subscribe")
    public void subscribe(HttpServletRequest request, @RequestParam("email") String email)
	{
		newsletterService.subscribe(email);
    }
	
	@RequestMapping("unsubscribe")
    public void unsubscribe(HttpServletRequest request, @RequestParam("token") String token)
	{
		newsletterService.subscribe(EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey()));
    }
}