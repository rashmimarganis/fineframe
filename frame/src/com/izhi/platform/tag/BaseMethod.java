package com.izhi.platform.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.izhi.framework.tag.TagUtils;

import freemarker.template.TemplateMethodModelEx;

public abstract class BaseMethod implements TemplateMethodModelEx,InitializingBean{
	protected Logger log=LoggerFactory.getLogger(this.getClass());
	@Override
	public void afterPropertiesSet() throws Exception {
		String name=getName();
		if(TagUtils.getMethods().containsKey(name)){
			log.error("["+name+"]相同的名字的标签已经存在，请重新选择一个名字！");
		}else{
			TagUtils.addMethod(name, this);
		}
	}
	public abstract String getName();
}
