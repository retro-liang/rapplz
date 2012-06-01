package com.retro.rapplz.server.datastore.service;

import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.AppTag;

public class AppTagDBService
{
	private static final Logger logger = Logger.getLogger(AppTagDBService.class.getName());
	
	private DAO dao;
	
	public AppTagDBService()
	{
		dao = new DAO();
	}
	
	public Key<AppTag> saveAppTag(String tagName)
	{
		AppTag appTag = new AppTag();
		appTag.setName(tagName);
		return dao.ofy().put(appTag);
	}
	
	public AppTag getAppTag(String tagName)
	{
		List<AppTag> appTagList = dao.ofy().query(AppTag.class).filter("name", tagName).list();
		if(appTagList != null && appTagList.size() > 0)
		{
			return appTagList.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public Key<AppTag> getAppTagKey(String tagName)
	{
		List<Key<AppTag>> appTagList = dao.ofy().query(AppTag.class).filter("name", tagName).listKeys();
		if(appTagList != null && appTagList.size() > 0)
		{
			return appTagList.get(0);
		}
		else
		{
			return null;
		}		
	}
}