package com.izhi.platform.webapp.listener;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.izhi.platform.util.SpringUtils;
import com.izhi.platform.util.TagUtils;
import com.izhi.platform.util.WebUtils;

public class ApplicationStartListener extends ContextLoaderListener implements
		ServletContextListener {

	private static Logger log=LoggerFactory.getLogger(ApplicationStartListener.class);
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		WebApplicationContext wac=WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SpringUtils.setContext(wac);
		String webRoot=event.getServletContext().getRealPath("/");
		WebUtils.setWebRoot(webRoot);
		/*
		 * 初始化Freemarker标签
		 */
		log.debug("初始化Freemarker标签");
		initTag(event.getServletContext());
	}
	 public static void setupContext(ServletContext context) {
		 
	 }
	 /*
	  * Freemarker标签初始化
	  */
	 public void initTag(ServletContext app){
		 Collection<String> vs=TagUtils.getTags().keySet();
		 for(String v:vs){
			 app.setAttribute(v, TagUtils.getTags().get(v));
		 }
	 }
	 
}
