package com.retro.rapplz.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

public class AppSearchResult extends JavaScriptObject
{
	//Overlay types always have protected, zero argument constructors.
	protected AppSearchResult()
	{
	}

	// JSNI methods to get app data.
	public final native int getResultCount() /*-{return this.resultCount;}-*/;
	public final native List<App> getResults() /*-{return this.results}-*/;
}