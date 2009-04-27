package com.izhi.cms.tag;

import java.util.List;

import javax.servlet.jsp.JspException;

import com.izhi.cms.model.ModelField;
import com.izhi.cms.model.TemplateTag;
import com.izhi.cms.service.CmsService;
import com.izhi.cms.service.ITemplateTagService;

import freemarker.core.Environment;
import freemarker.template.Template;

public class CmsTag extends BaseCmsTag {

	private static final long serialVersionUID = -4425084173354471043L;
	private String name;
	public int doStartTag() throws JspException {
		try {
			ITemplateTagService service=(ITemplateTagService)this.getBean("templateTagService");
			CmsService dao=(CmsService)this.getBean("cmsService");
			
			TemplateTag obj=service.findTemplateTagByName(name);
			String moduleName=obj.getTemplate().getModule().getPackageName();
			
			Environment e=Environment.getCurrentEnvironment();
			
			Template myTemplate = e.getConfiguration().getTemplate(moduleName+"/"+obj.getTemplate().getFileName()+".ftl");
			
			myTemplate.process(dao.findData(obj), pageContext.getOut());
			/*pageContext.getOut().print(obj.getContent());*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String fieldsToString(List<ModelField> list){
		String s="";
		for(ModelField mf:list){
			if("".equals(s)){
				s=mf.getName();
			}else{
				s+=","+mf.getName();
			}
		}
		return s;
	}

}
