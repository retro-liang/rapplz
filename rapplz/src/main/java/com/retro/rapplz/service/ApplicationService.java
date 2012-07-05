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

public interface ApplicationService
{
	public User signUp(AccountType accountType, String email, String password, String firstName, String lastName) throws ApplicationServiceException;
	public User confirmSignUp(String code) throws ApplicationServiceException;
	public User signIn(String email, String password) throws ApplicationServiceException;
	public User updateUser(Long userId, String firstName, String lastName) throws ApplicationServiceException;
	public void forgetPassword(String email) throws ApplicationServiceException;
	public void resetPassword(String code, String password) throws ApplicationServiceException;
	public int getUserAppsCount(Long userId) throws ApplicationServiceException;
	public int getUserRecommendationsCount(Long userId) throws ApplicationServiceException;
	public int getUserFollowersCount(Long userId) throws ApplicationServiceException;
	public int getUserFollowingsCount(Long userId) throws ApplicationServiceException;
	public List<User> getFollowers(Long userId) throws ApplicationServiceException;
	public List<User> getFollowings(Long userId) throws ApplicationServiceException;
	public User getUserDetail(Long userId) throws ApplicationServiceException;
	public int getAppRecommendationsCount(Long appId) throws ApplicationServiceException;
	public List<App> getApps(List<Long> appIds) throws ApplicationServiceException;
	public List<App> getAppsDetail(List<Long> appIds) throws ApplicationServiceException;
	public void haveApp(Long userId, Long appId) throws ApplicationServiceException;
	public void haveApps(Long userId, List<Long> appIds) throws ApplicationServiceException;
	public void recommendApp(Long fromUserId, Long appId, List<Long> toUserIds) throws ApplicationServiceException;
	public void recommendApps(Long userId, List<Long> appId) throws ApplicationServiceException;
	public void followUser(Long followerUserId, Long followingUserId) throws ApplicationServiceException;
	public void unfollowUser(Long followerUserId, Long followingUserId) throws ApplicationServiceException;
	public AppComment addAppComment(Long authorId, Long appId, AppComment appComment) throws ApplicationServiceException;
	public Review addReview(Long authorId, Long appId, Review review) throws ApplicationServiceException;
	public ReviewComment addReviewComment(Long authorId, Long reviewId, ReviewComment reviewComment) throws ApplicationServiceException;
	public Tag addAppTag(Long authorId, Long appId, Tag tag) throws ApplicationServiceException;
	public List<App> getAppRank(String criteria) throws ApplicationServiceException;
	public void sendFeedback(String name, String email, String feedback) throws ApplicationServiceException;
	public List<Activity> getLatestActivities() throws ApplicationServiceException;
}