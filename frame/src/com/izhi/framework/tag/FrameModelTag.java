package com.izhi.framework.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.izhi.framework.model.FrameModel;
import com.izhi.framework.service.IFrameModelService;
import com.izhi.platform.util.SpringUtils;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FrameModelTag implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] models,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		IFrameModelService frameAttributeService = (IFrameModelService) SpringUtils
				.getContext().getBean("frameModelService");
		String name = null;
		FrameModel model = null;

		if (params.containsKey("name")) {
			name = params.get("name").toString();
			model = frameAttributeService.findModelByName(name);
		}

		if (model != null) {

			String file = "WEB-INF/frame/model.ftl";
			Template tpl = env.getConfiguration().getTemplate(file);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("model", model);
			m.put("attribute",new FrameAttributeTag());
			tpl.process(m, env.getOut());
		}

	}

}
