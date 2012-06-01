package com.retro.rapplz.client;

import com.google.gwt.core.client.JavaScriptObject;

public class FacebookAccessToken extends JavaScriptObject
{
	protected FacebookAccessToken()
	{
		
	}
	
	// JSNI methods to get app data.
	public final native String getAccessToken() /*-{return this.access_token;}-*/;
	public final native String getExpires() /*-{return this.expires;}-*/;
}