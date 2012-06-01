package com.retro.rapplz.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.memcache.MemcacheService.IdentifiableValue;

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
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		IdentifiableValue oldChannels = syncCache.getIdentifiable("channels");
		if(oldChannels == null)
		{
			Set<String> channels = new HashSet<String>();
			channels.add(presence.clientId());
			syncCache.put("channels", channels);
		}
		else
		{
			Set<String> newChannels = new HashSet<String>((Set<String>)oldChannels.getValue());
			((Set<String>)newChannels).add(presence.clientId());
			syncCache.putIfUntouched("channels", oldChannels, newChannels);
		}
	}
}