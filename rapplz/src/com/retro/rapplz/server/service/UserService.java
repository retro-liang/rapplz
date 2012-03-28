package com.retro.rapplz.server.service;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.googlecode.objectify.Key;
import com.retro.rapplz.server.datastore.entity.Account;
import com.retro.rapplz.server.datastore.entity.Profile;
import com.retro.rapplz.server.datastore.entity.User;
import com.retro.rapplz.server.datastore.service.AccountDBService;
import com.retro.rapplz.server.datastore.service.ProfileDBService;
import com.retro.rapplz.server.datastore.service.RoleDBService;
import com.retro.rapplz.server.datastore.service.StatusDBService;
import com.retro.rapplz.server.datastore.service.UserDBService;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("userService")
public class UserService
{
	private static final Logger logger = Logger.getLogger(UserService.class.getName());	
	
	private UserDBService userDBService = new UserDBService();
	private AccountDBService accountDBService = new AccountDBService();
	private ProfileDBService profileDBService = new ProfileDBService();
	private RoleDBService roleDBService = new RoleDBService();
	private StatusDBService statusDBService = new StatusDBService();
	
	@GET
	@Path("search/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public String getUserById(@PathParam("id") String id)
	{
		logger.info("getUserById get invoked: " + id);
		if(userDBService.getUser(id) != null)
		{
			return userDBService.getUser(id).getId();
		}
		else
		{
			return null;
		}
	}
	
	@POST
	@Path("save")
	@Consumes("application/x-www-form-urlencoded")
	public String saveUser(@Context HttpServletRequest request, @FormParam("id") String id, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("email") String email, @FormParam("avatar") String avatar, @FormParam("federalType") String federalType)
	{
		logger.info("saveUser get invoked: " + id);
		if(id != null && !id.trim().equals(""))
		{
			Account account = new Account();
			account.setFederalType(federalType);
			Key<Account> accountKey = accountDBService.save(account);
			
			Profile profile = new Profile();
			profile.setFirstName(firstName);
			profile.setLastName(lastName);
			profile.setEmail(email);
			profile.setAvatar(avatar);
			Key<Profile> profileKey = profileDBService.save(profile);
			
			User user = new User();
			user.setId(id);
			user.setAccount(accountKey);
			user.setProfile(profileKey);
			
			logger.info("save user successfully: " + id);
			return String.valueOf(userDBService.saveUser(user).getId());
		}		
		return null;
	}
}