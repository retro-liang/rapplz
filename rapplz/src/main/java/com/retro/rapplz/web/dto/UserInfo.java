package com.retro.rapplz.web.dto;

import java.io.Serializable;

public class UserInfo implements Serializable
{
	private static final long serialVersionUID = -8368568975784154961L;
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String avatar;
	private String status;
	private int followerCount;
	private int followingCount;
	private int appCount;
	private int recommendationCount;
	
	public UserInfo()
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

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getFollowerCount()
	{
		return followerCount;
	}

	public void setFollowerCount(int followerCount)
	{
		this.followerCount = followerCount;
	}

	public int getFollowingCount()
	{
		return followingCount;
	}

	public void setFollowingCount(int followingCount)
	{
		this.followingCount = followingCount;
	}

	public int getAppCount()
	{
		return appCount;
	}

	public void setAppCount(int appCount)
	{
		this.appCount = appCount;
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