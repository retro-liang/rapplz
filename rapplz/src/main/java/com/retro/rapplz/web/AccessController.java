package com.retro.rapplz.web;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.retro.rapplz.db.dao.UserDao;
import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;
import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.UserAssembler;

@Controller
@RequestMapping("/")
public class AccessController
{
	private static final Logger logger = Logger.getLogger(AccessController.class.getName());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaltSource saltSource;
	
	@Autowired
	private UserAssembler userAssembler;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("sign_in.html")
    public String displaySignInPage(HttpServletRequest request)
	{
		logger.info("displaySignInPage: " + request.getRemoteAddr());
		return "sign_in";
    }
	
	@RequestMapping("sign_in_success")
    public String signInSuccessHandler(HttpServletRequest request)
	{
		logger.info("signInSuccessHandler: " + request.getRemoteAddr());
		return "welcome";
    }
	
	@RequestMapping("sign_up")
    public String signUpHandler(HttpServletRequest request)
	{
		logger.info("signUpHandler: " + request.getRemoteAddr());
		User user = new User();
		user.setEmail(System.currentTimeMillis() +"@rapplz.com");
		user.setPassword(passwordEncoder.encodePassword("123456", saltSource.getSalt(userAssembler.buildUserFromUserEntity(user))));
		AccountType accountType = new AccountType();
		accountType.setId(1l);
		accountType.setName("RAPPLZ");
		user.setAccountType(accountType);
		AccountStatus accountStatus = new AccountStatus();
		accountStatus.setId(1l);
		accountStatus.setName("ACTIVE");
		user.setAccountStatus(accountStatus);
		AccountRole accountRole = new AccountRole();
		accountRole.setId(1l);
		accountRole.setName("ROLE_USER");
		user.getAccountRoles().add(accountRole);
		userDao.addUser(user);
		return "welcome";
    }
}