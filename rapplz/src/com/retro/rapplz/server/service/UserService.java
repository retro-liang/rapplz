package com.retro.rapplz.server.service;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("userService")
public class UserService
{
	private static final Logger logger = Logger.getLogger(UserService.class.getName());	
	
	@GET
	@Path("signIn")
	public String signIn(@Context HttpServletRequest request)
	{
		
		
		User user = null;
        try {
            OAuthService oauth = OAuthServiceFactory.getOAuthService();
            user = oauth.getCurrentUser();
            return user.getEmail();
        } catch (OAuthRequestException e) {
            return e.toString();
        }
	}
}