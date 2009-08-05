package com.izhi.cms.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.izhi.cms.model.TemplateTag;
import com.izhi.cms.service.CmsService;
import com.izhi.cms.service.ITemplateTagService;
import com.izhi.platform.util.SpringUtils;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class TagDirective implements TemplateDirectiveModel  {



	@Override
	public void execute(Environment arg0, Map arg1, TemplateModel[] arg2,
			TemplateDirectiveBody arg3) throws TemplateException, IOException {
		Environment e=Environment.getCurrentEnvironment();
		String name="";
		if(arg0!=null&&arg1.size()>0){
			name=arg1.get("name").toString();
		}else{
			e.getOut().write("请输入标签名称。");
			return;
		}
		try {
			//ITemplateTagService service=(ITemplateTagService)this.getBean("templateTagService");
			WebApplicationContext context=SpringUtils.getContext();
			ITemplateTagService service=(ITemplateTagService)context.getBean("templateTagService");
			CmsService dao=(CmsService)context.getBean("cmsService");
			
			TemplateTag obj=service.findTemplateTagByName(name);
			String moduleName=obj.getTemplate().getModule().getPackageName();
			
			Template myTemplate = e.getConfiguration().getTemplate(moduleName+"/"+obj.getTemplate().getFileName()+".ftl");
			
			myTemplate.process(dao.findData(obj), e.getOut());
			/*pageContext.getOut().print(obj.getContent());*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
