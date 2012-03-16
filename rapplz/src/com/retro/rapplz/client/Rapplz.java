package com.retro.rapplz.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
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
public class Rapplz implements EntryPoint
{
	private static final int REFRESH_DELAY = 1000 * 3;
	private static final int REFRESH_INTERVAL = 1000 * 60 * 60 *24; // ms
	private static final String JSON_URL = "/rest/appService/getAll";
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable mainAppFlexTable = new FlexTable();
	
	private VerticalPanel loginPanel = new VerticalPanel();
	
	private Label recommandAppLabel = new Label("I want to recommand an app: ");
	private TextBox recommandAppTextBox = new TextBox();
	private Button recommandAppButton = new Button("Recommand");
	private HorizontalPanel commandBarPanel = new HorizontalPanel();	
	
	
	private Label lastUpdatedLabel = new Label();
	private ArrayList<String> apps = new ArrayList<String>();
	private Label errorMsgLabel = new Label();
	
	private Image facebookLoginIcon = new Image("/images/signin_providers_icon/facebook.gif");
	private Image twitterLoginIcon = new Image("/images/signin_providers_icon/twitter.gif");
	private Image googleLoginIcon = new Image("/images/signin_providers_icon/google.gif");
	private Image liveLoginIcon = new Image("/images/signin_providers_icon/live.gif");
	private Image yahooLoginIcon = new Image("/images/signin_providers_icon/yahoo.gif");
	private Image aolLoginIcon = new Image("/images/signin_providers_icon/aol.gif");
	private Image myspaceLoginIcon = new Image("/images/signin_providers_icon/myspace.gif");
	private Image openidLoginIcon = new Image("/images/signin_providers_icon/openid.gif");
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
	    // Add styles to elements in the stock list table.
	    mainAppFlexTable.setCellPadding(6);
	    mainAppFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
	    mainAppFlexTable.addStyleName("watchList");
	    mainAppFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	    mainAppFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
	    mainAppFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");

	    // Assemble Add Stock panel.
	    //addPanel.add(googleLoginIcon);
	    //addStockButton.setHTML("<img src='/images/signin_providers_icon/google.gif'");
	    //addPanel.add(addStockButton);
	    //addPanel.addStyleName("addPanel");

	    // Assemble Main panel.
	    errorMsgLabel.setStyleName("errorMessage");
	    errorMsgLabel.setVisible(false);

	    //mainPanel.add(addPanel);
	    mainPanel.add(errorMsgLabel);
	    mainPanel.add(mainAppFlexTable);	    
	    mainPanel.add(lastUpdatedLabel);
	    
	    loginPanel.add(facebookLoginIcon);
	    loginPanel.add(twitterLoginIcon);
	    loginPanel.add(googleLoginIcon);
	    //loginPanel.add(liveLoginIcon);
	    //loginPanel.add(yahooLoginIcon);
	    //loginPanel.add(aolLoginIcon);
	    //loginPanel.add(myspaceLoginIcon);
	    //loginPanel.add(openidLoginIcon);
	    
	    commandBarPanel.add(recommandAppLabel);
	    commandBarPanel.add(recommandAppTextBox);
	    commandBarPanel.add(recommandAppButton);

	    // Associate the Main panel with the HTML host page.
	    RootPanel.get("appList").add(mainPanel);
	    RootPanel.get("commandBar").add(commandBarPanel);
	    RootPanel.get("login-box").add(loginPanel);
	    
	    
	    

	    // Move cursor focus to the input box.
	    //newSymbolTextBox.setFocus(true);

	    // Setup timer to refresh list automatically.
	    Timer refreshTimer = new Timer() {
	      @Override
	      public void run() {
	        refreshAppList();
	      }
	    };
	    
	    refreshTimer.schedule(REFRESH_DELAY);
	    //refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	    

	    // Listen for mouse events on the Add button.
	    googleLoginIcon.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
	  		String CLIENT_ID = "929855298687.apps.googleusercontent.com"; // available from the APIs console
	  		String BUZZ_READONLY_SCOPE = "https://www.googleapis.com/auth/buzz.readonly";
	  		String BUZZ_PHOTOS_SCOPE = "https://www.googleapis.com/auth/photos";
	  		String USER_PROFILE_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";

