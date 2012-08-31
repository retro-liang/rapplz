package com.retro.rapplz.service;

import java.util.List;
import java.util.Set;

import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.AppInfo;

public interface AppService
{
	public Set<App> loadApps() throws ApplicationServiceException;
	public Set<AppInfo> loadAppInfos() throws ApplicationServiceException;
}