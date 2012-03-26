package com.retro.rapplz.server.datastore.service;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.Role;

public class RoleDBService
{
	private DAO dao;
	
	public RoleDBService()
	{
		dao = new DAO();
	}
	
	public Key<Role> save(Role role)
	{
		return dao.ofy().put(role);
	}
}