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
	
	public Key<Profile> save(Profile profile)
	{
		return dao.ofy().put(profile);
	}
}