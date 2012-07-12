package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.AccountType;

public interface AccountTypeDao
{
	public void addAccountType(AccountType AccountType);
	public List<AccountType> listAccountTypes();
	public AccountType getAccountTypeById(Long id);
	public AccountType getAccountTypeByName(String name);
	public void removeAccountType(Long id);
}