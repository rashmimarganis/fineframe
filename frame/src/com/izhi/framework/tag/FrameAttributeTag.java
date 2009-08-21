package com.izhi.framework.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.izhi.framework.model.FrameAttribute;
import com.izhi.framework.model.FrameControl;
import com.izhi.framework.service.IFrameAttributeService;
import com.izhi.platform.util.SpringUtils;
import com.izhi.platform.util.WebUtils;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@SuppressWarnings("unchecked")
public class FrameAttributeTag implements TemplateDirectiveModel{
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] models,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		IFrameAttributeService frameAttributeService=(IFrameAttributeService)SpringUtils.getContext().getBean("frameAttributeService");
		String name=null;
		FrameAttribute attr=null;
		FrameControl ctl=null;
		
		if(params.containsKey("name")){
		     name=params.get("name").toString();
		     attr=frameAttributeService.findAttributeByName(name);
		}
		
		if(attr!=null){
			ctl=attr.getControl();
			String file=WebUtils.frameTemplateRoot()+ctl.getTemplate().getFileName()+".ftl";
			Template tpl= env.getConfiguration().getTemplate(file);
			Map<String,FrameAttribute> m=new HashMap<String, FrameAttribute>();
			m.put("o", attr);
			tpl.process(m, env.getOut());
		}
	
	}
}
