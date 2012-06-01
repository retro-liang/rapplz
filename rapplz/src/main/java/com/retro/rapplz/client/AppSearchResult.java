package com.retro.rapplz.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class AppSearchResult extends JavaScriptObject
{
	//Overlay types always have protected, zero argument constructors.
	protected AppSearchResult()
	{
	}

	// JSNI methods to get app data.
	public final native int getResultCount() /*-{return this.resultCount;}-*/;
	public final native JsArray<ResultApp> getResults() /*-{return this.results}-*/;
}