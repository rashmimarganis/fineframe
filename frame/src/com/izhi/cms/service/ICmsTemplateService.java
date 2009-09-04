package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsTemplate;
import com.izhi.platform.util.PageParameter;

public interface ICmsTemplateService {
	int saveTemplate(CmsTemplate obj);

	int updateTemplate(CmsTemplate obj);

	boolean deleteTemplate(int id);

	boolean deleteTemplates(List<Integer> ids);

	CmsTemplate findTemplateById(int id);

	List<Map<String, Object>> findPage(PageParameter pp);

	int findTotalCount();

	List<Map<String, Object>> findJsonById(int id);
	
	
}
