package com.retro.rapplz.web.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude={"categoryNames", "icon", "haveCount", "recommendationCount"})
@ToString(exclude={"categoryNames", "icon", "haveCount", "recommendationCount"})
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
}