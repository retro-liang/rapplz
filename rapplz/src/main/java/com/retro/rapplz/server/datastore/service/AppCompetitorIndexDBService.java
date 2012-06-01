package com.retro.rapplz.server.datastore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppCompetitorIndex;

public class AppCompetitorIndexDBService
{
	private static final Logger logger = Logger.getLogger(AppTagIndexDBService.class.getName());
	
	private DAO dao;
	
	public AppCompetitorIndexDBService()
	{
		dao = new DAO();
	}
	
	public AppCompetitorIndex getAppCompetitorIndex(Key<AppCompetitorIndex> appCompetitorIndexKey)
	{
		try
		{
			return dao.ofy().get(appCompetitorIndexKey);
		}
		catch(NotFoundException e)
		{
			return null;
		}
	}
	
	public Key<AppCompetitorIndex> getAppCompetitorIndexKey(Key<App> appKey)
	{
		try
		{
			return dao.ofy().query(AppCompetitorIndex.class).ancestor(appKey).getKey();
		}
		catch(NotFoundException e)
		{
			return null;
		}
	}

	public AppCompetitorIndex getAppCompetitorIndexByAppKey(Key<App> appKey)
	{
		try
		{
			return dao.ofy().query(AppCompetitorIndex.class).ancestor(appKey).get();
		}
		catch(NotFoundException e)
		{
			return null;
		}
	}
	
	public Key<AppCompetitorIndex> saveAppCompetitorIndex(AppCompetitorIndex appCompetitorIndex)
	{
		return dao.ofy().put(appCompetitorIndex);
	}
	
	public List<App> getCompetitorApps(Key<App> appKey)
	{
		AppCompetitorIndex appCompetitorIndex = dao.ofy().query(AppCompetitorIndex.class).ancestor(appKey).get();
		if(appCompetitorIndex != null)
		{
			Set<Key<App>> competitorAppKeys = appCompetitorIndex.getAppCompetitorKeys();
			return new ArrayList<App>(dao.ofy().get(competitorAppKeys).values());
		}
		else
		{
			return null;
		}
	}
}