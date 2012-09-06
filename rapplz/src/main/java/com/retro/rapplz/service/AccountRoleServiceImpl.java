package com.retro.rapplz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.AccountRoleDao;
import com.retro.rapplz.db.entity.AccountRole;

@Service("accountRoleService")
@Transactional
public class AccountRoleServiceImpl implements AccountRoleService
{
	@Autowired
	private AccountRoleDao accountRoleDao;
	
	@Override
	@Transactional(readOnly = true)
	public AccountRole getAccountRoleById(Long id)
	{
		return (AccountRole)accountRoleDao.loadById(AccountRole.class, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AccountRole> listAccountRoles()
	{
		return accountRoleDao.list(AccountRole.class);
	}

	@Override
	@Transactional
	public void addAccountRole(AccountRole accountRole)
	{
		accountRoleDao.save(accountRole);
	}

	@Override
	@Transactional
	public void removeAccountRole(Long id)
	{
		accountRoleDao.removeById(AccountRole.class, id);
	}
}