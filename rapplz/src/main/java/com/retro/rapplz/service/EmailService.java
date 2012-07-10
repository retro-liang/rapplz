package com.retro.rapplz.service;

public interface EmailService
{
	public void sendEmail(String fromEmail, String fromName, String toEmail, String toName, String subject, String content);
}