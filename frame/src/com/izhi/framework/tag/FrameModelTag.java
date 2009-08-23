package com.izhi.framework.tag;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.izhi.framework.model.FrameModel;
import com.izhi.framework.service.IFrameModelService;
import com.izhi.platform.util.SpringUtils;
import com.izhi.platform.util.WebUtils;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Service
@SuppressWarnings("unchecked")
public class FrameModelTag extends BaseFrameTag {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] models,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		IFrameModelService frameModelService = (IFrameModelService) SpringUtils.getContext().getBean("frameModelService");
		String name = null;
		FrameModel model = null;
		
		if (params.containsKey("name")) {
			name = params.get("name").toString();
			model = frameModelService.findModelByName(name);
		}

		if (model != null) {

			String file = "WEB-INF/frame/model.ftl";
			Template tpl = env.getConfiguration().getTemplate(file);
			
			String templateStr="${model1.project.name?cap_first}${model1.name?cap_first}";
			Template fileNameTpl = new Template("fileName", new StringReader(templateStr),
			               env.getConfiguration());
			StringWriter sw=new StringWriter();
			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("model1", model);
			fileNameTpl.process(map, sw);  
			String fileName=sw.toString();
			Map<String, Object> m = BaseFrameTag.getModel();
			m.put("model1", model);
			File afile = new File(WebUtils.getWebRoot() + "/" + fileName+".js");
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile),"UTF-8"));
			
			tpl.process(m, out);
			out.flush();
			
		}

	}

	@Override
	public String getName() {
		return "model";
	}

	
}
