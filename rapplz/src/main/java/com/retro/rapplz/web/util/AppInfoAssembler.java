package com.retro.rapplz.web.util;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.service.AppService;
import com.retro.rapplz.web.dto.AppInfo;

@Service("appInfoAssembler")
public class AppInfoAssembler
{
private static final Logger logger = Logger.getLogger(UserInfoAssembler.class.getName());
	
	@Autowired
	private AppService appService;
	
	public AppInfo buildAppInfoFromApp(App app)
	{
		AppInfo appInfo = new AppInfo();
		appInfo.setId(app.getId().toString());
		appInfo.setRawId(app.getRawId());
		appInfo.setName(app.getName());
		//appInfo.setCategories(app.getCategories());
		appInfo.setIcon(app.getIconUrl());
		return appInfo;
	}
}