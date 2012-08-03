package com.retro.rapplz.service;

import java.util.List;

import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.service.exception.ApplicationServiceException;

public interface AppService
{
	public List<App> loadApps() throws ApplicationServiceException;
}