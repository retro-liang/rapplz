package com.retro.rapplz.server.datastore.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
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
	
	public App getAppById(Long id)
	{
		try
		{
			return dao.ofy().get(App.class, id);
		}
		catch(NotFoundException e)
		{
			return null;
		}
	}
	
	public Key<App> getAppKeyById(Long id)
	{
		try
		{
			return dao.ofy().query(App.class).filter("id", id).getKey();
		}
		catch(NotFoundException e)
		{
			return null;
		}
	}
	
	public Key<App> saveApp(App app)
	{
		return dao.ofy().put(app);
	}
	
	public void saveApps(List<App> apps)
	{
		dao.ofy().put(apps);
	}
	
	public int getAllAppsSize()
	{
		return dao.ofy().query(App.class).count();
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
