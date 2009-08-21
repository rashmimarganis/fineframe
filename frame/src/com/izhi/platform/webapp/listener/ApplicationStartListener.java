package com.izhi.platform.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.izhi.framework.tag.FrameAttributeTag;
import com.izhi.framework.tag.FrameModelTag;
import com.izhi.platform.util.SpringUtils;
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
		initTag(event.getServletContext());
	}
	 public static void setupContext(ServletContext context) {
		 
	 }
	 /*
	  * Freemarker标签初始化
	  */
	 public void initTag(ServletContext app){
		 app.setAttribute("attribute",new FrameAttributeTag());
		 app.setAttribute("model", new FrameModelTag());
	 }
	 
}
