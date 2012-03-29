package com.retro.rapplz.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;


public class ChannelServlet extends HttpServlet
{
	private static final long serialVersionUID = -3602285196779798709L;
	private static final Logger logger = Logger.getLogger(ChannelServlet.class.getName());
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		logger.info("Requesting Channel Token...");
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel("activity");
		logger.info("Channel Token Generated: " + token);
		resp.setContentType("text/html");
	    resp.getWriter().write(token);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		logger.info("Broadcasting activity...");
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage("activity", req.getParameter("uid") + "|" + req.getParameter("aid")));
		logger.info("Broadcasting activity done");
	}
}