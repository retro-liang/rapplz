package com.retro.rapplz.service;

import java.util.Set;

import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.AppInfo;

public interface AppService
{
	public Set<App> getApps() throws ApplicationServiceException;
	public Set<AppInfo> getAppInfos() throws ApplicationServiceException;
	public Set<AppInfo> getAppInfosByCategory(Long categoryId) throws ApplicationServiceException;
}