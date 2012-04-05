package com.retro.rapplz.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Activity extends JavaScriptObject
{
	protected Activity()
	{
	}

	// JSNI methods to get app data.
	public final native String getType() /*-{return this.t;}-*/;
	public final native String getUserId() /*-{return this.uid;}-*/;	
	public final native String getUserName() /*-{return this.un;}-*/;	
	public final native String getUserAvatar() /*-{return this.ua;}-*/;	
	public final native String getAppId() /*-{return this.aid;}-*/;	
	public final native String getAppName() /*-{return this.an;}-*/;
	public final native String getAppIcon() /*-{return this.ai;}-*/;	
}