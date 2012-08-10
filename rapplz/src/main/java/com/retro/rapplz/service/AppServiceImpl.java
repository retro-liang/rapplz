package com.retro.rapplz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.AppDao;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.service.exception.ApplicationServiceException;

@Service("appService")
@Transactional
public class AppServiceImpl implements AppService
{
	@Autowired
	private AppDao appDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<App> loadApps() throws ApplicationServiceException
	{
		return appDao.getApps();
	}
}