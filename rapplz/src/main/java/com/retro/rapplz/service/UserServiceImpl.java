package com.retro.rapplz.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.AccountRoleDao;
import com.retro.rapplz.db.dao.AccountStatusDao;
import com.retro.rapplz.db.dao.AccountTypeDao;
import com.retro.rapplz.db.dao.AppDao;
import com.retro.rapplz.db.dao.CategoryDao;
import com.retro.rapplz.db.dao.DeviceDao;
import com.retro.rapplz.db.dao.OSDao;
import com.retro.rapplz.db.dao.RecommendationDao;
import com.retro.rapplz.db.dao.UserDao;
import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;
import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.App;
import com.retro.rapplz.db.entity.Category;
import com.retro.rapplz.db.entity.Device;
import com.retro.rapplz.db.entity.OS;
import com.retro.rapplz.db.entity.Recommendation;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.UserInfo;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService
{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private AccountRoleDao accountRoleDao;
	
	@Autowired
	private AccountTypeDao accountTypeDao;
	
	@Autowired
	private AccountStatusDao accountStatusDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OSDao osDao;
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private RecommendationDao recommendationDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaltSource saltSource;
	
	@Override
	@Transactional(readOnly = true)
	public User loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException
	{
		logger.info("loadUserByUsername: " + email);
		User user = userDao.getRapplzUserByEmail(email);
		if (user == null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		return user;
	}
	
	@Override
	@Transactional(readOnly = true)
	public User getUser(Long id) throws ApplicationServiceException
	{
		return userDao.getUser(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public User getUserByFederalId(String id) throws ApplicationServiceException
	{
		return userDao.getUserByFederalId(id);
	}
	
	@Override
	@Transactional
	public User createUser(String accountRoleName, String accountTypeName, String accountStatusName, String email, String password, String firstName, String lastName, String federalId, String avatar) throws ApplicationServiceException
	{
		logger.info("createUser: " + email);
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setFederalId(federalId);
		user.setAvatar(avatar);
		if(federalId == null || federalId.trim().equals(""))	//rapplz user
		{
			user.setPassword(passwordEncoder.encodePassword(password, saltSource.getSalt(user)));
		}
		AccountRole accountRole = accountRoleDao.getAccountRoleByName(accountRoleName);
		AccountType accountType = accountTypeDao.getAccountTypeByName(accountTypeName);
		AccountStatus accountStatus = accountStatusDao.getAccountStatusByName(accountStatusName);
		user.getAccountRoles().add(accountRole);
		user.setAccountType(accountType);
		user.setAccountStatus(accountStatus);
		userDao.save(user);
		return user;
	}
	
	@Override
	@Transactional
	public void resetPassword(String email, String password) throws ApplicationServiceException
	{
		logger.info("reset password: " + email);
		User user = loadUserByUsername(email);
	    String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
		userDao.resetPassword(email, encodedPassword);
	}
	
	@Override
	@Transactional
	public void activateUser(Long id) throws ApplicationServiceException
	{
		logger.info("activate user: " + id);
		userDao.activateUser(id);
	}
	
	@Override
	@Transactional
	public void inactivateUser(Long id) throws ApplicationServiceException
	{
		logger.info("inactivate user: " + id);
		userDao.inactivateUser(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserAppCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserAppCount(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserRecommendationCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserRecommendationCount(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserFollowerCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserFollowerCount(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUserFollowingCount(Long id) throws ApplicationServiceException
	{
		return userDao.getUserFollowingCount(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserInfo loadUserInfo(UserInfo userInfo) throws ApplicationServiceException
	{
		Long id = Long.valueOf(userInfo.getId());
		int appCount = getUserAppCount(id);
		int recommendationCount = getUserRecommendationCount(id);
		int followerCount = getUserFollowerCount(id);
		int followingCount = getUserFollowingCount(id);
		userInfo.setAppCount(appCount);
		userInfo.setRecommendationCount(recommendationCount);
		userInfo.setFollowerCount(followerCount);
		userInfo.setFollowingCount(followingCount);
		return userInfo;
	}
	
	@Override
	@Transactional
	public void have(String osName, Long userId, String rawId, String appName, String icon, String[] deviceNames, String categoryName) throws ApplicationServiceException
	{
		OS os = osDao.getOSByName(osName);
		if(os != null)
		{
			User user = userDao.getUser(userId);
			if(user != null)
			{
				App app = appDao.getAppByRawId(rawId);
				if(app == null)
				{
					app = new App();
					app.setOs(os);
					app.setRawId(rawId);
					app.setName(appName);
					app.setIconUrl(icon);
				}
				
				if(deviceNames != null && deviceNames.length > 0)
				{
					for(String deviceName : deviceNames)
					{
						Device device = deviceDao.getDeviceByName(deviceName);
						if(device == null)
						{
							device = new Device();
							device.setName(deviceName);
							deviceDao.save(device);
						}
						app.getDevices().add(device);
					}
				}
				
				Category category = categoryDao.getCategoryByName(categoryName);
				if(category == null)
				{
					category = new Category();
					category.setName(categoryName);
					categoryDao.save(category);
				}
				app.getCategories().add(category);
				
				appDao.save(app);
				logger.info("app: " + app);
				user.getApps().add(app);
				userDao.save(user);
			}
		}
	}
	
	@Override
	@Transactional
	public void recommend(String osName, Long fromUserId, String[] toUserIds, String rawId, String appName, String icon, String[] deviceNames, String categoryName) throws ApplicationServiceException
	{
		OS os = osDao.getOSByName(osName);
		if(os != null)
		{
			User user = userDao.getUser(fromUserId);
			if(user != null)
			{
				App app = appDao.getAppByRawId(rawId);
				if(app == null)
				{
					app = new App();
					app.setOs(os);
					app.setRawId(rawId);
					app.setName(appName);
					app.setIconUrl(icon);
				}
				
				if(deviceNames != null && deviceNames.length > 0)
				{
					for(String deviceName : deviceNames)
					{
						Device device = deviceDao.getDeviceByName(deviceName);
						if(device == null)
						{
							device = new Device();
							device.setName(deviceName);
							deviceDao.save(device);
						}
						app.getDevices().add(device);
					}
				}
				
				Category category = categoryDao.getCategoryByName(categoryName);
				if(category == null)
				{
					category = new Category();
					category.setName(categoryName);
					categoryDao.save(category);
				}
				app.getCategories().add(category);
				
				appDao.save(app);
				
				if(toUserIds == null || toUserIds.length == 0)	//recommend to all
				{
					Recommendation recommendation = new Recommendation();
					recommendation.setApp(app);
					recommendation.setFromUser(user);
					recommendationDao.save(recommendation);
					user.getSentRecommendations().add(recommendation);
				}
				else	//recommend to a subset of friends
				{
					for(String toUserId : toUserIds)
					{
						User toUser = userDao.getUser(Long.valueOf(toUserId));
						if(toUser != null)
						{
							Recommendation recommendation = new Recommendation();
							recommendation.setApp(app);
							recommendation.setFromUser(user);
							recommendation.setToUser(toUser);
							recommendationDao.save(recommendation);
							user.getSentRecommendations().add(recommendation);
						}
					}
				}				
				userDao.save(user);
			}
		}
	}
}