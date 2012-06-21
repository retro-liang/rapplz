package com.retro.rapplz.web;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retro.rapplz.db.dao.AppDao;
import com.retro.rapplz.db.entity.App;

@Controller
@RequestMapping("/test")
public class RapplzController
{
	private static final Logger logger = Logger.getLogger(RapplzController.class.getName());
	
	@Autowired
    private AppDao appDao;
	
	@RequestMapping(method = RequestMethod.GET)
    public String welcomeHandler(HttpServletRequest request)
	{
		logger.info("request: " + request.getAttributeNames());
		List<App> apps = appDao.getApps();
		logger.info("apps: " + apps);
		return "welcome";
    }
}