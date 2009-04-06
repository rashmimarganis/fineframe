package com.izhi.platform.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.izhi.platform.security.support.Constants;
import com.izhi.platform.service.IFunctionService;

public class ApplicationStartListener extends ContextLoaderListener implements
		ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		WebApplicationContext wac=WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		IFunctionService resourceService=(IFunctionService)wac.getBean("functionService");
		resourceService.findAllUrl();
		event.getServletContext().setAttribute(Constants.APP_MANAGER_THEME,"default");
		
	}
	 public static void setupContext(ServletContext context) {
		 
	 }
}
