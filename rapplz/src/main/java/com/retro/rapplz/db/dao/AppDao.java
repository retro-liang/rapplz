package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.App;

public interface AppDao
{
	public App getApp(Long id);
	
	public App getAppByName(String name);
	
	public App getAppByRawId(String rawId);

	public List<App> getApps();
	
	public void save(App app);
	
	public void remove(Long id);
	
	public int getAppHaveCount(Long id);
	
	public int getAppRecommendationCount(Long id);
}