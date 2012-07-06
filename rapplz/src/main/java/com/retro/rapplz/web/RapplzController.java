package com.retro.rapplz.web;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retro.rapplz.db.dao.AppDao;
import com.retro.rapplz.db.dao.OSDao;
import com.retro.rapplz.db.dao.UserDao;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.OS;
import com.retro.rapplz.db.entity.User;

@Controller
@RequestMapping("/test")
public class RapplzController
{
	private static final Logger logger = Logger.getLogger(RapplzController.class.getName());
	
	@Autowired
    private AppDao appDao;
	
	@Autowired
    private OSDao osDao;
	
	@Autowired
    private UserDao userDao;
	
	@RequestMapping(method = RequestMethod.GET)
    public String welcomeHandler(HttpServletRequest request)
	{
		logger.info("welcome request: " + request.getAttributeNames());
		return "welcome";
    }
	
	@RequestMapping("app")
	public String appHandler(HttpServletRequest request)
	{
		logger.info("app request: " + request.getAttributeNames());
		List<App> apps = appDao.getApps();
		logger.info("apps: " + apps);
		return "welcome";
    }
	
	@RequestMapping("os")
	public String osHandler(HttpServletRequest request)
	{
		logger.info("os request: " + request.getAttributeNames());
		List<OS> oss = osDao.getOSs();
		logger.info("oss: " + oss);
		return "welcome";
    }
	
	@RequestMapping("user")
	public String userHandler(HttpServletRequest request)
	{
		logger.info("user request: " + request.getAttributeNames());
		List<User> users = userDao.listUser();
		logger.info("user: " + users);
		return "welcome";
    }
}