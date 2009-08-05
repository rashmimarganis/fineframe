package com.izhi.platform.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieRememberFilter implements Filter {
	public static final String Cookie_Username=AuthenticationProcessingFilter.ACEGI_SECURITY_FORM_USERNAME_KEY;
	public static final String Cookie_Check="xenging_cg_check";
	private final Logger log=LoggerFactory.getLogger(CookieRememberFilter.class);
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		String remember=request.getParameter("rememberme");
		log.info("Remember:"+remember);
		if(remember!=null&&!remember.equals("")){
			if("on".equals(remember)){
				String userName=request.getParameter("j_username");
				String rememberMe=request.getParameter("rememberme");
				Cookie theUsername=new Cookie(Cookie_Username,userName);
				Cookie theRememberMe=new Cookie(Cookie_Check,rememberMe);
				theUsername.setMaxAge(365*24*60*60);
				theRememberMe.setMaxAge(365*24*60*60);
				theUsername.setPath("/");
				theRememberMe.setPath("/");
				response.addCookie(theUsername);
				response.addCookie(theRememberMe);
			}else{
				Cookie theUsername=new Cookie(Cookie_Username,null);
				Cookie theRememberMe=new Cookie(Cookie_Check,null);
				theUsername.setMaxAge(0);
				theRememberMe.setMaxAge(0);
				theUsername.setPath("/");
				theRememberMe.setPath("/");
				response.addCookie(theUsername);
				response.addCookie(theRememberMe);
			}
		}
		chain.doFilter(request, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		

	}

}
