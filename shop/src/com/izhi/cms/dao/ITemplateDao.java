package com.izhi.cms.dao;

import java.util.List;

import com.izhi.cms.model.Template;
import com.izhi.platform.util.PageParameter;

public interface ITemplateDao {
	int saveTemplate(Template obj);
	boolean updateTemplate(Template obj);
	boolean deleteTemplate(int id);
	boolean deleteTemplates(List<Integer> ids) ;
	Template findTemplateById(int id);
	Template findTemplateByName(String name);
	List<Template> findPage(PageParameter pp);
	int findTotalCount();
}
