package com.retro.rapplz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retro.rapplz.db.dao.NewsletterDao;
import com.retro.rapplz.db.entity.Newsletter;

@Service("newsletterService")
@Transactional
public class NewsletterServiceImpl implements NewsletterService
{
	@Autowired
	private NewsletterDao newsletterDao;
	
	@Override
	public void subscribe(String email)
	{
		if(!newsletterDao.isEmailExist(email))
		{
			Newsletter newsletter = new Newsletter();
			newsletter.setEmail(email);
			newsletterDao.save(newsletter);
		}
	}

	@Override
	public void unsubscribe(String email)
	{
		newsletterDao.deleteByEmail(email);
	}
}