package com.retro.rapplz.service;

import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.UserInfo;

public interface UserService
{
	public User getUser(Long id) throws ApplicationServiceException;
	public User createUser(String accountRoleName, String accountTypeName, String accountStatusName, String email, String password, String firstName, String lastName) throws ApplicationServiceException;
	public void resetPassword(String email, String password) throws ApplicationServiceException;
	public void activateUser(Long id) throws ApplicationServiceException;
	public void inactivateUser(Long id) throws ApplicationServiceException;
	public int getUserAppCount(Long id) throws ApplicationServiceException;
	public int getUserRecommendationCount(Long id) throws ApplicationServiceException;
	public int getUserFollowerCount(Long id) throws ApplicationServiceException;
	public int getUserFollowingCount(Long id) throws ApplicationServiceException;
	public UserInfo loadUserInfo(UserInfo userInfo) throws ApplicationServiceException;
	public void have(String osName, Long userId, String rawId, String appName, String icon, String[] deviceNames, String categoryName) throws ApplicationServiceException;
	public void recommend(String osName, Long fromUserId, Long[] toUserIds, String rawId, String appName, String icon, String[] deviceNames, String categoryName) throws ApplicationServiceException;
}