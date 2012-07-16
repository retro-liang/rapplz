package com.retro.rapplz.config;

import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class RapplzConfig extends XMLConfiguration
{
	private static final long serialVersionUID = -5339207330248040895L;

	private static final Logger logger = Logger.getLogger(RapplzConfig.class.getName());
 	
	private static RapplzConfig instance;
    private static String configFile = "config.xml";
    
    private ServletContext servletContext;

    static
    {
    	instance = new RapplzConfig();
    }

    private RapplzConfig()
    {
    	try
        {
        	load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile));
        }
        catch (ConfigurationException configEx)
        {
            logger.warning(configEx.getMessage());            
        }
        FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
        strategy.setRefreshDelay(60 * 1000);
        setReloadingStrategy(strategy);
    }

    public static RapplzConfig getInstance()
    {
        return instance;
    }
    
    public String getSenderEmailAddress()
    {
    	return instance.getString("sender-email-address");
    }
    
    public String getSecurityKey()
    {
    	return instance.getString("security-key");
    }
    
    public String getFeedURL(String os, String device, String type)
	{
		return instance.getString("app-feed." + os + "." + device + "." + type);
	}
    
    public String getAppSearchAPI(String os)
	{
		return instance.getString("app-search." + os + ".api");
	}
	
	public String getAppSearchAPIParameter(String os, String parameterName)
	{
		return instance.getString("app-search." + os + ".parameters." + parameterName);
	}
	
	public String getDefaultAppSearchAPI(String os)
	{
    	String api = getAppSearchAPI(os);
    	String country = getAppSearchAPIParameter(os, "country");
    	String entity = getAppSearchAPIParameter(os, "entity");
    	String attribute = getAppSearchAPIParameter(os, "attribute");
    	String limit = getAppSearchAPIParameter(os, "limit");
    	return api + "country=" + country + "&entity=" + entity + "&attribute=" + attribute + "&limit=" + limit + "&term=";
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public static void main(String args[])
	{
		System.out.println(RapplzConfig.getInstance().getFeedURL("ios", "all", "top25-paid"));
		System.out.println(RapplzConfig.getInstance().getAppSearchAPI("ios"));
		System.out.println(RapplzConfig.getInstance().getAppSearchAPIParameter("ios", "limit"));
	}
}
