package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.App;

public interface AppDao extends BaseDao
{
	public App loadAppByRawId(String rawId);
	
	public List<App> getAppsByCategory(Long categoryId);

	public int getAppHaveCount(Long id);
	
	public int getAppRecommendationCount(Long id);
}