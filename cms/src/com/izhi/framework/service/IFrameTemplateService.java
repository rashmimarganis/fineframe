package com.izhi.framework.service;

import java.util.List;

import com.izhi.framework.model.FrameTemplateFile;
import com.izhi.platform.util.PageParameter;

public interface IFrameTemplateService {
	int saveTemplate(FrameTemplateFile obj);
	boolean updateTemplate(FrameTemplateFile obj);
	boolean deleteTemplate(int id);
	boolean deleteTemplates(List<Integer> ids) ;
	FrameTemplateFile findTemplateById(int id);
	FrameTemplateFile findTemplateByName(String name);
	List<FrameTemplateFile> findPage(PageParameter pp);
	int findTotalCount();
}
