package com.retro.rapplz.web;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.retro.rapplz.db.entity.User;

@Controller
@RequestMapping("/access")
public class AccessController extends MultiActionController
{
	private static final Logger logger = Logger.getLogger(AccessController.class.getName());
	
	@RequestMapping("sign_in.html")
    public String signInPage(HttpServletRequest request)
	{
		logger.info("displaySignInPage: " + request.getRemoteAddr());
		return "sign_in";
    }
	
	@RequestMapping("sign_in_success")
    public String signInSuccessHandler(HttpServletRequest request, ModelMap model)
	{
		logger.info("signInSuccessHandler: " + request.getRemoteAddr());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		//model.addAttribute("firstName", firstName);
		//model.addAttribute("lastName", lastName);
		model.addAttribute("email", email);
		return "homepage";
    }
	
	@RequestMapping("sign_up.html")
    public String signUpPage(HttpServletRequest request, ModelMap modelMap)
	{
		logger.info("displaySignUpPage: " + request.getRemoteAddr());
		modelMap.addAttribute("user", new User());
		return "sign_up";
    }
	
	@RequestMapping(value="sign_up", method = RequestMethod.POST)
    public String signUpHandler(HttpServletRequest request, User user)
	{
		logger.info("signUpHandler: " + request.getRemoteAddr());
		Queue queue = QueueFactory.getQueue("create-user");
	    queue.add(withUrl("/task/create_user").param("accountType", "ROLE_USER").param("email", user.getEmail()).param("password", user.getPassword()).param("firstName", user.getFirstName()).param("lastName", user.getLastName()));
		return "redirect:sign_up_success.html";
    }
	
	@RequestMapping("sign_up_success.html")
    public String signUpSuccessPage()
	{
		logger.info("Display sign up success page.");
		return "sign_up_success";
    }
}