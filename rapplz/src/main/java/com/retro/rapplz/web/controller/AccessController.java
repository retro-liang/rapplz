package com.retro.rapplz.web.controller;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.UserService;
import com.retro.rapplz.service.exception.ApplicationServiceException;
import com.retro.rapplz.web.dto.UserInfo;
import com.retro.rapplz.web.util.UserInfoAssembler;

@Controller
@RequestMapping("/access")
@SessionAttributes("userInfo")
public class AccessController extends MultiActionController
{
	private static final Logger logger = Logger.getLogger(AccessController.class.getName());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserInfoAssembler userInfoAssembler;
	
	@RequestMapping("sign-in.html")
    public String signInPage(HttpServletRequest request)
	{
		logger.info("displaySignInPage: " + request.getRemoteAddr());
		return "sign-in";
    }
	
	@RequestMapping("sign-in-success")
    public @ResponseBody UserInfo signInSuccessHandler(HttpServletRequest request, ModelMap modelMap, Principal principal)
	{
		logger.info("signInSuccessHandler: " + request.getRemoteAddr());
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserInfo userInfo = userInfoAssembler.buildUserInfoFromUser(user);
		modelMap.addAttribute(userInfo);
		return userInfo;
    }
	
    @RequestMapping("sign-in-fail")
    public String signInFailedHandler(ModelMap model)
	{
		logger.info("Sign in fail.");
		model.addAttribute("signInFailMessage", "Sign in fail, your email or password are not correct.");
		return "redirect:sign-in.html";
    }
    
    @RequestMapping("federal-sign-in")
    public @ResponseBody UserInfo federalSignInHandler(HttpServletRequest request,
    													ModelMap modelMap,
    													@RequestParam("type") String type,
    													@RequestParam("id") String id,
    													@RequestParam("email") String email,
    													@RequestParam("firstName") String firstName,
    													@RequestParam("lastName") String lastName,
    													@RequestParam("avatar") String avatar)
	{
		logger.info("federalSignInHandler: " + request.getRemoteAddr());
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
		try
		{
			User user = userService.getUserByFederalId(id);
			if(user == null)
			{
				logger.info("Creating new user...");
				user = userService.createUser(AccountRole.DEFAULT, type, AccountStatus.ACTIVE, email, "", firstName, lastName, id, avatar);
			}
			UserInfo userInfo = userInfoAssembler.buildUserInfoFromUser(user);
			modelMap.addAttribute(userInfo);
			return userInfo;
		}
		catch (ApplicationServiceException e)
		{
			logger.severe("Federal sign in failed: " + e);
			return null;
		}
    }
	
	@RequestMapping("sign-up.html")
    public String signUpPage(HttpServletRequest request, ModelMap modelMap)
	{
		logger.info("displaySignUpPage: " + request.getRemoteAddr());
		modelMap.addAttribute("user", new User());
		return "sign-up";
    }
	
	@RequestMapping(value="sign-up", method=RequestMethod.POST)
    public @ResponseBody String signUpHandler(HttpServletRequest request, @Valid User user, BindingResult result)
	{
		logger.info("signUpHandler: " + request.getRemoteAddr());
		if(result.hasErrors())
		{
			return result.getAllErrors().toString();
		}
		else
		{
			Queue queue = QueueFactory.getQueue("create-user");
		    queue.add(withUrl("/task/create-user").param("accountType", "ROLE_USER").param("email", user.getEmail()).param("password", user.getPassword()).param("firstName", user.getFirstName()).param("lastName", user.getLastName()));
			return "ok";
		}
    }
	
	@RequestMapping("sign-up-success.html")
    public String signUpSuccessPage()
	{
		logger.info("Display sign up success page.");
		return "sign-up-success";
    }
	
	@RequestMapping("sign-out-success.html")
    public String signOutPage()
	{
		logger.info("Display sign out page.");
		return "sign-out-success";
    }
	
	@RequestMapping("forget-password.html")
    public String forgetPasswordPage()
	{
		logger.info("Display forget password page.");
		return "forget-password";
    }

