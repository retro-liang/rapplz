package com.retro.rapplz.web.dto;

import java.io.Serializable;

public class AppInfo implements Serializable
{
	private static final long serialVersionUID = -974657587492152412L;
	
	private String id;
	private String rawId;
	private String name;
	private String[] categoryNames;
	private String icon;
	private int haveCount;
	private int recommendationCount;

	public AppInfo()
	{
		
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String[] getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String[] categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getRawId()
	{
		return rawId;
	}

	public void setRawId(String rawId)
	{
		this.rawId = rawId;
	}

	public int getHaveCount()
	{
		return haveCount;
	}

	public void setHaveCount(int haveCount)
	{
		this.haveCount = haveCount;
	}

	public int getRecommendationCount()
	{
		return recommendationCount;
	}

	public void setRecommendationCount(int recommendationCount)
	{
		this.recommendationCount = recommendationCount;
	}
}