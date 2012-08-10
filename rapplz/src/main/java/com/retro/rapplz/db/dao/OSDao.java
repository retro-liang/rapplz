package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.OS;

public interface OSDao
{
	public OS getOS(Long id);
	
	public OS getOSByName(String name);

	public List<OS> getOSs();
	
	public void save(OS os);
	
	public void remove(Long id);
}