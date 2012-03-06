package com.retro.rapplz.server.feed;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jdom.Element;

import com.retro.rapplz.server.config.RapplzConfig;
import com.retro.rapplz.server.datastore.entity.App;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@Path("/feedprocessor")
public class FeedProcessor
{
	private static final Logger logger = Logger.getLogger(FeedProcessor.class.getName());
	
	//private AppDBService appDBService = new AppDBService();;
	
	private URL feedSource;
	private List<App> appFeeds = new ArrayList<App>();
	private Date lastUpdateDate;
	private int newAppFeedCount;
	private int updateAppFeedCount;
	
	@GET
	@Path("{os}/{device}/{type}")
	public String parseIOSAppFeed(@PathParam("os") String os, @PathParam("device") String device, @PathParam("type") String type)
	{
		logger.info("parsing feed for [os=" + os + " device=" + device + " type=" + type + "]...");
		
		String feedURL = RapplzConfig.getInstance().getFeedURL(os, device, type);
		if(feedURL != null && !feedURL.trim().equals(""))
		{
			try
			{
				feedSource = new URL(feedURL);
				logger.info("Feed [os=" + os + " device=" + device + " type=" + type + "] Source URL: " + feedSource);
				newAppFeedCount = 0;
				updateAppFeedCount = 0;
				appFeeds.clear();
				loadFeed(feedSource);
				logger.info("Load total [" + appFeeds.size() + "] app feeds.");
				for(App app : appFeeds)
				{
					insertToDB(app);
				}
				logger.info("Insert [" + newAppFeedCount + "] new video news feed into database.");
				logger.info("Update [" + updateAppFeedCount + "] video news feed in database.");				
			}
			catch (MalformedURLException e)
			{
				logger.severe("Invalid feed url: " + feedURL + " - " + e);
			}
		}
		return "Insert [" + newAppFeedCount + "] new video news feed into database.\nUpdate [" + updateAppFeedCount + "] video news feed in database.";
	}
	
	@SuppressWarnings("unchecked")
	private void loadFeed(URL feedSource)
	{
		try
		{
			XmlReader reader = new XmlReader(feedSource);
		    SyndFeed feed = new SyndFeedInput().build(reader);
		    if(lastUpdateDate != null && lastUpdateDate.getTime() == feed.getPublishedDate().getTime())
		    {
		    	logger.info("Feed has no update since " + lastUpdateDate);
		    }
		    else
		    {
		    	logger.info("Feed has new update at " + feed.getPublishedDate());
		    	lastUpdateDate = feed.getPublishedDate();
				for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();)
				{
					try
					{
						SyndEntry entry = (SyndEntry) i.next();
						App app = new App();
						app.setLink(entry.getLink());
						if(entry.getCategories() != null && entry.getCategories().size() > 0)
						{
							app.setCategory(((SyndCategoryImpl)entry.getCategories().get(0)).getName());
						}						
						
						if(entry.getForeignMarkup() != null && entry.getForeignMarkup() instanceof List)
						{
							List<Element> elements = ((List<Element>)entry.getForeignMarkup());
							if(elements.size() > 0)
							{
								for(Element element : elements)
								{
									if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("name"))
									{
										app.setName(element.getValue());
										break;
									}
									if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("artist"))
									{
										app.setArtist(element.getValue());
										break;
									}
									if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("price"))
									{
										app.setPrice(element.getValue());
										break;
									}
									if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("image"))
									{
										if(app.getImage() == null || app.getImage().trim().equals(""))
										{
											app.setImage(element.getValue());
										}										
										break;
									}
									if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("releaseDate"))
									{
										app.setReleaseDate(element.getValue());
										break;
									}
								}
							}							
						}
						
						appFeeds.add(app);
					}
					catch(Exception e)
					{
						logger.severe("Parsing current video news feed failed: " + e);
					}
				}
		    }
		}
		catch(Exception e)
		{
			logger.severe("Loading video news feed failed: " + e);
		}
	}
	
	private void insertToDB(App app)
	{
		try
		{
			
			
			/*List<VideoNewsFeed> videoNewsFeeds = videoNewsFeedDBService.searchVideoNewsFeedByURL(videoNewsFeed.getUrl());
			if(videoNewsFeeds != null && videoNewsFeeds.size() > 0)
			{
				if(videoNewsFeeds.size() > 1)
				{
					logger.warn("There are [" + videoNewsFeeds.size() + "] video news feeds have the same URL [" + videoNewsFeed.getUrl() + "] in database, this shouldn't happen, please check.");					
				}
				
				for(VideoNewsFeed feed : videoNewsFeeds)
				{
					if(videoNewsFeed.equals(feed))
					{
						logger.debug("DB already has this record: " + videoNewsFeed);
					}
					else
					{
						feed.setTitle(videoNewsFeed.getTitle());
						feed.setNewsDate(videoNewsFeed.getNewsDate());
						logger.info("Updating video news feed in database: " + videoNewsFeed);
						videoNewsFeedDBService.saveVideoNewsFeed(videoNewsFeeds.get(0));
						updateAppFeedCount++;
					}
				}				
			}
			else
			{
				logger.info("Inserting new video news feed into database: " + videoNewsFeed);
				videoNewsFeedDBService.persistVideoNewsFeed(videoNewsFeed);
				newAppFeedCount++;
			}*/
		}
		catch(Exception e)
		{
			logger.severe("Inserting video news feed record into database failed.");
		}
	}
}
