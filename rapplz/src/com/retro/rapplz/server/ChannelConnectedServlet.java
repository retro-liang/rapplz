package com.retro.rapplz.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class ChannelConnectedServlet extends HttpServlet
{
	private static final long serialVersionUID = -7573821384664663995L;
	private static final Logger logger = Logger.getLogger(ChannelConnectedServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		ChannelPresence presence = channelService.parsePresence(req);
		logger.info("[User Connected] client id: " + presence.clientId());
	}
}