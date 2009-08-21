package com.izhi.framework.tag;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.TemplateDirectiveModel;

public final class TagUtils {
	private static Map<String,TemplateDirectiveModel> tags=new HashMap<String, TemplateDirectiveModel>();

	public static void addTag(String name,TemplateDirectiveModel tag) {
		tags.put(name, tag);
	}

	public static Map<String,TemplateDirectiveModel> getTags() {
		return tags;
	}
}
