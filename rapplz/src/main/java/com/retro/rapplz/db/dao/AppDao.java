package com.retro.rapplz.db.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.retro.rapplz.db.entity.App;

public interface AppDao
{
	public App loadApp(Long id) throws DataAccessException;

	public List<App> getApps() throws DataAccessException;
	
	public App saveApp(App app) throws DataAccessException;
	
	public void removeApp(Long id) throws DataAccessException;
}