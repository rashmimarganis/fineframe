package com.izhi.framework.tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;


import freemarker.template.TemplateDirectiveModel;

public abstract class BaseFrameTag implements TemplateDirectiveModel,InitializingBean{
	protected Logger log=LoggerFactory.getLogger(this.getClass());
	private static Map<String,Object> model=new HashMap<String, Object>();

	public abstract String getName();

	
	@Override
	public void afterPropertiesSet() throws Exception {
		String name=getName();
		if(TagUtils.getTags().containsKey(name)){
			log.error("["+name+"]相同的名字的标签已经存在，请重新选择一个名字！");
		}else{
			TagUtils.addTag(name, this);
			
		
		}
	}


	public static Map<String, Object> getModel() {
		Collection<String> ps=TagUtils.getTags().keySet();
		Map<String,TemplateDirectiveModel> tags=TagUtils.getTags();
		for(String s:ps){
			model.put(s, tags.get(s));
		}
		return model;
	}
}
