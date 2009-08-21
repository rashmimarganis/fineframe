package com.izhi.platform.util;

import java.io.File;

public final class WebUtils {

	private static String webRoot;
	
	
	private static String frameTemplateRoot="WEB-INF"+File.separator+"frame"+File.separator;

	public static String getWebRoot() {
		return webRoot;
	}

	public static void setWebRoot(String webRoot) {
		
		WebUtils.webRoot = webRoot;
	}

	public static String frameTemplateRoot(){
		return frameTemplateRoot;
	}
	public static String getFrameTemplateRoot() {
		return webRoot+frameTemplateRoot;
	}

	public static void setFrameTemplateRoot(String frameTemplateRoot) {
		WebUtils.frameTemplateRoot = frameTemplateRoot;
	}
	
	public static void main(String[] args){
		System.out.println(WebUtils.frameTemplateRoot());
	}
	
}
