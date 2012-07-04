package com.retro.rapplz.db.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.retro.rapplz.db.entity.OS;

public interface OSDao
{
	public OS loadOS(Long id) throws DataAccessException;

	public List<OS> getOSs() throws DataAccessException;
	
	public OS saveOS(OS os) throws DataAccessException;
	
	public void removeOS(Long id) throws DataAccessException;
}