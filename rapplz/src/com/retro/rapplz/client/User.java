package com.retro.rapplz.client;

import com.google.gwt.core.client.JavaScriptObject;

public class User extends JavaScriptObject
{
	// Overlay types always have protected, zero argument constructors.
	protected User()
	{
	}

	// JSNI methods to get app data.
	public final native String getId() /*-{return this.id;}-*/;
	public final native String getName() /*-{return this.name;}-*/;
	public final native String getEmail() /*-{return this.email;}-*/;
	public final native String picture() /*-{return this.picture;}-*/;
}