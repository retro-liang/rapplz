package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.AccountStatus;

public interface AccountStatusDao
{
	public void addAccountStatus(AccountStatus AccountStatus);
	public List<AccountStatus> listAccountStatuss();
	public AccountStatus getAccountStatusById(Long id);
	public AccountStatus getAccountStatusByName(String name);
	public void removeAccountStatus(Long id);
}