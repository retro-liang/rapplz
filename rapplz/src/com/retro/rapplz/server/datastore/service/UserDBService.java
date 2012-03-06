package com.retro.rapplz.server.datastore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppIndex;
import com.retro.rapplz.server.datastore.entity.User;

public class UserDBService
{
	private DAO dao;
	
	public UserDBService()
	{
		dao = new DAO();
	}
	
	public void saveUser(User user)
	{
		dao.ofy().put(user);
	}
	
	public List<App> findAppsByUserKey(Key<User> userKey)
	{
		AppIndex appIndex = dao.ofy().query(AppIndex.class).filter("user", userKey).get();
		List<Key<App>> list = new ArrayList<Key<App>>(appIndex.getApps());
		return (List<App>)dao.ofy().get(App.class, list).values();
	}
	
	public List<User> findUsersByAppKey(Key<App> appKey)
	{
		Set<Key<User>> userKeysSet = dao.ofy().query(AppIndex.class).filter("apps", appKey).fetchParentKeys();
		List<Key<User>> userKeys = new ArrayList<Key<User>>(userKeysSet);
		return (List<User>)dao.ofy().get(User.class, userKeys).values();
	}
}
