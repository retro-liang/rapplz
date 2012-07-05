package com.retro.rapplz.service;

import java.util.List;

import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.Activity;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.AppComment;
import com.retro.rapplz.db.entity.Review;
import com.retro.rapplz.db.entity.ReviewComment;
import com.retro.rapplz.db.entity.Tag;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;

public class ApplicationServiceImpl implements ApplicationService
{

	@Override
	public User signUp(AccountType accountType, String email, String password,
			String firstName, String lastName)
			throws ApplicationServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User confirmSignUp(String code) throws ApplicationServiceException
	{
		return null;
	}

	@Override
	public User signIn(String email, String password)
			throws ApplicationServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User updateUser(Long userId, String firstName, String lastName) throws ApplicationServiceException
	{
		return null;
	}

	@Override
	public void forgetPassword(String email) throws ApplicationServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(String code, String password)
			throws ApplicationServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getUserAppsCount(Long userId) throws ApplicationServiceException
	{
		return 0;
	}
	
	@Override
	public int getUserRecommendationsCount(Long userId) throws ApplicationServiceException
	{
		return 0;
	}
	
	@Override
	public int getUserFollowersCount(Long userId) throws ApplicationServiceException
	{
		return 0;
	}
	
	@Override
	public int getUserFollowingsCount(Long userId) throws ApplicationServiceException
	{
		return 0;
	}
	
	@Override
	public List<User> getFollowers(Long userId) throws ApplicationServiceException
	{
		return null;
	}
	
	@Override
	public List<User> getFollowings(Long userId) throws ApplicationServiceException
	{
		return null;
	}
	
	@Override
	public User getUserDetail(Long userId) throws ApplicationServiceException
	{
		return null;
	}
	
	@Override
	public int getAppRecommendationsCount(Long appId) throws ApplicationServiceException
	{
		return 0;
	}
	
	@Override
	public List<App> getApps(List<Long> appIds) throws ApplicationServiceException
	{
		return null;
	}
	
	@Override
	public List<App> getAppsDetail(List<Long> appIds) throws ApplicationServiceException
	{
		return null;
	}

	@Override
	public void haveApp(Long userId, Long appId) throws ApplicationServiceException
	{
		
	}
	
	@Override
	public void haveApps(Long userId, List<Long> appIds) throws ApplicationServiceException
	{
		
	}

	@Override
	public void recommendApp(Long fromUserId, Long appId, List<Long> toUserIds)	throws ApplicationServiceException
	{
		
	}
	
	@Override
	public void recommendApps(Long userId, List<Long> appId) throws ApplicationServiceException
	{
		
	}
	
	@Override
	public void followUser(Long followerUserId, Long followingUserId) throws ApplicationServiceException
	{
		
	}
	
	@Override
	public void unfollowUser(Long followerUserId, Long followingUserId) throws ApplicationServiceException
	{
		
	}

	@Override
	public AppComment addAppComment(Long authorId, Long appId,
			AppComment appComment) throws ApplicationServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Review addReview(Long authorId, Long appId, Review review)
			throws ApplicationServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewComment addReviewComment(Long authorId, Long reviewId,
			ReviewComment reviewComment) throws ApplicationServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Tag addAppTag(Long authorId, Long appId, Tag tag) throws ApplicationServiceException
	{
		return null;
	}
	
	@Override
	public List<App> getAppRank(String criteria) throws ApplicationServiceException
	{
		return null;
	}

	@Override
	public void sendFeedback(String name, String email, String feedback)
			throws ApplicationServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Activity> getLatestActivities() throws ApplicationServiceException
	{
		return null;
	}

}