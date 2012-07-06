package com.retro.rapplz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.AccountRoleDao;
import com.retro.rapplz.db.entity.AccountRole;

@Service("accountRoleService")
public class AccountRoleServiceImpl implements AccountRoleService
{
	@Autowired
	private AccountRoleDao accountRoleDao;

	@Override
	@Transactional
	public void addAccountRole(AccountRole accountRole)
	{
		accountRoleDao.addAccountRole(accountRole);
	}

	@Override
	@Transactional
	public List<AccountRole> listAccountRoles()
	{
		return accountRoleDao.listAccountRoles();
	}

	@Override
	@Transactional
	public void removeAccountRole(Long id)
	{
		accountRoleDao.removeAccountRole(id);
	}

	@Override
	@Transactional
	public AccountRole getAccountRoleById(Long id)
	{
		return accountRoleDao.getAccountRoleById(id);
	}
}