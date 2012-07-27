package com.retro.rapplz.service;

import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;

public interface UserService
{
	public User createUser(String accountRoleName, String accountTypeName, String accountStatusName, String email, String password, String firstName, String lastName) throws ApplicationServiceException;
	public void resetPassword(String email, String password) throws ApplicationServiceException;
	public void activateUser(Long id) throws ApplicationServiceException;
	public void inactivateUser(Long id) throws ApplicationServiceException;
}