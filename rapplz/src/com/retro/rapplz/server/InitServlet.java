package com.retro.rapplz.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.retro.rapplz.server.config.RapplzConfig;

public class InitServlet extends HttpServlet
{
	private static final long serialVersionUID = -860192434293020346L;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		RapplzConfig.getInstance().setServletContext(config.getServletContext());
	}
}