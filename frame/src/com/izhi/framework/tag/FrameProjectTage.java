package com.izhi.framework.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.izhi.framework.model.FrameProject;
import com.izhi.framework.service.IFrameProjectService;
import com.izhi.platform.util.SpringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Service
@SuppressWarnings("unchecked")
public class FrameProjectTage extends BaseFrameTag {

	@Override
	public String getName() {
		return "project";
	}

	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String name=null;
		FrameProject fp=null;
		IFrameProjectService fps=(IFrameProjectService)SpringUtils.getBean("frameProjectService");
		
		if(params.containsKey("name")){
			name=params.get("name").toString();
			fp=fps.findProjectByName(name);
			if(fp!=null){
				Map<String,Object> m=getModel();
				m.put("project", fp);
			}
			
		}
		
	}

}
