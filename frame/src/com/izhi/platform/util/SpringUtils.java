package com.izhi.platform.util;

import org.springframework.context.ApplicationContext;

public class SpringUtils {
	private static ApplicationContext context;
	
	public static ApplicationContext  getContext(){
		return context;
	}
	
	public static void setContext(ApplicationContext context){
		SpringUtils.context=context;
	}
	
	public static Object getBean(String name){
		return getContext().getBean(name);
	}
}
