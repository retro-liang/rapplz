package com.retro.rapplz.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.ChannelFactory.ChannelCreatedCallback;
import com.google.gwt.appengine.channel.client.SocketError;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings("deprecation")
public class Rapplz implements EntryPoint
{
	private static final Logger logger = Logger.getLogger(Rapplz.class.getName());
	
	private final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks
    private Date expires = new Date(System.currentTimeMillis() + DURATION);    
	
	private static final int REFRESH_DELAY = 500;
	private static final int REFRESH_INTERVAL = 1000 * 60 * 60 *24; // ms
	private static final int ACTIVITY_CLOSE_DELAY = 5 * 1000;
	
	private static final String SEARCH_USER_URL = "/rest/userService/search";
	private static final String SAVE_USER_URL = "/rest/userService/save";
	private static final String ALL_APPS_JSON_URL = "/rest/appService/all";
	private static final String ALL_FEATURED_APPS_JSON_URL = "/rest/appService/tag/featured";
	private static final String ALL_RECOMMENDED_APPS = "/rest/appService/tag/recommended";
	private static final String ALL_APP_COMPETITORS = "/rest/appService/competitors";
	private static final String ALL_APP_LIKES = "/rest/appService/likes";
	private static final String ALL_APPS_SIZE_URL = "/rest/appService/allAppsSize";
	private static final String SEARCH_APP_URL = "http://itunes.apple.com/search?country=US&entity=software&limit=5&term=";
	private static final String RECOMMEND_APP_URL = "/rest/appService/recommend";
	private static final String PREFER_APP_URL = "/rest/appService/prefer";
	
	private static final String REQUEST_CHANNEL_TOKEN_URL = "/channel/";
	
	private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
	private static final String CLIENT_ID = "929855298687.apps.googleusercontent.com";
	private static final String USER_PROFILE_EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	private static final String USER_PROFILE_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";	
	
	private VerticalPanel mainPanel = new VerticalPanel();	
	private FlexTable mainAppFlexTable = new FlexTable();
	private Label lastUpdatedLabel = new Label();
	private ArrayList<String> apps = new ArrayList<String>();
	private Label errorMsgLabel = new Label();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		
		
	    // Add styles to elements in the stock list table.
		mainAppFlexTable.setCellPadding(3);
	    
	    // Assemble Main panel.
	    errorMsgLabel.setStyleName("errorMessage");
	    errorMsgLabel.setVisible(false);

	    //mainPanel.add(addPanel);
	    mainPanel.add(errorMsgLabel);
	    mainPanel.add(mainAppFlexTable);	    
	    mainPanel.add(lastUpdatedLabel);
	    
	    RootPanel.get("appList").add(mainPanel);
	    
	    //Setup timer to refresh list automatically.
	    Timer refreshTimer = new Timer()
	    {
	      @Override
	      public void run()
	      {
	    	  //retrieveAppsInfo();
	    	  retrieveApps(ALL_RECOMMENDED_APPS);
	      }
	    };
	    
	    refreshTimer.schedule(REFRESH_DELAY);
	    //refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	    
	    ClickHandler googleSignInClickHandler = new ClickHandler()
	    {
	    	@Override
			public void onClick(ClickEvent event)
			{
	    		googleSignIn();
			}
	    };	    
	    RootPanel.get("google-sign-in-button").addDomHandler(googleSignInClickHandler, ClickEvent.getType());
	    
	    ClickHandler facebookSignInClickHandler = new ClickHandler()
	    {
	    	@Override
			public void onClick(ClickEvent event)
			{
	    		facebookSignIn();
			}
	    };	    
	    //RootPanel.get("facebook-sign-in-button").addDomHandler(facebookSignInClickHandler, ClickEvent.getType());
	    
	    ClickHandler searchAppClickHandler = new ClickHandler()
	    {
	    	@Override
			public void onClick(ClickEvent event)
			{
	    		String userId = Cookies.getCookie("sid");
	    		String searchText = RootPanel.get("search-app-text").getElement().getPropertyString("value");
	    		searchApp(userId, searchText, null);
			}
	    };
	    RootPanel.get("search-app-button").addDomHandler(searchAppClickHandler, ClickEvent.getType());	
	    
