package com.retro.rapplz.util;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.web.dto.UserInfo;

@Service("userInfoAssembler")
public class UserInfoAssembler
{
	private static final Logger logger = Logger.getLogger(UserInfoAssembler.class.getName());
	
	@Transactional(readOnly = true)
	public UserInfo buildUserInfoFromUser(User user)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstName(user.getFirstName());
		userInfo.setLastName(user.getLastName());
		userInfo.setEmail(user.getEmail());
		userInfo.setAvatar(user.getAvatar());
		userInfo.setStatus(user.getAccountStatus().getName());
		userInfo.setAppCount(user.getApps().size());
		userInfo.setRecommendationCount(user.getSentRecommendation().size());
		userInfo.setFollowerCount(user.getFollowers().size());
		userInfo.setFollowingCount(user.getFollowings().size());
		return userInfo;
	}
}