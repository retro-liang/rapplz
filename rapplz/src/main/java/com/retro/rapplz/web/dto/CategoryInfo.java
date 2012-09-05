package com.retro.rapplz.web.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryInfo implements Serializable
{
	private static final long serialVersionUID = 1395142228282593345L;
	
	private String id;
	private String name;
}
