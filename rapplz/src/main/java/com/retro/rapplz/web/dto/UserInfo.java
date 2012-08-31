package com.retro.rapplz.web.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude={"lastName", "avatar", "status", "appCount", "followerCount", "followingCount", "recommendationCount"})
@ToString(exclude={"lastName", "avatar", "status", "appCount", "followerCount", "followingCount", "recommendationCount"})
public class UserInfo implements Serializable
{
	private static final long serialVersionUID = -8368568975784154961L;
	
	private String id;
	private String token;
	private String firstName;
	private String lastName;
	private String email;
	private String avatar;
	private String status;
	private int appCount;
	private int followerCount;
	private int followingCount;
	private int recommendationCount;
}