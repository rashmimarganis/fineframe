package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsAttribute;
import com.izhi.platform.util.PageParameter;

public interface ICmsAttributeService {
	int saveAttribute(CmsAttribute obj);

	int updateAttribute(CmsAttribute obj);

	boolean deleteAttribute(int id);

	boolean deleteAttributes(List<Integer> ids);

	CmsAttribute findAttributeById(int id);


	List<Map<String, Object>> findPage(PageParameter pp,int modelId);

	int findTotalCount(int modelId);

	List<Map<String, Object>> findJsonById(int id);
}
