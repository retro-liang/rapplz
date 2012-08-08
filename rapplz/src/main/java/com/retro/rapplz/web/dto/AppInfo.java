package com.retro.rapplz.web.dto;

import java.io.Serializable;
import java.util.Set;

import com.retro.rapplz.db.entity.Category;

public class AppInfo implements Serializable
{
	private static final long serialVersionUID = -974657587492152412L;
	
	private String id;
	private String rawId;
	private String name;
	private Set<Category> categories;
	private String company;
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

	public String getCompany()
	{
		return company;
	}

	public void setCompany(String company)
	{
		this.company = company;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public Set<Category> getCategories()
	{
		return categories;
	}

	public void setCategories(Set<Category> categories)
	{
		this.categories = categories;
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