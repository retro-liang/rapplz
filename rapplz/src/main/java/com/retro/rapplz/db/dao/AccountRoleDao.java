package com.retro.rapplz.db.dao;

import java.util.List;

import com.retro.rapplz.db.entity.AccountRole;

public interface AccountRoleDao
{
	public void addAccountRole(AccountRole accountRole);
	public List<AccountRole> listAccountRoles();
	public AccountRole getAccountRoleById(Long id);
	public AccountRole getAccountRoleByName(String name);
	public void removeAccountRole(Long id);
}