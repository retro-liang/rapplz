package com.retro.rapplz.server.datastore.service;

import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.AppIndex;

public class AppIndexDBService
{
	private DAO dao;
	
	public AppIndexDBService()
	{
		dao = new DAO();
	}
	
	public void saveAppIndex(AppIndex appIndex)
	{
		dao.ofy().put(appIndex);
	}
}