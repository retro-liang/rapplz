package com.retro.rapplz.server.datastore.service;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.Profile;

public class ProfileDBService
{
	private DAO dao;
	
	public ProfileDBService()
	{
		dao = new DAO();
	}
	
	public Profile getProfileByKey(Key<Profile> profileKey)
	{
		return dao.ofy().get(profileKey);
	}
	
	public Key<Profile> save(Profile profile)
	{
		return dao.ofy().put(profile);
	}
}