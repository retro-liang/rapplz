package com.retro.rapplz.service;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService
{
	private static final Logger logger = Logger.getLogger(EmailServiceImpl.class.getName());

	@Override
	public void sendEmail(String fromEmail, String fromName, String toEmail, String toName, String subject, String content)
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try
		{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail, fromName));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toName));
			msg.setSubject(subject);
			msg.setText(content);
			Transport.send(msg);
		}
		catch(Exception e)
		{
			logger.severe("Sending email from [" + fromEmail + "] to [" + toEmail + "] exception: " + e);
		}
	}
}