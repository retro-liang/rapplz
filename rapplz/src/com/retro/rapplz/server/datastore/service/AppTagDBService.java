package com.retro.rapplz.server.datastore.service;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.AppTag;

public class AppTagDBService
{
	private DAO dao;
	
	public AppTagDBService()
	{
		dao = new DAO();
	}
	
	public Key<AppTag> getAppTagKey(String tagName)
	{
		return dao.ofy().query(AppTag.class).filter("name", tagName).getKey();
	}
}