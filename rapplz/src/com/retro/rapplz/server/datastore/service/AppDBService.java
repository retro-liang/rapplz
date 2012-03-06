package com.retro.rapplz.server.datastore.service;

import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.App;

public class AppDBService
{
	private DAO dao;
	
	public AppDBService()
	{
		dao = new DAO();
	}
	
	public void saveApp(App app)
	{
		dao.ofy().put(app);
	}
}
