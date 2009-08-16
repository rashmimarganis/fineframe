package com.izhi.framework.dao;

import java.util.List;

import com.izhi.framework.model.FrameTemplate;
import com.izhi.platform.util.PageParameter;

public interface IFrameTemplateDao {
	int saveTemplate(FrameTemplate obj);
	boolean updateTemplate(FrameTemplate obj);
	boolean deleteTemplate(int id);
	boolean deleteTemplates(List<Integer> ids) ;
	FrameTemplate findTemplateById(int id);
	FrameTemplate findTemplateByName(String name);
	List<FrameTemplate> findPage(PageParameter pp);
	int findTotalCount();
}
