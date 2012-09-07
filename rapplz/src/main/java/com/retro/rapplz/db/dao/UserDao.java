package com.retro.rapplz.db.dao;

import java.util.Set;

import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.User;

public interface UserDao extends BaseDao
{
	public User getUserByEmail(String email);
	public User getRapplzUserByEmail(String email);
	public User getGoogleUserByEmail(String email);
	public User getFacebookUserByEmail(String email);
	public User getTwitterUserByEmail(String email);
	public User getUserByEmailAccountType(String email, String accountType);
	public User getUserByFederalId(String id);
	public Set<AccountRole> getAccountRolesByEmail(String email);
	public int getUserAppCount(Long id);
	public int getUserRecommendationCount(Long id);
	public int getUserFollowerCount(Long id);
	public int getUserFollowingCount(Long id);
	public void resetPassword(String email, String password);
	public String activateUser(Long id);
	public String inactivateUser(Long id);
	public boolean alreadyHave(Long userId, Long appId);
	public boolean alreadyRecommend(Long userId, Long appId);
}