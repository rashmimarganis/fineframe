package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsModelPage;
import com.izhi.platform.util.PageParameter;

public interface ICmsModelPageService {
	int saveModelPage(CmsModelPage obj);

	int updateModelPage(CmsModelPage obj);

	boolean deleteModelPage(int id);

	boolean deleteModelPages(List<Integer> ids);

	CmsModelPage findModelPageById(int id);


	List<Map<String, Object>> findPage(PageParameter pp,int modelId);

	int findTotalCount(int modelId);

	List<Map<String, Object>> findJsonById(int id);
}
