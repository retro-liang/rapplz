package com.retro.rapplz.service;

import java.util.List;

import com.retro.rapplz.db.entity.Activity;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.db.entity.Device;
import com.retro.rapplz.db.entity.OS;
import com.retro.rapplz.service.exception.BackendServiceException;

public interface BackendService
{
	public List<OS> getOSs() throws BackendServiceException;
	public List<Device> getDevices() throws BackendServiceException;
	public List<Category> getCategories() throws BackendServiceException;
	public List<App> getAppsInfo() throws BackendServiceException;
	public void createActivity(Activity activity) throws BackendServiceException;
}