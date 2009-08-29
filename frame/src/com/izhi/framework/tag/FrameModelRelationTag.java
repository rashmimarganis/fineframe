package com.izhi.framework.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.izhi.framework.model.FrameModel;
import com.izhi.framework.service.IFrameModelService;
import com.izhi.platform.util.SpringUtils;
import com.izhi.platform.util.WebUtils;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@SuppressWarnings("unchecked")
public class FrameModelRelationTag extends BaseFrameTag {

	@Override
	public String getName() {
		return "relation";
	}

	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] models,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		FrameModel model=null;
		int modelId=0;
		IFrameModelService  mrs=(IFrameModelService)SpringUtils.getBean("frameModelService");
		if(params.containsKey("modelId")){
			modelId=Integer.parseInt(params.get("modelId").toString());
			model=mrs.findModelById(modelId);
		}
		if(model!=null){
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("p", model.getProject());
			data.put("m", model);
			data.put("relations", model.getRelations());
			String file=WebUtils.frameTemplateRoot()+"relations.ftl";
			Template tpl=env.getConfiguration().getTemplate(file);
			tpl.process(data, env.getOut());
		}
	}

}
