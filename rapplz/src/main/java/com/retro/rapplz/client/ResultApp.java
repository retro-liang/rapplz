package com.retro.rapplz.client;

import com.google.gwt.core.client.JavaScriptObject;

public class ResultApp extends JavaScriptObject
{
	protected ResultApp()
	{
		
	}
	
	public final native String getKind() /*-{return this.kind;}-*/;
	public final native String[] getFeatures() /*-{return this.features;}-*/;
	public final native String[] getSupportedDevices() /*-{return this.supportedDevices;}-*/;
	public final native boolean isGameCenterEnabled() /*-{return this.isGameCenterEnabled;}-*/;
	public final native String getArtistViewUrl() /*-{return this.artistViewUrl;}-*/;
	public final native String getArtworkUrl60() /*-{return this.artworkUrl60;}-*/;
	public final native String[] getScreenshotUrls() /*-{return this.screenshotUrls;}-*/;
	public final native String[] getIpadScreenshotUrls() /*-{return this.ipadScreenshotUrls;}-*/;
	public final native String getArtworkUrl512() /*-{return this.artworkUrl512;}-*/;
	public final native int getArtistId() /*-{return this.artistId;}-*/;
	public final native String getArtistName() /*-{return this.artistName;}-*/;
	public final native double getPrice() /*-{return this.price;}-*/;
	public final native String getVersion() /*-{return this.version;}-*/;
	public final native String getDescription() /*-{return this.description;}-*/;
	public final native String[] getGenreIds() /*-{return this.genreIds;}-*/;
	public final native String getReleaseDate() /*-{return this.releaseDate;}-*/;
	public final native String getSellerName() /*-{return this.sellerName;}-*/;
	public final native String getCurrency() /*-{return this.currency;}-*/;
	public final native String[] getGenres() /*-{return this.genres;}-*/;
	public final native String getBundleId() /*-{return this.bundleId;}-*/;
	public final native int getTrackId() /*-{return this.trackId;}-*/;
	public final native String getTrackName() /*-{return this.trackName;}-*/;
	public final native String getPrimaryGenreName() /*-{return this.primaryGenreName;}-*/;
	public final native int getPrimaryGenreId() /*-{return this.primaryGenreId;}-*/;
	public final native String getReleaseNotes() /*-{return this.releaseNotes;}-*/;
	public final native String getWrapperType() /*-{return this.wrapperType;}-*/;
	public final native String getTrackCensoredName() /*-{return this.trackCensoredName;}-*/;
	public final native String getTrackViewUrl() /*-{return this.trackViewUrl;}-*/;
	public final native String getContentAdvisoryRating() /*-{return this.contentAdvisoryRating;}-*/;
	public final native String getArtworkUrl100() /*-{return this.artworkUrl100;}-*/;
	public final native String[] getLanguageCodesISO2A() /*-{return this.languageCodesISO2A;}-*/;
	public final native String getFileSizeBytes() /*-{return this.fileSizeBytes;}-*/;
	public final native String getSellerUrl() /*-{return this.sellerUrl;}-*/;
	public final native double getAverageUserRatingForCurrentVersion() /*-{return this.averageUserRatingForCurrentVersion;}-*/;
	public final native int getUserRatingCountForCurrentVersion() /*-{return this.userRatingCountForCurrentVersion;}-*/;
	public final native String getTrackContentRating() /*-{return this.trackContentRating;}-*/;
	public final native double getAverageUserRating() /*-{return this.averageUserRating;}-*/;
	public final native int getUserRatingCount() /*-{return this.userRatingCount;}-*/;
}