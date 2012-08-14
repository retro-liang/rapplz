package com.retro.rapplz.db.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.retro.rapplz.db.entity.App;

//@ContextConfiguration
//@DirtiesContext
public class AppDaoTest //extends AbstractTransactionalJUnit4SpringContextTests
{
	/**
     * Logger for debugging purposes
     */
    private Logger logger = Logger.getLogger(AppDaoTest.class);
    
    //@Autowired
    protected AppDao dao;

    
    /**
     * A Spring application context that we'll create from a test application context and use to create
     * our DAO object (and data source, session factory, etc.)
     */
    private static ApplicationContext ctx = null;
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        // Load the applicationContext.xml file
        ctx = new ClassPathXmlApplicationContext("spring/applicationContext-hibernate.xml");
    }
    
    @Before
    public void setUp()
    {
        dao = (AppDao)ctx.getBean("appDao");
    }
    
    @After
    public void tearDown()
    {
        dao = null;
    }
    
    /**
     * Tests to make sure that we can add
     */
    @Test
    public void testSaveApp()
    {
    	System.out.println("start testing...");
    	List<App> apps = dao.getApps();
    	System.out.println("app list size: " + apps.size());
    	
    	dao.remove(1l);
    	apps = dao.getApps();
    	System.out.println("app list size: " + apps.size());
    	
        // Create an app
        App app = new App();
        app.setName( "new-app1" );
        
        // Add an app to the database
        dao.save(app);
        System.out.println(app.getId());
        // Load the app into another object
        Assert.assertNotNull("The app that was created was unable to be loaded from the database", app);
        
        // Assert that the app exists
        Assert.assertEquals( "Names do not match", "new-app1", app.getName() );
        
        // Remove the app from the database
        dao.remove(app.getId());
        
        apps = dao.getApps();
    	System.out.println("app list size: " + apps.size());
        
        // Assert that the app is no longer in the database
    	App nullApp = dao.getApp(app.getId());
        Assert.assertNull("The app should have been deleted but it was not", nullApp);
        System.out.println("App: " + app);
    }
}