package com.retro.rapplz.db.dao;

import java.util.List;

public interface BaseDao
{
	@SuppressWarnings("rawtypes")
	public Object loadById(Class clazz, Long id);
	
	@SuppressWarnings("rawtypes")
	public Object loadByName(Class clazz, String name);
	
	@SuppressWarnings("rawtypes")
	public List list(Class clazz);
	
	public void save(Object entity);

	@SuppressWarnings("rawtypes")
	public void removeById(Class clazz, Long id);
	
	public void removeBy(Object entity);
}