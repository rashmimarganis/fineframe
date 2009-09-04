package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsFunction;
import com.izhi.platform.util.PageParameter;

public interface ICmsFunctionService {
	int saveFunction(CmsFunction obj);

	int updateFunction(CmsFunction obj);

	boolean deleteFunction(int id);

	boolean deleteFunctions(List<Integer> ids);

	CmsFunction findFunctionById(int id);


	List<Map<String, Object>> findPage(PageParameter pp,int modelId);

	int findTotalCount(int modelId);

	List<Map<String, Object>> findJsonById(int id);
}
