package com.retro.rapplz.server.feed;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jdom.Element;

import com.retro.rapplz.server.config.RapplzConfig;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.service.AppDBService;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@Path("/feedprocessor")
public class FeedProcessor
{
	private static final Logger logger = Logger.getLogger(FeedProcessor.class.getName());
	
	private AppDBService appDBService = new AppDBService();
	private Pattern idPattern = Pattern.compile("/id[\\d]+\\?");
	
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
				URL feedSource = new URL(feedURL);
				logger.info("Feed [os=" + os + " device=" + device + " type=" + type + "] Source URL: " + feedSource);
				List<App> appFeeds = loadFeed(feedSource);
				if(appFeeds != null && appFeeds.size() > 0)
				{
					insertToDB(appFeeds);
					logger.info("Parse total [" + appFeeds.size() + "] app feeds.");
					return "Parse total [" + appFeeds.size() + "] app feeds.";
				}
				else
				{
					logger.info("No app feed parsed");
					return "No app feed parsed";
				}
			}
			catch (MalformedURLException e)
			{
				logger.severe("Invalid feed url: " + feedURL + " - " + e);
				return "Invalid feed url: " + feedURL + " - " + e;
			}
		}
		else
		{
			logger.severe("Parse failed: feed url is null or empty.");
			return "Parse failed: feed url is null or empty.";
		}		
	}
	
	@SuppressWarnings("unchecked")
	private List<App> loadFeed(URL feedSource)
	{
		try
		{
			XmlReader reader = new XmlReader(feedSource);
		    SyndFeed feed = new SyndFeedInput().build(reader);
		    Date lastUpdateDate = (Date)RapplzConfig.getInstance().getServletContext().getAttribute("feedLastUpdateDate");
		    if(lastUpdateDate != null && lastUpdateDate.getTime() == feed.getPublishedDate().getTime())
		    {
		    	logger.info("Feed has no update since " + lastUpdateDate);
		    }
		    else
		    {
		    	logger.info("Feed has new update at " + feed.getPublishedDate());
		    	RapplzConfig.getInstance().getServletContext().setAttribute("feedLastUpdateDate", feed.getPublishedDate());
		    	List<App> appFeeds = new ArrayList<App>();
				for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();)
				{
					try
					{
						SyndEntry entry = (SyndEntry) i.next();
						App app = new App();
						String link = entry.getLink();
						if(link != null && !link.trim().equals(""))
						{
							Matcher matcher = idPattern.matcher(link);
							if(matcher.find())
							{
								app.setId(Long.valueOf(matcher.group().replace("/id", "").replace("?", "")));
							}							
						}
						app.setUpdateDate(entry.getUpdatedDate().toString());
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
									}
									else if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("contentType"))
									{
										app.setContentType(element.getAttributeValue("label"));
									}
									else if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("artist"))
									{
										app.setArtist(element.getValue());
									}
									else if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("price"))
									{
										app.setPrice(element.getValue());
									}
									else if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("image"))
									{
										if(app.getImage() == null || app.getImage().trim().equals(""))
										{
											app.setImage(element.getValue());
										}
									}
									else if(element.getName()!= null && element.getName().trim().equalsIgnoreCase("releaseDate"))
									{
										app.setReleaseDate(element.getValue());
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
				return appFeeds;
		    }
		}
		catch(Exception e)
		{
			logger.severe("Loading video news feed failed: " + e);
		}
		return null;
	}
	
	private void insertToDB(List<App> apps)
	{
		try
		{
			appDBService.saveApps(apps);			
		}
		catch(Exception e)
		{
			logger.severe("Exception happened during saving apps into database: " + e);
		}
	}
	
	public static void main(String args[])
	{
		
		String link = "http://itunes.apple.com/us/app/accelerated-learning-flashing/id495314163?mt=8&amp;uo=2";
		Pattern p = Pattern.compile("/id[\\d]+\\?");
		Matcher m = p.matcher(link);
		if(m.find())
		{
			System.out.println("found: " + m.group());
		}		
	}
}
