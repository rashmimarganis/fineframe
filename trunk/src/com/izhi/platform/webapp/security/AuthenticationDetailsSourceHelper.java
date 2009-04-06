package com.izhi.platform.webapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.acegisecurity.ui.AuthenticationDetailsSourceImpl;

public class AuthenticationDetailsSourceHelper extends
		AuthenticationDetailsSourceImpl {
	public Object buildDetails(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null){
			session = request.getSession(true);
		}
		return super.buildDetails(request);
	}
}
