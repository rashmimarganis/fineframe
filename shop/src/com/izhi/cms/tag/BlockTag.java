package com.izhi.cms.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.izhi.cms.model.Block;
import com.izhi.cms.service.IBlockService;

import freemarker.core.Environment;
import freemarker.template.Template;

public class BlockTag extends BaseCmsTag {

	private static final long serialVersionUID = 3493525035004635582L;
	private String name;
	public int doStartTag() throws JspException {
		try {
			IBlockService service=(IBlockService)this.getBean("blockService");
			Block obj=service.findBlockByName(name);
			Environment e=Environment.getCurrentEnvironment();
			Template myTemplate = e.getConfiguration().getTemplate("cms/block.ftl");
			Map<String, Object> m=new HashMap<String, Object>();
			m.put("block", obj);
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

}
