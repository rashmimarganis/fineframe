package com.izhi.platform.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.izhi.framework.plugin.JsonString;
import com.izhi.platform.util.SpringUtils;

public class ApplicationStartListener extends ContextLoaderListener implements
		ServletContextListener {

	private static JsonString json;
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		WebApplicationContext wac=WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SpringUtils.setContext(wac);
		initTag(event.getServletContext());
	}
	 public static void setupContext(ServletContext context) {
		 
	 }
	 /*
	  * Freemarker标签初始化
	  */
	 public void initTag(ServletContext app){
		 json=new JsonString();
		 app.setAttribute("json", json);
	 }
	 
}
