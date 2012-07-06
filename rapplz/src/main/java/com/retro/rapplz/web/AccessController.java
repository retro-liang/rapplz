package com.retro.rapplz.web;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AccessController
{
	private static final Logger logger = Logger.getLogger(AccessController.class.getName());
	
	@RequestMapping("sign_in.html")
    public String displaySignInPage(HttpServletRequest request)
	{
		logger.info("displaySignInPage: " + request.getRemoteAddr());
		return "sign_in";
    }
	
	@RequestMapping("sign_in_success")
    public String signInSuccessHandler(HttpServletRequest request)
	{
		logger.info("signInSuccessHandler: " + request.getRemoteAddr());
		return "welcome";
    }
}