	  		AuthRequest req = new AuthRequest(AUTH_URL, CLIENT_ID).withScopes(USER_PROFILE_SCOPE, BUZZ_READONLY_SCOPE, BUZZ_PHOTOS_SCOPE); // Can specify multiple scopes here
	  		
	  		Auth.get().login(req, new Callback<String, Throwable>() {
	  			  @Override
	  			  public void onSuccess(String token) {
	  			    // You now have the OAuth2 token needed to sign authenticated requests.
	  				  Window.alert("OK: " + token);
	  				RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token);
	  				Window.alert("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token);
	  			    try {
	  			      Request request = builder.sendRequest(null, new RequestCallback() {
	  			        public void onError(Request request, Throwable exception) {
	  			          displayError("Couldn't retrieve JSON: " + exception);
	  			        }

	  			        public void onResponseReceived(Request request, Response response) {
	  			        	displayError("User Profile: " + asUser(response.getText()).getId() + " | " + asUser(response.getText()).getName());
	  			        }
	  			      });
	  			    } catch (RequestException e) {
	  			      displayError("Couldn't retrieve JSON");
	  			    }
	  			  }
	  			  @Override
	  			  public void onFailure(Throwable caught) {
	  			    // The authentication process failed for some reason, see caught.getMessage()
	  				Window.alert("No: " + caught.toString());
	  			  }
	  			});
	      }
	    });
	    
	    
	    recommandAppButton.addClickHandler(new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event)
			{
				String searchURL = "http://itunes.apple.com/search?country=CA&entity=software&limit=10&term=pinterest";
				
				RequestBuilder requestbuilder = new RequestBuilder(RequestBuilder.GET, URL.encode(searchURL));

			    try
			    {
			      requestbuilder.sendRequest(null, new RequestCallback() {
			        public void onError(Request request, Throwable exception) {
			          displayError("Couldn't retrieve JSON: " + exception);
			        }

			        public void onResponseReceived(Request request, Response response) {
			          if (200 == response.getStatusCode())
			          {
			        	  StringBuilder sb = new StringBuilder(response.getText().trim());
			        	  Window.alert(sb.toString());
			        	  AppSearchResult appSearchResult = asAppSearchResult(sb.toString());
			        	  
			        	  if(appSearchResult != null)
			        	  {
			        		  PopupPanel popup = new PopupPanel(false);
							    popup.setStyleName("demo-PopUpPanel");
							    VerticalPanel PopUpPanelContents = new VerticalPanel();
							    popup.setTitle("Search count: " + appSearchResult.getResultCount());
							    HTML message = new HTML("Click 'Close' to close");
							    message.setStyleName("demo-PopUpPanel-message");
							    
							    Button button = new Button("Close");
							    SimplePanel holder = new SimplePanel();
							    holder.add(button);
							    holder.setStyleName("demo-PopUpPanel-footer");
							    PopUpPanelContents.add(message);
							    PopUpPanelContents.add(holder);
							    popup.setWidget(PopUpPanelContents);
								
								
							    popup.setAnimationEnabled(true);
								
							    popup.center();
			        	  }
			          } else {
			            displayError("Couldn't retrieve JSON (" + response.getStatusCode() + ")");
			          }
			        }
			      });
			    } catch (RequestException e) {
			      displayError("Couldn't retrieve JSON");
			    }
				
				
				
			}	    	
	    });

	    // Listen for keyboard events in the input box.
	    /*newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
	      public void onKeyPress(KeyPressEvent event) {
	        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
	          addStock();
	        }
	      }
	    });*/
	}
	
	/**
	   * Add stock to FlexTable. Executed when the user clicks the addStockButton or
	   * presses enter in the newSymbolTextBox.
	   */
	  /*private void addStock() {
	    final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
	    newSymbolTextBox.setFocus(true);

	    // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
	    if (!symbol.matches("^[0-9a-zA-Z\\.]{1,10}$")) {
	      Window.alert("'" + symbol + "' is not a valid symbol.");
	      newSymbolTextBox.selectAll();
	      return;
	    }

	    newSymbolTextBox.setText("");

	    // Don't add the stock if it's already in the table.
	    if (apps.contains(symbol))
	      return;

	    // Add the stock to the table.
	    int row = mainAppFlexTable.getRowCount();
	    apps.add(symbol);
	    mainAppFlexTable.setText(row, 0, symbol);
	    mainAppFlexTable.setWidget(row, 2, new Label());
	    mainAppFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
	    mainAppFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
	    mainAppFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");

	    // Add a button to remove this stock from the table.
	    Button removeStockButton = new Button("x");
	    removeStockButton.addStyleDependentName("remove");
	    removeStockButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        int removedIndex = apps.indexOf(symbol);
	        apps.remove(removedIndex);
	        mainAppFlexTable.removeRow(removedIndex + 1);
	      }
	    });
	    mainAppFlexTable.setWidget(row, 3, removeStockButton);

	    // Get the stock price.
	    refreshAppList();

	  }*/
	
	/**
	   * Generate random stock prices.
	   */
	private void refreshAppList()
	{
		// Send request to server and catch any errors.
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(JSON_URL));

	    try {
	      Request request = builder.sendRequest(null, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          displayError("Couldn't retrieve JSON: " + exception);
	        }

	        public void onResponseReceived(Request request, Response response) {
	          if (200 == response.getStatusCode())
	          {
	        	  StringBuilder sb = new StringBuilder(response.getText().trim());
	        	  //displayError("OK: " + sb.substring(sb.indexOf("["), sb.lastIndexOf("}")));	        	  
	        	  updateTable(asArrayOfApp(sb.substring(sb.indexOf("["), sb.lastIndexOf("}"))));
	          } else {
	            displayError("Couldn't retrieve JSON (" + response.getStatusCode() + ")");
	          }
	        }
	      });
	    } catch (RequestException e) {
	      displayError("Couldn't retrieve JSON");
	    }
	}	
	
	
	  
	  /**
	   * Update the Price and Change fields all the rows in the stock table.
	   *
	   * @param prices Stock data for all rows.
	   */
	  private void updateTable(JsArray<App> apps)
	  {
		  
	    for (int i = 0; i < apps.length(); i++) {
	    	/*DialogBox dialogBox = new DialogBox();
	    	dialogBox.setText(apps.get(i).getName());
	    	VerticalPanel dialogVPanel = new VerticalPanel();
			dialogVPanel.addStyleName("dialogVPanel");
			dialogVPanel.add(new HTML("<img src='" + apps.get(i).getImage().trim() + "' />"));
			dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);			
			dialogBox.setWidget(dialogVPanel);
	    	dialogBox.setAnimationEnabled(true);*/
	    	
	    	mainAppFlexTable.setHTML((i / 12), (i % 12), "<img src='" + apps.get(i).getImage().trim() + "' />");	    	
	    }

	    // Display timestamp showing last refresh.
	    lastUpdatedLabel.setText("Last update : " + new Date());

	    // Clear any errors.
	    errorMsgLabel.setVisible(false);
	  }

	  /**
	   * Update a single row in the stock table.
	   *
	   * @param price Stock data for a single row.
	   */
	  private void updateTable(App app)
	  {
		  int row = apps.indexOf(app) + 1;
		  
	  }
	  
	  public void displayApps(JsArray<App> apps)
	  {
		  for (int i = 0; i < apps.length(); i++)
    	  {
    		  	//dialogBox.setText(apps.get(i).getName());
				//serverResponseLabel.addStyleName("serverResponseLabelError");
				//serverResponseLabel.setHTML("<img src='" + apps.get(i).getImage() + "' />");
				//dialogBox.center();
				//closeButton.setFocus(true);
    	  }
	  }
	  
	private final native AppSearchResult asAppSearchResult(String json) /*-{return eval(json);}-*/;
	private final native JsArray<App> asArrayOfApp(String json) /*-{return eval(json);}-*/;
	private final native User asUser(String json) /*-{return eval(json);}-*/;
	
	/**
	   * If can't get JSON, display error message.
	   * @param error
	   */
	  private void displayError(String error) {
	    errorMsgLabel.setText("Error: " + error);
	    errorMsgLabel.setVisible(true);
	  }
}
