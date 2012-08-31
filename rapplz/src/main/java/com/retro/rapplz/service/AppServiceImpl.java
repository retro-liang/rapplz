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

@Service("appService")
@Transactional
public class AppServiceImpl implements AppService
{
	private static final Logger logger = Logger.getLogger(AppServiceImpl.class.getName());
	
	@Autowired
	private AppDao appDao;
	
	@Override
	@Transactional(readOnly = true)
	public Set<App> loadApps() throws ApplicationServiceException
	{
		return new HashSet<App>(appDao.getApps());
	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<AppInfo> loadAppInfos() throws ApplicationServiceException
	{
		Set<AppInfo> appInfos = new HashSet<AppInfo>();
		List<App> apps = appDao.getApps();
		for(App app : apps)
		{
			AppInfo appInfo = new AppInfo();
			appInfo.setId(app.getId().toString());
			appInfo.setRawId(app.getRawId());
			appInfo.setName(app.getName());
			appInfo.setIcon(app.getIconUrl());
			Set<Category> categories = app.getCategories();
			String[] categoryNames = new String[categories.size()];
			int i = 0;
			for(Category category : categories)
			{
				categoryNames[i] = category.getName();
				i++;
			}
			appInfo.setCategoryNames(categoryNames);
			appInfo.setHaveCount(appDao.getAppHaveCount(app.getId()));
			appInfo.setRecommendationCount(appDao.getAppRecommendationCount(app.getId()));
			appInfos.add(appInfo);
		}
		return appInfos;
	}
}