	    KeyPressHandler searchAppTextKeyPressHandler = new KeyPressHandler()
	    {
	    	public void onKeyPress(KeyPressEvent event)
	    	{
	    		if(event.getCharCode() == KeyCodes.KEY_ENTER)
	    		{
	    			String userId = Cookies.getCookie("sid");
		    		String searchText = RootPanel.get("search-app-text").getElement().getPropertyString("value");
		    		searchApp(userId, searchText, null);
	    		}
	    	}
	    };
	    RootPanel.get("search-app-text").addDomHandler(searchAppTextKeyPressHandler, KeyPressEvent.getType());
	    
	    refreshStatus();
	    
	    //setupChannel();
	}
	
	private void setupChannel()
	{
		try
		{
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(REQUEST_CHANNEL_TOKEN_URL));
			builder.sendRequest(null, new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request,	Response response)
				{
					String token = response.getText();
					createChannel(token);
				}

				@Override
				public void onError(Request request, Throwable exception)
				{
					displayError("Retrieve token failed: " + exception);
				}				
			});			
		}
		catch(Exception e)
		{
			displayError("Couldn't retrieve token: " + e);			
		}		
	}
	
	private void createChannel(String token)
	{
		ChannelFactory.createChannel(token, new ChannelCreatedCallback()
	    {
	    	@Override
	    	public void onChannelCreated(Channel channel)
	    	{
	    		channel.open(new SocketListener()
	    		{
	    			@Override
	    			public void onOpen()
	    			{
	    				//Window.alert("Channel opened!");
	    			}
	    			@Override
	    			public void onMessage(String message)
	    			{
	    				retrieveAppsInfo();
	    				retrieveApps(ALL_RECOMMENDED_APPS);
	    				if(message != null && !message.trim().equals(""))
	    				{
	    					createActivityPopup(asArrayOfActivity(message));
	    				}
	    			}
	    			@Override
	    			public void onError(SocketError error)
	    			{
	    				Window.alert("Error: " + error.getDescription());
	    			}
	    			@Override
	    			public void onClose()
	    			{
	    				Window.alert("Channel closed!");	    				
	    			}
		    	});
		    }
		});
	}
	
	private void createActivityPopup(JsArray<Activity> activities)
	{
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("New Activity");
		dialogBox.setAnimationEnabled(true);
		dialogBox.addStyleName("concerned");
		dialogBox.addStyleName("notice");
		HorizontalPanel activityHorizontalPanel = new HorizontalPanel();
		activityHorizontalPanel.addStyleName("concerned");
		activityHorizontalPanel.addStyleName("notice");
		activityHorizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		for(int i = 0; i < activities.length(); i++)
		{
			//Image userAvarta = new Image(activity.getUserAvatar());
			
			Label userName = new Label(activities.get(i).getUserName());
			Label staticLabel = new Label(activities.get(i).getUserName() + " has just recommended an app: " + activities.get(i).getAppName());
			//Image appIcon = new Image(activity.getAppIcon());
			Label appName = new Label(activities.get(i).getAppName());
			//activityHorizontalPanel.add(userAvarta);
			//activityHorizontalPanel.add(userName);
			activityHorizontalPanel.add(staticLabel);
			//activityHorizontalPanel.add(appIcon);
			//activityHorizontalPanel.add(appName);
		}		
		
		dialogBox.setWidget(activityHorizontalPanel);		
		dialogBox.setPopupPosition(1100, 400);
		dialogBox.show();
		
		Timer closeTimer = new Timer()
	    {
			@Override
			public void run()
			{
				dialogBox.removeFromParent();
			}
	    };
	    
	    closeTimer.schedule(ACTIVITY_CLOSE_DELAY);
	}
	
	private void refreshStatus()
	{
		if(Cookies.getCookie("sid") != null)
		{
			signIn();
			
			ClickHandler userClickHandler = new ClickHandler()
		    {
		    	@Override
				public void onClick(ClickEvent event)
				{
		    		openUserAccount();
				}
		    };
		    RootPanel.get("user-link").addDomHandler(userClickHandler, ClickEvent.getType());
		    RootPanel.get("user-text").addDomHandler(userClickHandler, ClickEvent.getType());
			
			ClickHandler signOutClickHandler = new ClickHandler()
		    {
		    	@Override
				public void onClick(ClickEvent event)
				{
		    		signOut();
		    		
				}
		    };
		    RootPanel.get("sign-link").addDomHandler(signOutClickHandler, ClickEvent.getType());
		    RootPanel.get("sign-text").addDomHandler(signOutClickHandler, ClickEvent.getType());
		}
		else
		{
			RootPanel.get("topnav").setVisible(true);
			RootPanel.get("signin_menu").setVisible(true);
		}
	}
	
	private void signIn()
	{
		RootPanel.get("topnav").setVisible(false);
		RootPanel.get("signin_menu").setVisible(false);
		
		RootPanel.get("user-text").getElement().setInnerText(Cookies.getCookie("fn") + " " + Cookies.getCookie("ln"));
		RootPanel.get("sign-text").getElement().setInnerText("Sign out");
	}
	
	private void signOut()
	{
		Cookies.removeCookie("sid");
		Cookies.removeCookie("fn");
		Cookies.removeCookie("ln");
		RootPanel.get("user-text").getElement().setInnerText("");
		RootPanel.get("sign-text").getElement().setInnerText("");
		RootPanel.get("topnav").setVisible(true);
		RootPanel.get("signin_menu").setVisible(true);
	}
	
	private void openUserAccount()
	{
		Window.alert("open user account popup");
	}
	
	private void googleSignIn()
	{
		try
		{
			AuthRequest req = new AuthRequest(AUTH_URL, CLIENT_ID).withScopes(USER_PROFILE_SCOPE, USER_PROFILE_EMAIL_SCOPE);
    		Auth.get().login(req, new Callback<String, Throwable>()
    		{
    			@Override
    			public void onSuccess(String token)
    			{
    				JsonpRequestBuilder jsonpRequestbuilder = new JsonpRequestBuilder();
    				jsonpRequestbuilder.requestObject("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token, new AsyncCallback<GoogleUser>()
					{
    					public void onFailure(Throwable throwable)
						{
							displayError("Couldn't retrieve JSON: " + throwable);
						}

    					public void onSuccess(final GoogleUser googleUser)
						{
    						Cookies.setCookie("sid", googleUser.getId(), expires, null, "/", false);
    						Cookies.setCookie("fn", googleUser.getFirstName(), expires, null, "/", false);
    						Cookies.setCookie("ln", googleUser.getLastName(), expires, null, "/", false);
    						refreshStatus();
    						
    						RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(SEARCH_USER_URL + "/" + googleUser.getId()));
							try
							{
								builder.sendRequest(null, new RequestCallback()
								{
									public void onError(Request request, Throwable exception)
									{
										displayError("Can't search user: " + exception);
									}

									public void onResponseReceived(Request request, Response response)
									{
										if(200 != response.getStatusCode())
										{
											RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(SAVE_USER_URL));
			    							try
			    							{
			    								StringBuffer postData = new StringBuffer();
			    								postData.append(URL.encode("federalType")).append("=").append(URL.encode("google"));
												postData.append("&");
			    								postData.append(URL.encode("id")).append("=").append(URL.encode(googleUser.getId()));
												postData.append("&");
												postData.append(URL.encode("firstName")).append("=").append(URL.encode(googleUser.getFirstName()));
												postData.append("&");
												postData.append(URL.encode("lastName")).append("=").append(URL.encode(googleUser.getLastName()));
												postData.append("&");
												postData.append(URL.encode("email")).append("=").append(URL.encode(googleUser.getEmail()));
												if(googleUser.getPicture() != null)
												{
													postData.append("&");
													postData.append(URL.encode("avatar")).append("=").append(URL.encode(googleUser.getPicture()));
												}
												//Window.alert(postData.toString());
												builder.setHeader("Content-type", "application/x-www-form-urlencoded");
												builder.sendRequest(postData.toString(), new RequestCallback()
			    								{
			    									public void onError(Request request, Throwable exception)
			    									{
			    										displayError("Can't save user: " + exception);
			    									}

			    									public void onResponseReceived(Request request, Response response)
			    									{
			    										if(200 == response.getStatusCode())
			    										{
			    											Cookies.setCookie("sid", response.getText(), expires, null, "/", false);
			    										}
			    									}
			    								});
			    							}
			    							catch(RequestException e)
			    							{
			    								displayError("Save user exception: " + e);
			    							}
										}
										else
										{
											Cookies.setCookie("sid", googleUser.getId(), expires, null, "/", false);
										}
									}
								});
							}
							catch(RequestException e)
							{
								displayError("Search user exception: " + e);
							}    							
						}
					});
    			}

    			@Override
    			public void onFailure(Throwable caught)
    			{
    				displayError("Auth google failed: " + caught.toString());
    			}
    		});
		}
		catch(Exception e)
		{
			displayError("Exception: " + e);
		}
	}
	
	private void facebookSignIn()
	{
		try
		{
			AuthRequest req = new AuthRequest("https://www.facebook.com/dialog/oauth", "393043390720286").withScopes("user_about_me","email","publish_actions");
			//AuthRequest req = new AuthRequest("https://graph.facebook.com/oauth/authorize", "393043390720286").withScopes("user_about_me","email");
    		Auth.get().login(req, new Callback<String, Throwable>()
    		{
    			@Override
    			public void onSuccess(String token)
    			{
    				displayError("https://graph.facebook.com/oauth/access_token?client_id=393043390720286&redirect_uri=http%3A%2F%2F127.0.0.1%3A8888%2Frapplz%2FoauthWindow.html&client_secret=831851a023652076a92af4f6801a0241&code=" + token);
    				JsonpRequestBuilder jsonpRequestbuilder = new JsonpRequestBuilder();
    				jsonpRequestbuilder.requestObject("https://graph.facebook.com/oauth/access_token?client_id=393043390720286&redirect_uri=http%3A%2F%2F127.0.0.1%3A8888%2Frapplz%2FoauthWindow.html&client_secret=831851a023652076a92af4f6801a0241&code=" + token, new AsyncCallback<FacebookAccessToken>()
					{
    					public void onFailure(Throwable throwable)
						{
							displayError("Couldn't auth facebookb user: " + throwable);
						}

    					public void onSuccess(final FacebookAccessToken facebookAccessToken)
						{
    						Window.alert("good: " + facebookAccessToken.getAccessToken() + " | " + facebookAccessToken.getExpires());
    						/*Cookies.setCookie("sid", googleUser.getId(), expires, null, "/", false);
    						Cookies.setCookie("fn", googleUser.getFirstName(), expires, null, "/", false);
    						Cookies.setCookie("ln", googleUser.getLastName(), expires, null, "/", false);
    						refreshStatus();
    						
    						RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(SEARCH_USER_URL + "/" + googleUser.getId()));
							try
							{
								builder.sendRequest(null, new RequestCallback()
								{
									public void onError(Request request, Throwable exception)
									{
										displayError("Can't search user: " + exception);
									}

									public void onResponseReceived(Request request, Response response)
									{
										if(200 != response.getStatusCode())
										{
											RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(SAVE_USER_URL));
			    							try
			    							{
			    								StringBuffer postData = new StringBuffer();
												postData.append(URL.encode("id")).append("=").append(URL.encode(googleUser.getId()));
												postData.append("&");
												postData.append(URL.encode("firstName")).append("=").append(URL.encode(googleUser.getFirstName()));
												postData.append("&");
												postData.append(URL.encode("lastName")).append("=").append(URL.encode(googleUser.getLastName()));
												postData.append("&");
												postData.append(URL.encode("email")).append("=").append(URL.encode(googleUser.getEmail()));
												if(googleUser.getPicture() != null)
												{
													postData.append("&");
													postData.append(URL.encode("avatar")).append("=").append(URL.encode(googleUser.getPicture()));
												}
												//Window.alert(postData.toString());
												builder.setHeader("Content-type", "application/x-www-form-urlencoded");
												builder.sendRequest(postData.toString(), new RequestCallback()
			    								{
			    									public void onError(Request request, Throwable exception)
			    									{
			    										displayError("Can't save user: " + exception);
			    									}

			    									public void onResponseReceived(Request request, Response response)
			    									{
			    										if(200 == response.getStatusCode())
			    										{
			    											Cookies.setCookie("sid", response.getText(), expires, null, "/", false);
			    										}
			    									}
			    								});
			    							}
			    							catch(RequestException e)
			    							{
			    								displayError("Save user exception: " + e);
			    							}
										}
										else
										{
											displayError("url: " + SEARCH_USER_URL + "/" + googleUser.getId());
											Cookies.setCookie("sid", googleUser.getId(), expires, null, "/", false);
										}
									}
								});
							}
							catch(RequestException e)
							{
								displayError("Search user exception: " + e);
							}*/   							
						}
					});
    			}

    			@Override
    			public void onFailure(Throwable caught)
    			{
    				displayError("Auth facebook failed: " + caught.toString());
    			}
    		});
		}
		catch(Exception e)
		{
			displayError("Exception: " + e);
		}
	}
	
	private void twitterSignIn()
	{
		try
		{
			AuthRequest req = new AuthRequest(AUTH_URL, CLIENT_ID).withScopes(USER_PROFILE_SCOPE, USER_PROFILE_EMAIL_SCOPE);
    		Auth.get().login(req, new Callback<String, Throwable>()
    		{
    			@Override
    			public void onSuccess(String token)
    			{
    				JsonpRequestBuilder jsonpRequestbuilder = new JsonpRequestBuilder();
    				jsonpRequestbuilder.requestObject("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token, new AsyncCallback<GoogleUser>()
					{
    					public void onFailure(Throwable throwable)
						{
							displayError("Couldn't retrieve JSON: " + throwable);
						}

    					public void onSuccess(final GoogleUser googleUser)
						{
    						Cookies.setCookie("sid", googleUser.getId(), expires, null, "/", false);
    						Cookies.setCookie("fn", googleUser.getFirstName(), expires, null, "/", false);
    						Cookies.setCookie("ln", googleUser.getLastName(), expires, null, "/", false);
    						refreshStatus();
    						
    						RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(SEARCH_USER_URL + "/" + googleUser.getId()));
							try
							{
								builder.sendRequest(null, new RequestCallback()
								{
									public void onError(Request request, Throwable exception)
									{
										displayError("Can't search user: " + exception);
									}

									public void onResponseReceived(Request request, Response response)
									{
										if(200 != response.getStatusCode())
										{
											RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(SAVE_USER_URL));
			    							try
			    							{
			    								StringBuffer postData = new StringBuffer();
			    								postData.append(URL.encode("federalType")).append("=").append(URL.encode("google"));
												postData.append("&");
			    								postData.append(URL.encode("id")).append("=").append(URL.encode(googleUser.getId()));
												postData.append("&");
												postData.append(URL.encode("firstName")).append("=").append(URL.encode(googleUser.getFirstName()));
												postData.append("&");
												postData.append(URL.encode("lastName")).append("=").append(URL.encode(googleUser.getLastName()));
												postData.append("&");
												postData.append(URL.encode("email")).append("=").append(URL.encode(googleUser.getEmail()));
												if(googleUser.getPicture() != null)
												{
													postData.append("&");
													postData.append(URL.encode("avatar")).append("=").append(URL.encode(googleUser.getPicture()));
												}
												//Window.alert(postData.toString());
												builder.setHeader("Content-type", "application/x-www-form-urlencoded");
												builder.sendRequest(postData.toString(), new RequestCallback()
			    								{
			    									public void onError(Request request, Throwable exception)
			    									{
			    										displayError("Can't save user: " + exception);
			    									}

			    									public void onResponseReceived(Request request, Response response)
			    									{
			    										if(200 == response.getStatusCode())
			    										{
			    											Cookies.setCookie("sid", response.getText(), expires, null, "/", false);
			    										}
			    									}
			    								});
			    							}
			    							catch(RequestException e)
			    							{
			    								displayError("Save user exception: " + e);
			    							}
										}
										else
										{
											displayError("url: " + SEARCH_USER_URL + "/" + googleUser.getId());
											Cookies.setCookie("sid", googleUser.getId(), expires, null, "/", false);
										}
									}
								});
							}
							catch(RequestException e)
							{
								displayError("Search user exception: " + e);
							}    							
						}
					});
    			}

    			@Override
    			public void onFailure(Throwable caught)
    			{
    				displayError("Auth google failed: " + caught.toString());
    			}
    		});
		}
		catch(Exception e)
		{
			displayError("Exception: " + e);
		}
	}
	
	private void searchApp(String userId, String searchText, final String thanAppId)
	{
		if(userId != null && !userId.trim().equals("") && !userId.trim().equals("0"))
		{
			if(searchText != null && !searchText.trim().equals(""))
			{
				try
			    {
					JsonpRequestBuilder jsonpRequestbuilder = new JsonpRequestBuilder();
			    	jsonpRequestbuilder.requestObject((SEARCH_APP_URL + searchText), new AsyncCallback<AppSearchResult>()
					{
						public void onFailure(Throwable throwable)
						{
							displayError("Couldn't retrieve JSON: " + throwable);
						}

						public void onSuccess(AppSearchResult appSearchResult)
						{
							if(appSearchResult != null)
							{
								final PopupPanel popupPanel = new PopupPanel(false);
								popupPanel.setStyleName("search-app-popup");
								VerticalPanel PopUpPanelContents = new VerticalPanel();
								PopUpPanelContents.setSpacing(5);
								if(appSearchResult.getResultCount() > 0)
								{
									for(int i = 0; i < appSearchResult.getResults().length(); i++)
									{
										final ResultApp resultApp = appSearchResult.getResults().get(i);
										HorizontalPanel horizontalPanel = new HorizontalPanel();
										horizontalPanel.addStyleName("app-box");
										Image image = new Image(resultApp.getArtworkUrl60());
										image.addStyleName("search-app-image");
										Label label = new Label(resultApp.getTrackName());
										label.addStyleName("float-left");
										label.addStyleName("search-app-name");
										HTML button = new HTML("<span class='submit button selected search-app-button'>Recommend</span>");									    										    		
										button.addStyleName("float-right");
										button.addClickHandler(new ClickHandler()
										{
										@Override
										public void onClick(ClickEvent event)
										{
											try
											{
												String userId = Cookies.getCookie("sid");
			    								String appId = String.valueOf(resultApp.getTrackId());
												String name = resultApp.getTrackName();
												String icon = resultApp.getArtworkUrl60();
												String link = resultApp.getTrackViewUrl();
												String price = String.valueOf(resultApp.getPrice());
												if(thanAppId == null)
												{
													recommendApp(userId, appId, name, icon, link, price);
												}
												else
												{
													preferApp(userId, appId, thanAppId, name, icon, link, price);
												}
											}
											catch(Exception e)
											{
												logger.warning("Recommend app [id=" + resultApp.getTrackId() + "name=" + resultApp.getTrackName() + "] exception: " + e);
											}
											popupPanel.removeFromParent();
										}
										});
										horizontalPanel.add(image);
										horizontalPanel.add(label);
										horizontalPanel.add(button);
										PopUpPanelContents.add(horizontalPanel);
									}
								}
								popupPanel.setTitle("Search count: " + appSearchResult.getResultCount());
								HTML message = new HTML("Search count: " + appSearchResult.getResultCount());
								message.setStyleName("demo-PopUpPanel-message");
								
								HTML closeButton = new HTML("<span class='submit button'>Close</span>");									    								    
								closeButton.addClickHandler(new ClickHandler()
								{
									public void onClick(ClickEvent event)
									{
										popupPanel.removeFromParent();
									}
								});
								SimplePanel holder = new SimplePanel();
								holder.add(closeButton);
								holder.setStyleName("demo-PopUpPanel-footer");
								PopUpPanelContents.add(message);
								PopUpPanelContents.add(holder);
								popupPanel.setWidget(PopUpPanelContents );
								popupPanel.setAnimationEnabled(true);
								popupPanel.setGlassEnabled(true);
								popupPanel.center();
							}
						}
					});			      
			    }
				catch (Exception e)
				{
					displayError("Couldn't retrieve JSON");
			    }
			}
		}
		else
		{
			needSignIn();
		}
	}
	
	private void recommendApp(String userId, String appId, String name, String icon, String link, String price) throws Exception
	{
		if(userSignedIn())
		{
			try
			{
				Map<String, String> postData = new HashMap<String, String>();
				postData.put("userId", userId);
				postData.put("appId", appId);
				postData.put("name", name);
				postData.put("icon", icon);
				postData.put("link", link);
				postData.put("price", price);
				sendPostRequest(RECOMMEND_APP_URL, postData);
			}
			catch(Exception e)
			{
				logger.warning("Recommend app exception: " + e);
				throw e;
			}
		}
		else
		{
			needSignIn();
		}
	}
	
	private void preferApp(String userId, String appId, String thanAppId, String name, String icon, String link, String price) throws Exception
	{
		if(userSignedIn())
		{
			try
			{
				Map<String, String> postData = new HashMap<String, String>();
				postData.put("userId", userId);
				postData.put("appId", appId);
				postData.put("thanAppId", thanAppId);
				postData.put("name", name);
				postData.put("icon", icon);
				postData.put("link", link);
				postData.put("price", price);
				sendPostRequest(PREFER_APP_URL, postData);
			}
			catch(Exception e)
			{
				logger.warning("Recommend app exception: " + e);
				throw e;
			}
		}
		else
		{
			needSignIn();
		}
	}
	
	private void sendPostRequest(String url, Map<String, String> postData) throws Exception
	{
		try
		{
			RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
			builder.setHeader("Content-type", "application/x-www-form-urlencoded");
			StringBuffer data = new StringBuffer();
			Set<String> dataKeys = postData.keySet();
			for(String dataKey : dataKeys)
			{
				data.append(URL.encode(dataKey)).append("=").append(URL.encode(postData.get(dataKey))).append("&");
			}
			
			if(data.charAt(data.length() - 1) == '&')
			{
				data.deleteCharAt(data.length() - 1);
			}			
			
			builder.sendRequest(data.toString(), new RequestCallback()
			{
				public void onResponseReceived(Request request, Response response)
				{
					if(200 != response.getStatusCode())
					{
						logger.warning("Receive post failed response code: " + response.getStatusCode());
					}					
				}
				public void onError(Request request, Throwable exception)
				{
					logger.warning("Receive post response exception: " + exception);
		        }
			});
		}
		catch(Exception e)
		{
			logger.warning("Send post request exception: " + e);
			throw e;
		}
	}
	
	private void retrieveAppsInfo()
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(ALL_APPS_SIZE_URL));
		try
		{
			builder.sendRequest(null, new RequestCallback()
			{
				public void onResponseReceived(Request request, Response response)
				{
					if(200 == response.getStatusCode())
					{
						lastUpdatedLabel.setText("Total apps number: " + response.getText());
					}
					else
					{
						logger.warning("Couldn't retrieve apps info, response code: " + response.getStatusCode());
					}
				}
				public void onError(Request request, Throwable exception)
				{
					logger.warning("Couldn't retrieve apps info: " + exception);
		        }
			});
		}
		catch(RequestException e)
		{
			logger.warning("Request exception happened during retrieving apps info: " + e);
		}
	}

	private void retrieveApps(String requestURL)
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(requestURL));
		try
		{
			builder.sendRequest(null, new RequestCallback()
			{
				public void onResponseReceived(Request request, Response response)
				{
					if(200 == response.getStatusCode())
					{
						StringBuilder sb = new StringBuilder(response.getText().trim());
						if(sb.length() > 0)
						{
							if(sb.toString().contains("["))
							{
								updateTable(asArrayOfApp(sb.substring(sb.indexOf("["), sb.lastIndexOf("}"))));
							}
							else
							{
								updateTable(asArrayOfApp("[" + sb.substring((sb.indexOf(":") + 1), sb.lastIndexOf("}")) + "]"));
							}
						}
					}
					else
					{
						logger.warning("Couldn't retrieve apps, response code: " + response.getStatusCode());
					}
				}
				public void onError(Request request, Throwable exception)
				{
					logger.warning("Couldn't retrieve apps: " + exception);
		        }
			});
		}
		catch(RequestException e)
		{
			logger.warning("Request exception happened during retrieving apps: " + e);
		}
	}
	
	private void retrieveAppCompetitors(String appId)
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(ALL_APP_COMPETITORS));
		try
		{
			builder.sendRequest(null, new RequestCallback()
			{
				public void onResponseReceived(Request request, Response response)
				{
					if(200 == response.getStatusCode())
					{
						StringBuilder sb = new StringBuilder(response.getText().trim());
						if(sb.length() > 0)
						{
							if(sb.toString().contains("["))
							{
								updateTable(asArrayOfApp(sb.substring(sb.indexOf("["), sb.lastIndexOf("}"))));
							}
							else
							{
								updateTable(asArrayOfApp("[" + sb.substring((sb.indexOf(":") + 1), sb.lastIndexOf("}")) + "]"));
							}
						}
					}
					else
					{
						logger.warning("Couldn't retrieve apps, response code: " + response.getStatusCode());
					}
				}
				public void onError(Request request, Throwable exception)
				{
					logger.warning("Couldn't retrieve apps: " + exception);
		        }
			});
		}
		catch(RequestException e)
		{
			logger.warning("Request exception happened during retrieving apps: " + e);
		}
	}
	
	private void retrieveAppLikes()
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(ALL_APP_LIKES));
		try
		{
			builder.sendRequest(null, new RequestCallback()
			{
				public void onResponseReceived(Request request, Response response)
				{
					if(200 == response.getStatusCode())
					{
						StringBuilder sb = new StringBuilder(response.getText().trim());
						if(sb.length() > 0)
						{
							if(sb.toString().contains("["))
							{
								updateTable(asArrayOfApp(sb.substring(sb.indexOf("["), sb.lastIndexOf("}"))));
							}
							else
							{
								updateTable(asArrayOfApp("[" + sb.substring((sb.indexOf(":") + 1), sb.lastIndexOf("}")) + "]"));
							}
						}
					}
					else
					{
						logger.warning("Couldn't retrieve apps, response code: " + response.getStatusCode());
					}
				}
				public void onError(Request request, Throwable exception)
				{
					logger.warning("Couldn't retrieve apps: " + exception);
		        }
			});
		}
		catch(RequestException e)
		{
			logger.warning("Request exception happened during retrieving apps: " + e);
		}
	}
	
	private void updateTable(JsArray<App> apps)
	{
		mainAppFlexTable.removeAllRows();
		for(int i = 0; i < apps.length(); i++)
		{
			final App app = apps.get(i);
			
			VerticalPanel appPanel = new VerticalPanel();
	    	appPanel.setSpacing(5);
	    	appPanel.addStyleName("app-box");
	    	
	    	Label label = new Label(app.getName());
	    	appPanel.add(label);
	    	
	    	HorizontalPanel horizontalPanel = new HorizontalPanel();
	    	horizontalPanel.addStyleName("float-left");
	    	horizontalPanel.setSpacing(5);
	    	
			Image image = new Image(app.getImage().trim());
			horizontalPanel.add(image);
			
			VerticalPanel verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(3);
			
			
			HorizontalPanel likePanel = new HorizontalPanel();
			likePanel.getElement().setAttribute("id", "like_" + app.getId());
			likePanel.setSpacing(3);
			HTML count = new HTML("<span class='jn'></span><span class='hn'><span class='in'>" + app.getRecommendedCount() + "</span></span><span class='kn'></span>");
			likePanel.add(count);
			HTML likeButton = new HTML("<span class='button selected'>I like it too.</span>");
			ClickHandler recommendAppClickHandler = new ClickHandler()
		    {
		    	@Override
				public void onClick(ClickEvent event)
				{
		    		try
		    		{
		    			String userId = Cookies.getCookie("sid");
		    			recommendApp(userId, app.getId(), app.getName(), app.getImage(), app.getLink(), String.valueOf(app.getPrice()));
					}
		    		catch (Exception e)
		    		{
		    			logger.warning("Recommend app [id=" + app.getId() + "name=" + app.getName() + "] exception: " + e);
					}
				}
		    };
		    likeButton.addClickHandler(recommendAppClickHandler);
	    	likePanel.add(likeButton);
			verticalPanel.add(likePanel);
			
			HorizontalPanel dislikePanel = new HorizontalPanel();
			dislikePanel.getElement().setAttribute("id", "dislike_" + app.getId());
			dislikePanel.setSpacing(3);
			final TextBox dislikeTextBox = new TextBox();	    	
			dislikePanel.add(dislikeTextBox);
			HTML dislikeButton = new HTML("<span class='button selected'>I have a better one.</span>");
			ClickHandler searchAppClickHandler = new ClickHandler()
		    {
		    	@Override
				public void onClick(ClickEvent event)
				{
		    		String userId = Cookies.getCookie("sid");
		    		String searchText = dislikeTextBox.getValue();
		    		searchApp(userId, searchText, app.getId());
				}
		    };
		    dislikeButton.addClickHandler(searchAppClickHandler);			
			dislikePanel.add(dislikeButton);
			HTML arenaButton = new HTML("<span class='button arena'>Arena</span>");
			dislikePanel.add(arenaButton);
			verticalPanel.add(dislikePanel);
			
	    	horizontalPanel.add(verticalPanel); 	
	    	appPanel.add(horizontalPanel);
	    	
	    	mainAppFlexTable.setWidget(i, 0, appPanel);
	    	mainAppFlexTable.getCellFormatter().addStyleName(i, 0, "app-box");
		}
	    // Clear any errors.
	    errorMsgLabel.setVisible(false);
	}
	
	private boolean userSignedIn()
	{
		if(Cookies.getCookie("sid") != null && !Cookies.getCookie("sid").trim().equals("") && !Cookies.getCookie("sid").trim().equals("0"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void needSignIn()
	{
		Window.alert("Please login first.");
	}
	
	private void displayError(String error)
	{
		errorMsgLabel.setText("Error: " + error);
		errorMsgLabel.setVisible(true);
	}
	
	private final native AppSearchResult asAppSearchResult(String json) /*-{return eval(json);}-*/;
	private final native JsArray<App> asArrayOfApp(String json) /*-{return eval(json);}-*/;
	private final native GoogleUser asGoogleUser(String json) /*-{return eval(json);}-*/;
	private final native JsArray<Activity> asArrayOfActivity(String json) /*-{return eval(json);}-*/;
}