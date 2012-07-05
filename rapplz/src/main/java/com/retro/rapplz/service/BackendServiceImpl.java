package com.retro.rapplz.service;

import java.util.List;

import com.retro.rapplz.db.entity.Activity;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.db.entity.Device;
import com.retro.rapplz.db.entity.OS;
import com.retro.rapplz.service.exception.BackendServiceException;

public class BackendServiceImpl implements BackendService
{

	@Override
	public List<OS> getOSs() throws BackendServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Device> getDevices() throws BackendServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCategories() throws BackendServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<App> getAppsInfo() throws BackendServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void createActivity(Activity activity) throws BackendServiceException
	{
		
	}
	
}