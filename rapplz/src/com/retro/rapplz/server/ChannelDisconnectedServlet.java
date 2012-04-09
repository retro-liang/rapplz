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
import com.google.appengine.api.memcache.MemcacheService.IdentifiableValue;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ChannelDisconnectedServlet extends HttpServlet
{
	private static final long serialVersionUID = 5485566597005177894L;
	private static final Logger logger = Logger.getLogger(ChannelDisconnectedServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		ChannelPresence presence = channelService.parsePresence(req);
		logger.info("[User Disconnected] client id: " + presence);
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		IdentifiableValue oldChannels = syncCache.getIdentifiable("channels");
		if(oldChannels != null)
		{
			Set<String> newChannels = new HashSet<String>((Set<String>)oldChannels.getValue());
			newChannels.remove(presence.clientId());
			syncCache.putIfUntouched("channels", oldChannels, newChannels);
		}
	}
}