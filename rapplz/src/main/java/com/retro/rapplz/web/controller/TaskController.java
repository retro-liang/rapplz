package com.retro.rapplz.web.controller;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.retro.rapplz.config.RapplzConfig;
import com.retro.rapplz.db.entity.AccountRole;
import com.retro.rapplz.db.entity.AccountStatus;
import com.retro.rapplz.db.entity.AccountType;
import com.retro.rapplz.db.entity.User;
import com.retro.rapplz.security.EncryptAES;
import com.retro.rapplz.service.EmailService;
import com.retro.rapplz.service.UserService;

@Controller
@RequestMapping("/task")
public class TaskController
{
	private static final Logger logger = Logger.getLogger(TaskController.class.getName());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("create-user")
    public void createUserTask(HttpServletRequest request, HttpServletResponse response,
    							@RequestParam("accountType") String accountType,
    							@RequestParam("email") String email,
    							@RequestParam("password") String password,
    							@RequestParam("firstName") String firstName,
    							@RequestParam("lastName") String lastName)
	{
		logger.info("createUserTask: " + email);
		try
		{
			User user = userService.createUser(AccountRole.DEFAULT, AccountType.DEFAULT, AccountStatus.DEFAULT, email, password, firstName, lastName);
			String token = EncryptAES.encrypt(user.getId().toString(), RapplzConfig.getInstance().getSecurityKey());
			Queue queue = QueueFactory.getQueue("send-email");
		    queue.add(withUrl("/task/send-email").param("fromEmail", RapplzConfig.getInstance().getSenderEmailAddress())
		    										.param("fromName", "Rapplz")
		    										.param("toEmail", email)
		    										.param("toName", firstName)
		    										.param("subject", "Welcome to Rapplz")
		    										.param("content", "Thank you for opening an account with us.\nPlease click the link below to activate your account:\n\nhttp://" + request.getLocalAddr() + ":" + request.getServerPort() + "/access/activate-account?token=" + token));
		    response.getWriter().println(user + " has been created!");
		}
		catch(Exception e)
		{
			logger.severe("Create user taks failed: " + e);
		}
    }
	
	@RequestMapping("send-email")
    public void sendEmailTask(HttpServletRequest request, HttpServletResponse response,
    							@RequestParam("fromEmail") String fromEmail,
    							@RequestParam("fromName") String fromName,
    							@RequestParam("toEmail") String toEmail,
    							@RequestParam("toName") String toName,
    							@RequestParam("subject") String subject,
    							@RequestParam("content") String content)
	{
		try
		{
			emailService.sendEmail(fromEmail, fromName, toEmail, toName, subject, content);
			response.getWriter().println("Account creation email sent to user: " + toEmail);
		}
		catch(Exception e)
		{
			logger.severe("Send email taks failed: " + e);
		}
	}
}