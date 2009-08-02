package com.izhi.platform.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.izhi.cms.tag.BlockTag;
import com.izhi.cms.tag.TagDirective;
import com.izhi.platform.util.SpringUtils;

public class ApplicationStartListener extends ContextLoaderListener implements
		ServletContextListener {
	private static TagDirective tag=null;
	private static BlockTag block=null;
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		WebApplicationContext wac=WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		//IFunctionService resourceService=(IFunctionService)wac.getBean("functionService");
		//resourceService.findAllUrl();
		//event.getServletContext().setAttribute(Constants.APP_MANAGER_THEME,"default");
		SpringUtils.setContext(wac);
		tag=new TagDirective();
		block=new BlockTag();
		event.getServletContext().setAttribute("block", block);
		
		event.getServletContext().setAttribute("tag", tag);
		
		
	}
	 public static void setupContext(ServletContext context) {
		 
	 }
}
