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
import com.retro.rapplz.server.datastore.entity.User;
import com.retro.rapplz.server.datastore.service.UserDBService;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("userService")
public class UserService
{
	private static final Logger logger = Logger.getLogger(UserService.class.getName());	
	
	private UserDBService userDBService = new UserDBService();
	
	@GET
	@Path("search/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public User getUserById(@PathParam("id") String id)
	{
		logger.info("getUserById get invoked: " + id);
		return userDBService.getUser(id);
	}
	
	@POST
	@Path("save")
	@Consumes("application/x-www-form-urlencoded")
	public Long saveUser(@Context HttpServletRequest request, @FormParam("id") Long id, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("email") String email, @FormParam("avatar") String avatar)
	{
		logger.info("saveUser get invoked: " + id);
		if(id != null && id != 0)
		{
			User user = new User();
			user.setId(id);
			user.getProfile().setFirstName(firstName);
			user.getProfile().setLastName(lastName);
			user.getProfile().setEmail(email);
			user.getProfile().setAvatar(avatar);
			return userDBService.saveUser(user).getId();
		}		
		return null;
	}
}