package com.retro.rapplz.server.datastore.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppTag;
import com.retro.rapplz.server.datastore.entity.AppTagIndex;

public class AppDBService
{
	private DAO dao;
	
	public AppDBService()
	{
		dao = new DAO();
	}
	
	public App searchAppById(Long id)
	{
		return dao.ofy().get(App.class, id);
	}
	
	public void saveApp(App app)
	{
		dao.ofy().put(app);
	}
	
	public void saveApps(List<App> apps)
	{
		dao.ofy().put(apps);
	}
	
	public int getAllAppsSize()
	{
		return dao.ofy().query(App.class).count();
	}
	
	public List<App> getAppsByTag(Key<AppTag> appTag)
	{
		AppTagIndex appTagIndex = dao.ofy().query(AppTagIndex.class).filter("appTag", appTag).get();
		if(appTagIndex != null && appTagIndex.getApps() != null && appTagIndex.getApps().size() > 0)
		{
			return (List<App>) dao.ofy().get(App.class, appTagIndex.getApps()).values();
		}
		else
		{
			return null;
		}
	}
	
	public List<App> getAllApps()
	{
		List<App> apps = new ArrayList<App>();
		Iterator<Key<App>> keyIterator = dao.ofy().query(App.class).fetchKeys().iterator();
		while(keyIterator.hasNext())
		{
			apps.add(dao.ofy().get((Key<App>)keyIterator.next()));
			
		}
		return apps;
	}
	
	public void deleteAllApps()
	{
		dao.ofy().delete(dao.ofy().query(App.class).fetchKeys());
	}
}
