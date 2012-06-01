package com.retro.rapplz.client;

import com.google.gwt.core.client.JavaScriptObject;


public class App extends JavaScriptObject
{
	// Overlay types always have protected, zero argument constructors.
	protected App()
	{
	}

	// JSNI methods to get app data.
	public final native String getId() /*-{return this.id;}-*/;	
	public final native String getName() /*-{return this.name;}-*/;	
	public final native String getLink() /*-{return this.link;}-*/;	
	public final native String getContentType() /*-{return this.contentType;}-*/;	
	public final native String getCategory() /*-{return this.category;}-*/;	
	public final native String getArtist() /*-{return this.artist;}-*/;
	public final native String getPrice() /*-{return this.price;}-*/;
	public final native String getImage() /*-{return this.image;}-*/;
	public final native String getReleaseDate() /*-{return this.releaseDate;}-*/;
	public final native String getUpdateDate() /*-{return this.updateDate;}-*/;
	public final native String getRecommendedCount() /*-{return this.recommendedCount;}-*/;
}