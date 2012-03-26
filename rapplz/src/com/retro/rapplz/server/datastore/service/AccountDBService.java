package com.retro.rapplz.server.datastore.service;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.dao.DAO;
import com.retro.rapplz.server.datastore.entity.Account;

public class AccountDBService
{
	private DAO dao;
	
	public AccountDBService()
	{
		dao = new DAO();
	}
	
	public Key<Account> save(Account account)
	{
		return dao.ofy().put(account);
	}
}