package com.retro.rapplz.db.dao;

import java.util.List;

public interface BaseDao
{
	public Object loadById(Class clazz, Long id);
	
	public Object loadByName(Class clazz, String name);
	
	public List list(Class clazz);
	
	public void save(Object entity);

	public void removeById(Class clazz, Long id);
	
	public void removeBy(Object entity);
}