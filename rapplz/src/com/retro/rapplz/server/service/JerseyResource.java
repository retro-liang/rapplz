package com.retro.rapplz.server.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.retro.rapplz.server.cron.CronJobs;
import com.retro.rapplz.server.feed.FeedProcessor;

public class JerseyResource extends Application
{
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(FeedProcessor.class);
		s.add(AppService.class);
		s.add(UserService.class);
		s.add(CronJobs.class);
		return s;
	}
}