package com.retro.rapplz.db.dao;

import java.util.List;
import java.util.Set;

import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.User;

public interface UserDao
{
	public User getUser(Long id);
	public User getUserByEmail(String email);
	public User getRapplzUserByEmail(String email);
	public User getGoogleUserByEmail(String email);
	public User getFacebookUserByEmail(String email);
	public User getTwitterUserByEmail(String email);
	public User getUserByEmailAccountType(String email, String accountType);
	public User getUserByFederalId(String id);
	public List<User> getUsers();
	public Set<AccountRole> getAccountRolesByEmail(String email);
	public int getUserAppCount(Long id);
	public int getUserRecommendationCount(Long id);
	public int getUserFollowerCount(Long id);
	public int getUserFollowingCount(Long id);
	public void save(User user);
	public void remove(Long id);
	public void resetPassword(String email, String password);
	public String activateUser(Long id);
	public String inactivateUser(Long id);
}