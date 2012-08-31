package com.retro.rapplz.db.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retro.rapplz.db.entity.Device;

@Repository
public class DeviceDaoImpl implements DeviceDao
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Device getDevice(Long id)
	{
		return (Device)sessionFactory.getCurrentSession().load(Device.class, id);
	}
	
	@Override
	public Device getDeviceByName(String name)
	{
		return (Device)sessionFactory.getCurrentSession().createQuery("select d from Device d where d.name like '" + name + "'").setCacheable(true).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Device> getDevices()
	{
		return sessionFactory.getCurrentSession().createQuery("from Device").list();		
	}

	@Override
	public void save(Device device)
	{
		sessionFactory.getCurrentSession().save(device);
	}

	@Override
	public void remove(Long id)
	{
		sessionFactory.getCurrentSession().delete(id);
	}
}