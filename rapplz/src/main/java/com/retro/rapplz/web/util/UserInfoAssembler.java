package com.retro.rapplz.web.util;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.UserService;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.UserInfo;

@Service("userInfoAssembler")
public class UserInfoAssembler
{
	private static final Logger logger = Logger.getLogger(UserInfoAssembler.class.getName());
	
	@Autowired
	private UserService userService;
	
	public UserInfo buildUserInfoFromUser(User user)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user.getId().toString());
		userInfo.setToken(EncryptAES.encrypt(user.getId().toString(), RapplzConfig.getInstance().getSecurityKey()));
		userInfo.setFirstName(user.getFirstName());
		userInfo.setLastName(user.getLastName());
		userInfo.setEmail(user.getEmail());
		userInfo.setAvatar(user.getAvatar());
		userInfo.setStatus(user.getAccountStatus().getName());
		try
		{
			userInfo = userService.loadUserInfo(userInfo);
		}
		catch (ApplicationServiceException e)
		{
			logger.severe("Fetching user data error: " + e);
		}
		return userInfo;
	}
}