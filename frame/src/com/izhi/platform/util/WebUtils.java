package com.izhi.platform.util;

public final class WebUtils {

	private static String webRoot;
	
	private static String frameTemplateRoot="WEB-INF/frame/";

	public static String getWebRoot() {
		return webRoot;
	}

	public static void setWebRoot(String webRoot) {
		WebUtils.webRoot = webRoot;
	}

	public static String getFrameTemplateRoot() {
		return webRoot+frameTemplateRoot;
	}

	public static void setFrameTemplateRoot(String frameTemplateRoot) {
		WebUtils.frameTemplateRoot = frameTemplateRoot;
	}
	
	
}
