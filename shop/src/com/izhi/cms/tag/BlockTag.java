package com.izhi.cms.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.izhi.cms.model.Block;
import com.izhi.cms.service.IBlockService;
import com.izhi.platform.util.SpringUtils;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class BlockTag implements TemplateDirectiveModel {

	private static final long serialVersionUID = 3493525035004635582L;
	private String name;

	

	@Override
	public void execute(Environment arg0, Map arg1, TemplateModel[] arg2,
			TemplateDirectiveBody arg3) throws TemplateException, IOException {
		WebApplicationContext context=SpringUtils.getContext();
		name=arg1.get("name").toString();
		try {
			IBlockService service=(IBlockService)context.getBean("blockService");
			Block obj=service.findBlockByName(name);
			Environment e=Environment.getCurrentEnvironment();
			Template myTemplate = e.getConfiguration().getTemplate("cms/block.ftl");
			Map<String, Object> m=new HashMap<String, Object>();
			m.put("block", obj);
			
			myTemplate.process(m, e.getOut());
			/*pageContext.getOut().print(obj.getContent());*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
