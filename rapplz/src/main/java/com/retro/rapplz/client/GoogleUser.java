package com.retro.rapplz.client;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleUser extends JavaScriptObject
{
	// Overlay types always have protected, zero argument constructors.
	protected GoogleUser()
	{
	}

	// JSNI methods to get app data.
	public final native String getId() /*-{return this.id;}-*/;
	public final native String getName() /*-{return this.name;}-*/;
	public final native String getFirstName() /*-{return this.given_name;}-*/;
	public final native String getLastName() /*-{return this.family_name;}-*/;
	public final native String getGender() /*-{return this.gender;}-*/;
	public final native String getLink() /*-{return this.link;}-*/;
	public final native String getEmail() /*-{return this.email;}-*/;
	public final native String getPicture() /*-{return this.photo;}-*/;
}