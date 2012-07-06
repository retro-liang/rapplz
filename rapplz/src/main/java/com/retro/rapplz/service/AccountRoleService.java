package com.retro.rapplz.service;

import java.util.List;

import com.retro.rapplz.db.entity.AccountRole;

public interface AccountRoleService
{
	public void addAccountRole(AccountRole accountRole);
	public List<AccountRole> listAccountRoles();
	public void removeAccountRole(Long id);
	public AccountRole getAccountRoleById(Long id);
}
