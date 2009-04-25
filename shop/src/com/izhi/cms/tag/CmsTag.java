package com.izhi.cms.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.izhi.cms.model.ModelField;
import com.izhi.cms.model.TemplateTag;
import com.izhi.cms.service.ITemplateTagService;

import freemarker.core.Environment;
import freemarker.template.Template;

public class CmsTag extends BaseCmsTag {

	private static final long serialVersionUID = -4425084173354471043L;
	private String name;
	public int doStartTag() throws JspException {
		try {
			ITemplateTagService service=(ITemplateTagService)this.getBean("templateTagService");
			SessionFactory sf=(SessionFactory)this.getBean("sessionFactory");
			TemplateTag obj=service.findTemplateTagByName(name);
			Session session=sf.getCurrentSession();
			obj.getItemCount();
			String sql="from "+obj.getModel().getClassName();
			Query q=session.createQuery(sql);
			q.setMaxResults(obj.getItemCount());
			Map<String, Object> m=new HashMap<String, Object>();
			m.put("data", q.list());
			
			Environment e=Environment.getCurrentEnvironment();
			
			Template myTemplate = e.getConfiguration().getTemplate("cms/"+obj.getTemplate().getFileName()+".ftl");
			
			myTemplate.process(m, pageContext.getOut());
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
