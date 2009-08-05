package com.izhi.cms.dao;

import java.util.List;

import com.izhi.cms.model.TemplateTag;
import com.izhi.platform.util.PageParameter;

public interface ITemplateTagDao {
	int saveTemplateTag(TemplateTag obj);
	boolean updateTemplateTag(TemplateTag obj);
	boolean deleteTemplateTag(int id);
	boolean deleteTemplateTags(List<Integer> ids) ;
	TemplateTag findTemplateTagById(int id);
	TemplateTag findTemplateTagByName(String name);
	List<TemplateTag> findPage(PageParameter pp);
	int findTotalCount();
}
