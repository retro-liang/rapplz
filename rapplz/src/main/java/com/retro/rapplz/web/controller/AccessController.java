package com.retro.rapplz.web.controller;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.service.UserService;
import com.retro.rapplz.service.exception.ApplicationServiceException;

@Controller
@RequestMapping("/access")
@SessionAttributes("user")
public class AccessController extends MultiActionController
{
	private static final Logger logger = Logger.getLogger(AccessController.class.getName());
	
	@Autowired
	private PlaintextPasswordEncoder encoder;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("sign_in.html")
    public String signInPage(HttpServletRequest request)
	{
		logger.info("displaySignInPage: " + request.getRemoteAddr());
		return "sign_in";
    }
	
	@RequestMapping("sign_in_success")
    public String signInSuccessHandler(HttpServletRequest request, ModelMap model, Principal principal)
	{
		logger.info("signInSuccessHandler: " + request.getRemoteAddr());
		String email = principal.getName();
		User user = new User();
		user.setEmail(email);
		//model.addAttribute("firstName", firstName);
		//model.addAttribute("lastName", lastName);
		model.addAttribute("user", user);
		return "redirect:/index.html";
    }
	
    @RequestMapping("sign_in_fail")
    public String signInFailedHandler(ModelMap model)
	{
		logger.info("Sign in fail.");
		model.addAttribute("signInFailMessage", "Sign in fail, your email or password are not correct.");
		return "redirect:sign_in.html";
    }
	
	@RequestMapping("sign_up.html")
    public String signUpPage(HttpServletRequest request, ModelMap modelMap)
	{
		logger.info("displaySignUpPage: " + request.getRemoteAddr());
		modelMap.addAttribute("user", new User());
		return "sign_up";
    }
	
	@RequestMapping(value="sign_up", method=RequestMethod.POST)
    public String signUpHandler(HttpServletRequest request, @Valid User user, BindingResult result)
	{
		logger.info("signUpHandler: " + request.getRemoteAddr());
		if(result.hasErrors())
		{
			return "sign_up";
		}
		else
		{
			Queue queue = QueueFactory.getQueue("create-user");
		    queue.add(withUrl("/task/create_user").param("accountType", "ROLE_USER").param("email", user.getEmail()).param("password", user.getPassword()).param("firstName", user.getFirstName()).param("lastName", user.getLastName()));
			return "redirect:sign_up_success.html";
		}
    }
	
	@RequestMapping("sign_up_success.html")
    public String signUpSuccessPage()
	{
		logger.info("Display sign up success page.");
		return "sign_up_success";
    }
	
	@RequestMapping("sign_out_success.html")
    public String signOutPage()
	{
		logger.info("Display sign out page.");
		return "sign_out_success";
    }
	
	@RequestMapping("forget_password.html")
    public String forgetPasswordPage()
	{
		logger.info("Display forget password page.");
		return "forget_password";
    }

	@RequestMapping(value="forget_password", method=RequestMethod.POST)
    public String forgetPasswordHandler(@RequestParam("email") String email) throws UnsupportedEncodingException
	{
		logger.info("forgetPasswordHandler");
		String token = encoder.encodePassword(email, new Date());
		Queue queue = QueueFactory.getQueue("send-email");
	    queue.add(withUrl("/task/send_email").param("fromEmail", RapplzConfig.getInstance().getSenderEmailAddress())
	    										.param("fromName", "Rapplz")
	    										.param("toEmail", email)
	    										.param("toName", "")
	    										.param("subject", "Rapplz - reset password")
	    										.param("content", "http://www.rapplz.com/access/reset_password.html?token=" + token));
		return "redirect:forget_password_email_sent.html";
    }
	
	@RequestMapping("forget_password_email_sent.html")
    public String forgetPasswordEmailSentPage()
	{
		logger.info("Display forget password email sent page.");
		return "forget_password_email_sent";
    }

	@RequestMapping("reset_password.html")
    public String resetPasswordPage(@RequestParam("token") String token, ModelMap model) throws UnsupportedEncodingException
	{
		logger.info("Display reset password page.");
		String[] result = encoder.obtainPasswordAndSalt(token);
		if(result != null && result.length == 2)
		{
			String email = result[0];
			String createdDate = result[1];
			logger.info("email: " + email);
			logger.info("createdDate: " + createdDate);
			if(email != null && !email.equals("") && createdDate != null && !createdDate.equals(""))
			{
				SimpleDateFormat dataFormat = new SimpleDateFormat();
				try
				{
					Date date = dataFormat.parse(createdDate);
					if(System.currentTimeMillis() - date.getTime() <= 1000 * 60 * 60 *24)
					{
						model.addAttribute("token", token);
						return "reset_password";
					}
				}
				catch (ParseException e)
				{
				}
			}
		}
		return "redirect:reset_password_fail.html";
    }
	
	@RequestMapping("reset_password")
    public String resetPasswordHandler(@RequestParam("token") String token, @RequestParam("password") String password, ModelMap model)
	{
		logger.info("Display sign out page.");
		String[] result = encoder.obtainPasswordAndSalt(token);
		String email = result[0];
		try
		{
			userService.resetPassword(email, password);
			return "redirect:reset_password_success.html";
		}
		catch (ApplicationServiceException e)
		{
			return "redirect:reset_password_fail.html";
		}
    }
	
	@RequestMapping("reset_password_success.html")
    public String resetPasswordSuccessPage()
	{
		logger.info("Display forget password success page.");
		return "reset_password_success";
    }
	
	@RequestMapping("reset_password_fail.html")
    public String resetPasswordFailPage()
	{
		logger.info("Display reset password fail page.");
		return "reset_password_fail";
    }
}