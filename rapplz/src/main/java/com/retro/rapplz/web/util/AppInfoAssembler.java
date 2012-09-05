package com.retro.rapplz.web.util;

import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.AppDao;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.web.dto.AppInfo;

@Service("appInfoAssembler")
public class AppInfoAssembler
{
	private static final Logger logger = Logger.getLogger(AppInfoAssembler.class.getName());
	
	@Autowired
	private AppDao appDao;
	
	public AppInfo buildAppInfoFromApp(App app)
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
		return appInfo;
	}
}