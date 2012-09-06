package com.retro.rapplz.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.AppDao;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.AppInfo;
import com.retro.rapplz.web.util.AppInfoAssembler;

@Service("appService")
@Transactional
public class AppServiceImpl implements AppService
{
	private static final Logger logger = Logger.getLogger(AppServiceImpl.class.getName());
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private AppInfoAssembler appInfoAssembler;
	
	@Override
	@Transactional(readOnly = true)
	public Set<App> getApps() throws ApplicationServiceException
	{
		return new HashSet<App>(appDao.list(App.class));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<AppInfo> getAppInfos() throws ApplicationServiceException
	{
		Set<AppInfo> appInfos = new HashSet<AppInfo>();
		List<App> apps = appDao.list(App.class);
		for(App app : apps)
		{
			AppInfo appInfo = appInfoAssembler.buildAppInfoFromApp(app);
			appInfos.add(appInfo);
		}
		return appInfos;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<AppInfo> getAppInfosByCategory(Long categoryId) throws ApplicationServiceException
	{
		Set<AppInfo> appInfos = new HashSet<AppInfo>();
		logger.info("Loading apps by cateogry");
		List<App> apps = appDao.getAppsByCategory(categoryId);		
		for(App app : apps)
		{
			AppInfo appInfo = appInfoAssembler.buildAppInfoFromApp(app);
			appInfos.add(appInfo);
		}
		return appInfos;
	}
}