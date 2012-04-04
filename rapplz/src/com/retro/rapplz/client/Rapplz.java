package com.retro.rapplz.client;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.gwt.user.client.ui.Button;
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
	
	private static final int REFRESH_DELAY = 1000;
	private static final int REFRESH_INTERVAL = 1000 * 60 * 60 *24; // ms
	
	private static final String SEARCH_USER_URL = "/rest/userService/search";
	private static final String SAVE_USER_URL = "/rest/userService/save";
	private static final String ALL_APPS_JSON_URL = "/rest/appService/all";
	private static final String ALL_FEATURED_APPS_JSON_URL = "/rest/appService/tag/featured";
	private static final String ALL_RECOMMENDED_APPS_JSON_URL = "/rest/appService/tag/recommended";
	private static final String ALL_APPS_SIZE_URL = "/rest/appService/allAppsSize";
	private static final String SEARCH_APP_URL = "http://itunes.apple.com/search?country=US&entity=software&limit=10&term=";
	private static final String ADD_RECOMMEND_APP_URL = "/rest/appService/recommend";
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
		refreshStatus();
		
	    // Add styles to elements in the stock list table.
	    mainAppFlexTable.setCellPadding(6);
	    mainAppFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
	    mainAppFlexTable.addStyleName("watchList");
	    mainAppFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	    mainAppFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
	    mainAppFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");

	    // Assemble Main panel.
	    errorMsgLabel.setStyleName("errorMessage");
	    errorMsgLabel.setVisible(false);

	    //mainPanel.add(addPanel);
	    mainPanel.add(errorMsgLabel);
	    mainPanel.add(mainAppFlexTable);	    
	    mainPanel.add(lastUpdatedLabel);
	    
	    RootPanel.get("appList").add(mainPanel);	    
	    
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
	    RootPanel.get("facebook-sign-in-button").addDomHandler(facebookSignInClickHandler, ClickEvent.getType());
	    
	    ClickHandler searchAppClickHandler = new ClickHandler()
	    {
	    	@Override
			public void onClick(ClickEvent event)
			{
	    		searchApp();
			}
	    };
	    RootPanel.get("search-app-button").addDomHandler(searchAppClickHandler, ClickEvent.getType());	
	    
	    KeyPressHandler searchAppTextKeyPressHandler = new KeyPressHandler()
	    {
	    	public void onKeyPress(KeyPressEvent event)
	    	{
	    		if(event.getCharCode() == KeyCodes.KEY_ENTER)
	    		{
	    			searchApp();
	    		}
	    	}
	    };
	    RootPanel.get("search-app-text").addDomHandler(searchAppTextKeyPressHandler, KeyPressEvent.getType());
	    

	    // Setup timer to refresh list automatically.
	    Timer refreshTimer = new Timer()
	    {
	      @Override
	      public void run()
	      {
	    	  retrieveAppsInfo();
	    	  retrieveApps(ALL_RECOMMENDED_APPS_JSON_URL);
	    	  //retrieveApps(ALL_APPS_JSON_URL);
	      }
	    };
	    
	    refreshTimer.schedule(REFRESH_DELAY);
	    //refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	    
	    setupChannel();
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
	    				//Window.alert("Received: " + message + " length: " + message.trim().split("|").length);
	    				if(message != null && !message.trim().equals("") && message.trim().split("|").length == 6)
	    				{
	    					//Window.alert("creating popup");
	    					createActivityPopup(message.split("|"));
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
	
	private void createActivityPopup(String[] attributes)
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
		Image userAvarta = new Image(attributes[1]);
		Label userName = new Label(attributes[2]);
		Label staticLabel = new Label(" has just recommended an app: ");
		Image appIcon = new Image(attributes[4]);
		Label appName = new Label(attributes[5]);
		activityHorizontalPanel.add(userAvarta);
		activityHorizontalPanel.add(userName);
		activityHorizontalPanel.add(staticLabel);
		activityHorizontalPanel.add(appIcon);
		activityHorizontalPanel.add(appName);
		dialogBox.setWidget(activityHorizontalPanel);		
		dialogBox.setPopupPosition(100, 50);
		dialogBox.show();
		
		new Timer()
	    {
			@Override
			public void run()
			{
				dialogBox.removeFromParent();
			}
	    };
	}
	
	private void refreshStatus()
	{
		if(Cookies.getCookie("sid") != null)
		{
			RootPanel.get("user-text").getElement().setInnerText(Cookies.getCookie("fn") + " " + Cookies.getCookie("ln"));
			RootPanel.get("sign-text").getElement().setInnerText("Sign out");
			
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
		    		Cookies.removeCookie("sid");
		    		Cookies.removeCookie("fn");
		    		Cookies.removeCookie("ln");
		    		RootPanel.get("user-text").getElement().setInnerText("");
		    		RootPanel.get("sign-text").getElement().setInnerText("");
				}
		    };
		    RootPanel.get("sign-link").addDomHandler(signOutClickHandler, ClickEvent.getType());
		    RootPanel.get("sign-text").addDomHandler(signOutClickHandler, ClickEvent.getType());
		}
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
	
	private void facebookSignIn()
	{
		try
		{
			AuthRequest req = new AuthRequest("https://www.facebook.com/dialog/oauth", "393043390720286").withScopes("user_about_me");
    		Auth.get().login(req, new Callback<String, Throwable>()
    		{
    			@Override
    			public void onSuccess(String token)
    			{
    				Window.alert("facebook token: " + token);
    				displayError("facebook access url: " + "https://graph.facebook.com/oauth/access_token?client_id=393043390720286&client_secret=831851a023652076a92af4f6801a0241&redirect_uri=http://www.rapplz.com/&code=" + token);
    				/*JsonpRequestBuilder jsonpRequestbuilder = new JsonpRequestBuilder();
    				jsonpRequestbuilder.requestObject("https://graph.facebook.com/oauth/access_token?client_id=YOUR_APP_ID&client_secret=YOUR_APP_SECRET&redirect_uri=www.rapplz.com&code=" + token, new AsyncCallback<GoogleUser>()
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
					});*/
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
	
	private void searchApp()
	{
		if(Cookies.getCookie("sid") != null && !Cookies.getCookie("sid").trim().equals(""))
		{
			if(RootPanel.get("search-app-text").getElement().getPropertyString("value") != null && !RootPanel.get("search-app-text").getElement().getPropertyString("value").trim().equals(""))
			{
				try
			    {
					JsonpRequestBuilder jsonpRequestbuilder = new JsonpRequestBuilder();
			    	jsonpRequestbuilder.requestObject((SEARCH_APP_URL + RootPanel.get("search-app-text").getElement().getPropertyString("value")), new AsyncCallback<AppSearchResult>()
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
									    if(appSearchResult.getResultCount() > 0)
									    {
									    	for(int i = 0; i < appSearchResult.getResults().length(); i++)
									    	{
									    		final ResultApp resultApp = appSearchResult.getResults().get(i);
									    		HorizontalPanel horizontalPanel = new HorizontalPanel();
									    		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
									    		Image image = new Image(resultApp.getArtworkUrl60());
									    		image.addStyleName("search-app-image");
									    		Label label = new Label(resultApp.getTrackName());
									    		label.addStyleName("search-app-name");
									    		Button button = new Button("Recommend");
									    		button.addStyleName("submit");
									    		button.addStyleName("button");
									    		button.addStyleName("selected");
									    		button.addStyleName("search-app-button");								    		
									    		
									    		button.addClickHandler(new ClickHandler()
									    		{
												@Override
												public void onClick(ClickEvent event)
												{
													try
													{
														RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(ADD_RECOMMEND_APP_URL));
														StringBuffer postData = new StringBuffer();
														postData.append(URL.encode("userId")).append("=").append(URL.encode(Cookies.getCookie("sid")));
														postData.append("&");
														postData.append(URL.encode("appId")).append("=").append(URL.encode(String.valueOf(resultApp.getTrackId())));
														postData.append("&");
														postData.append(URL.encode("name")).append("=").append(URL.encode(resultApp.getTrackName()));
														postData.append("&");
														postData.append(URL.encode("icon")).append("=").append(URL.encode(resultApp.getArtworkUrl60()));
														postData.append("&");
														postData.append(URL.encode("link")).append("=").append(URL.encode(resultApp.getTrackViewUrl()));
														postData.append("&");
														postData.append(URL.encode("price")).append("=").append(URL.encode(String.valueOf(resultApp.getPrice())));
														builder.setHeader("Content-type", "application/x-www-form-urlencoded");
														builder.sendRequest(postData.toString(), new RequestCallback()
														{
															public void onResponseReceived(Request request, Response response)
															{
																if(200 == response.getStatusCode())
																{
																	lastUpdatedLabel.setText("App added successfully: " + response.getText());
																}
																else
																{
																	logger.warning("Couldn't add recommend app, response code: " + response.getStatusCode());
																}
															}
															public void onError(Request request, Throwable exception)
															{
																logger.warning("Couldn't add recommend app: " + exception);
													        }
														});
													}
													catch(Exception e)
													{
														logger.warning("Request exception happened during adding recommend app: " + e);
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
									    
									    Button closeButton = new Button("Close");
									    closeButton.addStyleName("submit");
									    closeButton.addStyleName("button");								    
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
			    } catch (Exception e) {
			      displayError("Couldn't retrieve JSON");
			    }
			}
		}
		else
		{
			Window.alert("Please login first.");
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
	
	private void updateTable(JsArray<App> apps)
	{
		for(int i = 0; i < apps.length(); i++)
		{
	    	/*DialogBox dialogBox = new DialogBox();
	    	dialogBox.setText(apps.get(i).getName());
	    	VerticalPanel dialogVPanel = new VerticalPanel();
			dialogVPanel.addStyleName("dialogVPanel");
			dialogVPanel.add(new HTML("<img src='" + apps.get(i).getImage().trim() + "' />"));
			dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);			
			dialogBox.setWidget(dialogVPanel);
	    	dialogBox.setAnimationEnabled(true);*/
	    	
	    	mainAppFlexTable.setHTML((i / 5), (i % 5), "<img src='" + apps.get(i).getImage().trim() + "' width='50%' style='float:left;' /><span class='jn'></span><span class='hn'><span class='in'>" + apps.get(i).getRecommendedCount() + "</span></span><span class='kn'></span><div>");	    	
		}
	    // Clear any errors.
	    errorMsgLabel.setVisible(false);
	}
	  
	private void displayError(String error)
	{
		errorMsgLabel.setText("Error: " + error);
		errorMsgLabel.setVisible(true);
	}
	
	private final native AppSearchResult asAppSearchResult(String json) /*-{return eval(json);}-*/;
	private final native JsArray<App> asArrayOfApp(String json) /*-{return eval(json);}-*/;
	private final native GoogleUser asGoogleUser(String json) /*-{return eval(json);}-*/;
}