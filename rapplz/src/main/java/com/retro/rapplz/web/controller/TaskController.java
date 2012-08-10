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
			logger.severe("Create user task failed: " + e);
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
			logger.severe("Send email task failed: " + e);
		}
	}
	
	@RequestMapping("have-app")
    public void haveAppTask(HttpServletRequest request, HttpServletResponse response,
    							@RequestParam("os") String os,
    							@RequestParam("userId") Long userId,
    							@RequestParam("rawId") String rawId,
    							@RequestParam("name") String name,
    							@RequestParam("icon") String icon,
    							@RequestParam("device") String device,
								@RequestParam("category") String category)
	{
		try
		{
			userService.have(os, userId, rawId, name, icon, device.split(","), category);
			response.getWriter().println("Added app [" + name + "] to user [" + userId + "] app list successfully.");
		}
		catch(Exception e)
		{
			logger.severe("Have app task failed: " + e);
		}
	}
	
	@RequestMapping("recommend-app")
    public void recommendAppTask(HttpServletRequest request, HttpServletResponse response,
    							@RequestParam("os") String os,
    							@RequestParam("fromUserId") Long fromUserId,
    							@RequestParam("toUserIds") Long[] toUserIds,
    							@RequestParam("rawId") String rawId,
    							@RequestParam("name") String name,
    							@RequestParam("icon") String icon,
    							@RequestParam("device") String device,
								@RequestParam("category") String category)
	{
		try
		{
			userService.recommend(os, fromUserId, toUserIds, rawId, name, icon, device.split(","), category);
			if(toUserIds != null && toUserIds.length > 0)
			{
				User fromUser = userService.getUser(fromUserId);
				if(fromUser != null)
				{
					for(Long toUserId : toUserIds)
					{
						User toUser = userService.getUser(toUserId);
						Queue queue = QueueFactory.getQueue("send-email");
					    queue.add(withUrl("/task/send-email").param("fromEmail", RapplzConfig.getInstance().getSenderEmailAddress())
					    										.param("fromName", "Rapplz")
					    										.param("toEmail", toUser.getEmail())
					    										.param("toName", toUser.getFirstName())
					    										.param("subject", "Rapplz app recommendation from " + fromUser.getFirstName())
					    										.param("content", "Your friend " + fromUser.getFirstName() + " from Rapplz has just recommeded you an app: " + name + "\nClick the link below to check it out:\n\nhttp://" + request.getLocalAddr() + ":" + request.getServerPort() + "/app/" + name.replaceAll(" ", "-")));
					}
				}
			}
			response.getWriter().println("Added app [" + name + "] to user [" + fromUserId + "] recommendation list successfully.");
		}
		catch(Exception e)
		{
			logger.severe("Recommend app task failed: " + e);
		}
	}
}