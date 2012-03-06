package com.retro.rapplz.server.datastore.dao;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase
{
	static
	{
		ObjectifyService.register(User.class);
	}
}
