package com.izhi.cms.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseCmsTag extends TagSupport {

	private static final long serialVersionUID = 8966647559932652924L;
	public static WebApplicationContext  wac;
	
	public WebApplicationContext getApplicationContext(){
		if(wac==null){
			wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.pageContext.getServletContext())
;		}
		return wac;
	}
	
	public Object getBean(String name){
		return  getApplicationContext().getBean(name);
	}
	
	
}
