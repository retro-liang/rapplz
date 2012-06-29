package com.retro.rapplz.service;

import com.retro.rapplz.db.entity.User;

public interface RapplzService
{
	public User register();
	public User login(String username, String password);
}