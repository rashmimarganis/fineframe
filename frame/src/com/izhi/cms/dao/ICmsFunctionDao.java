package com.izhi.cms.dao;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsFunction;
import com.izhi.platform.util.PageParameter;

public interface ICmsFunctionDao {
	int saveFunction(CmsFunction obj);

	int updateFunction(CmsFunction obj);

	boolean deleteFunction(int id);

	boolean deleteFunctions(List<Integer> ids);

	CmsFunction findFunctionById(int id);


	List<Map<String, Object>> findPage(PageParameter pp);

	int findTotalCount();

	List<Map<String, Object>> findJsonById(int id);
}