	@RequestMapping(value="forget-password", method=RequestMethod.POST)
    public @ResponseBody String forgetPasswordHandler(HttpServletRequest request, @RequestParam("email") String email) throws UnsupportedEncodingException
	{
		logger.info("forgetPasswordHandler");
		String token = EncryptAES.encrypt((email + "~~" + System.currentTimeMillis()), RapplzConfig.getInstance().getSecurityKey());
		Queue queue = QueueFactory.getQueue("send-email");
	    queue.add(withUrl("/task/send-email").param("fromEmail", RapplzConfig.getInstance().getSenderEmailAddress())
	    										.param("fromName", "Rapplz")
	    										.param("toEmail", email)
	    										.param("toName", "")
	    										.param("subject", "Rapplz - reset password")
	    										.param("content", "http://" + request.getLocalAddr() + ":" + request.getServerPort() + "/access/reset-password.html?token=" + token));
		return "ok";
    }
	
	@RequestMapping("forget-password-email-sent.html")
    public String forgetPasswordEmailSentPage()
	{
		logger.info("Display forget password email sent page.");
		return "forget-password-email-sent";
    }

	@RequestMapping("reset-password.html")
    public String resetPasswordPage(@RequestParam("token") String token, ModelMap model) throws UnsupportedEncodingException
	{
		logger.info("Display reset password page.");
		if(token != null && !token.trim().equals(""))
		{
			String decryptedToken = EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey());
			if(decryptedToken != null && !decryptedToken.trim().equals(""))
			{
				String[] result = decryptedToken.split("~~");
				if(result != null && result.length == 2)
				{
					String email = result[0];
					String createdDate = result[1];
					logger.info("email: " + email);
					logger.info("createdDate: " + createdDate);
					if(email != null && !email.equals("") && createdDate != null && !createdDate.equals(""))
					{
						if(System.currentTimeMillis() - Long.valueOf(createdDate) <= 1000 * 60 * 60 *24)
						{
							model.addAttribute("token", token);
							return "reset-password";
						}						
					}
				}
			}
		}
		return "redirect:reset-password-fail.html";
    }
	
	@RequestMapping("reset-password")
    public String resetPasswordHandler(@RequestParam("token") String token, @RequestParam("password") String password, ModelMap model)
	{
		logger.info("Display sign out page.");
		if(token != null && !token.trim().equals(""))
		{
			String decryptedToken = EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey());
			if(decryptedToken != null && !decryptedToken.trim().equals(""))
			{
				String[] result = decryptedToken.split("~~");
				if(result != null && result.length == 2)
				{
					String email = result[0];
					try
					{
						userService.resetPassword(email, password);
						return "redirect:reset-password-success.html";
					}
					catch (ApplicationServiceException e)
					{
						return "redirect:reset-password-fail.html";
					}
				}
			}
		}
		return "redirect:reset-password-fail.html";
    }
	
	@RequestMapping("reset-password-success.html")
    public String resetPasswordSuccessPage()
	{
		logger.info("Display forget password success page.");
		return "reset-password-success";
    }
	
	@RequestMapping("reset-password-fail.html")
    public String resetPasswordFailPage()
	{
		logger.info("Display reset password fail page.");
		return "reset-password-fail";
    }
	
	@RequestMapping(value="activate-account")
    public @ResponseBody String activateAccountHandler(HttpServletRequest request, @RequestParam("token") String token) throws UnsupportedEncodingException
	{
		String userId = EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey());
		try
		{
			userService.activateUser(Long.valueOf(userId));
			return "ok";
		}
		catch(Exception e)
		{
			logger.severe("Activate user accound failed: " + e);
		}
		return "error";
    }
	
	@RequestMapping(value="inactivate-account")
    public @ResponseBody String inactivateAccountHandler(HttpServletRequest request, @RequestParam("token") String token) throws UnsupportedEncodingException
	{
		String userId = EncryptAES.decrypt(token, RapplzConfig.getInstance().getSecurityKey());
		try
		{
			userService.inactivateUser(Long.valueOf(userId));
			return "ok";
		}
		catch(Exception e)
		{
			logger.severe("Inactivate user accound failed: " + e);
		}
		return "error";
    }
}