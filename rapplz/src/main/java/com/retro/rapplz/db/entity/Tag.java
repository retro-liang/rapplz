package com.retro.rapplz.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Table(name="tag")
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true, includeFieldNames=true)
public class Tag extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="tag_name_id")
	private TagName tagName;
	
	@ManyToOne
	@JoinColumn(name="author_user_id")
	private User author;
	
	@ManyToOne
	@JoinColumn(name="app_id")
	private App app;
}