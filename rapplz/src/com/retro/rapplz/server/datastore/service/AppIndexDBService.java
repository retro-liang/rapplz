package com.retro.rapplz.server.datastore.service;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.AppIndex;
import com.retro.rapplz.server.datastore.entity.AppTagIndex;
import com.retro.rapplz.server.datastore.entity.User;

public class AppIndexDBService
{
	private DAO dao;
	
	public AppIndexDBService()
	{
		dao = new DAO();
	}
	
	public Key<AppIndex> getAppIndexKeyByUseKey(Key<User> userKey)
	{
		return dao.ofy().query(AppIndex.class).ancestor(userKey).getKey();
	}
	
	public AppIndex getAppIndexByUseKey(Key<User> userKey)
	{
		return dao.ofy().query(AppIndex.class).ancestor(userKey).get();
	}
	
	public void saveAppIndex(AppIndex appIndex)
	{
		dao.ofy().put(appIndex);
	}
}