package com.retro.rapplz.service;

import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;

public interface UserService
{
	public User createUser(String accountType, String email, String password, String firstName, String lastName) throws ApplicationServiceException;
}