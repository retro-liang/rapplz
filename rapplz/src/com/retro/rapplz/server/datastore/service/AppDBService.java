package com.retro.rapplz.server.datastore.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.App;

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
	
	public List<App> getAllApps()
	{
		//return new ArrayList<App>(dao.ofy().get(dao.ofy().query(App.class).fetchKeys()).values());
		List<App> apps = new ArrayList<App>();
		Iterator keyIterator = dao.ofy().query(App.class).fetchKeys().iterator();
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
