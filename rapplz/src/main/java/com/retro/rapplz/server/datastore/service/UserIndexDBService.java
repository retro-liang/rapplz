package com.retro.rapplz.server.datastore.service;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.User;
import com.retro.rapplz.server.datastore.entity.UserIndex;

public class UserIndexDBService
{
	private DAO dao;
	
	public UserIndexDBService()
	{
		dao = new DAO();
	}
	
	public Key<UserIndex> getUserIndexKeyByUseKey(Key<User> userKey)
	{
		return dao.ofy().query(UserIndex.class).ancestor(userKey).getKey();
	}
	
	public UserIndex getUserIndexByUseKey(Key<User> userKey)
	{
		return dao.ofy().query(UserIndex.class).ancestor(userKey).get();
	}
	
	public void saveUserIndex(UserIndex userIndex)
	{
		dao.ofy().put(userIndex);
	}
}