package com.retro.rapplz.db.dao;

public interface NewsletterDao extends BaseDao
{
	public boolean isEmailExist(String email);
	public void deleteByEmail(String email);
}