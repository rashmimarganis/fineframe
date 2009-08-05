package com.izhi.platform.util;

import org.springframework.web.context.WebApplicationContext;

public class SpringUtils {
	private static WebApplicationContext context;
	
	public static WebApplicationContext  getContext(){
		return context;
	}
	
	public static void setContext(WebApplicationContext context){
		SpringUtils.context=context;
	}
}
