package com.retro.rapplz.server.datastore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppTag;
import com.retro.rapplz.server.datastore.entity.AppTagIndex;

public class AppTagIndexDBService
{
	private static final Logger logger = Logger.getLogger(AppTagIndexDBService.class.getName());
	
	private DAO dao;
	
	public AppTagIndexDBService()
	{
		dao = new DAO();
	}
	
	public AppTagIndex getAppTagIndex(Key<AppTagIndex> appTagIndexKey)
	{
		return dao.ofy().get(appTagIndexKey);
	}
	
	public Key<AppTagIndex> getAppTagIndexKey(Key<AppTag> appTagKey)
	{
		return dao.ofy().query(AppTagIndex.class).ancestor(appTagKey).getKey();
	}
	
	public List<App> getAppsByAppTag(AppTag appTag)
	{
		List<AppTagIndex> appTagIndexList = dao.ofy().query(AppTagIndex.class).ancestor(appTag).list();
		if(appTagIndexList != null && appTagIndexList.size() > 0)
		{
			AppTagIndex appTagIndex = appTagIndexList.get(0);
			if(appTagIndex != null && appTagIndex.getAppKeys() != null && appTagIndex.getAppKeys().size() > 0)
			{
				return new ArrayList<App>(dao.ofy().get(appTagIndex.getAppKeys()).values());
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public void saveAppTagIndex(AppTagIndex appTagIndex)
	{
		dao.ofy().put(appTagIndex);
	}
	
	public Key<AppTagIndex> saveAppTagIndex(Key<AppTag> appTagKey, Key<App> appKey)
	{
		AppTagIndex appTagIndex = new AppTagIndex();
		appTagIndex.setAppTagKey(appTagKey);
		appTagIndex.getAppKeys().add(appKey);
		return dao.ofy().put(appTagIndex);
	}
	
	public List<AppTagIndex> getAppTagIndexByApp(App app)
	{
		return dao.ofy().query(AppTagIndex.class).filter("apps", app).list();
	}
}