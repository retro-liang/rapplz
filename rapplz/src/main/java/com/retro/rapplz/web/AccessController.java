package com.retro.rapplz.web;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.retro.rapplz.db.entity.User;

@Controller
@RequestMapping("/")
public class AccessController
{
	private static final Logger logger = Logger.getLogger(AccessController.class.getName());
	
	@RequestMapping("sign_in.html")
    public String signInPage(HttpServletRequest request)
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
    public String signUpHandler(HttpServletRequest request, User user)
	{
		logger.info("signUpHandler: " + request.getRemoteAddr());
		Queue queue = QueueFactory.getQueue("create-user");
	    queue.add(withUrl("/task/create_user").param("email", user.getEmail()).param("password", user.getPassword()).param("firstName", user.getFirstName()));
		return "sign_up_success.html";
    }
	
	@RequestMapping("sign_up_success.html")
    public String signUpSuccessPage()
	{
		logger.info("Display sign up success page.");
		return "sign_up_success";
    }
}