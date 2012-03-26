package com.retro.rapplz.server.datastore.service;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.Status;

public class StatusDBService
{
	private DAO dao;
	
	public StatusDBService()
	{
		dao = new DAO();
	}
	
	public Key<Status> save(Status status)
	{
		return dao.ofy().put(status);
	}
}