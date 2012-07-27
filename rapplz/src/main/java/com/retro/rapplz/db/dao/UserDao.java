package com.retro.rapplz.db.dao;

import java.util.List;
import java.util.Set;

import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.User;

public interface UserDao
{
	public void addUser(User user);
	public User findByEmail(String email);
	public User getUserByID(Long id);
	public void resetPassword(String email, String password);
	public String activateUser(Long id);
	public String inactivateUser(Long id);
	public void updateUser(User user);
	public List<User> listUser();
	public void removeUser(Long id);
	public Set<AccountRole> getAccountRolesByEmail(String email);
	public int getUserAppCount(Long id);
	public int getUserRecommendationCount(Long id);
	public int getUserFollowerCount(Long id);
	public int getUserFollowingCount(Long id);
}