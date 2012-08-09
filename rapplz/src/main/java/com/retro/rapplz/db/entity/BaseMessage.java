package com.retro.rapplz.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@MappedSuperclass
@ToString(callSuper=true, exclude={"jdoDetachedState"})
@EqualsAndHashCode(callSuper=true, exclude={"jdoDetachedState", "content"})
public class BaseMessage extends BaseEntity
{
	@ManyToOne
	@JoinColumn(name="author_user_id")
	@Getter
	@Setter
	private User author;
	
	@Getter
	@Setter
	private String content;
	
	@Transient
	protected Object[] jdoDetachedState;
}