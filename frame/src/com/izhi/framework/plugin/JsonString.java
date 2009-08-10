package com.izhi.framework.plugin;

import java.util.List;

import net.sf.json.JSONArray;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
@SuppressWarnings("unchecked")
public class JsonString implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		List<?> l = (List<?>) args.get(0);
		return JSONArray.fromObject(l).toString();
	}

}
