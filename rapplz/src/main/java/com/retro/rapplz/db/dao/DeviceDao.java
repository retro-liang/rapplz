package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.Device;

public interface DeviceDao
{
	public Device getDevice(Long id);
	
	public Device getDeviceByName(String name);

	public List<Device> getDevices();
	
	public void save(Device device);
	
	public void remove(Long id);
}