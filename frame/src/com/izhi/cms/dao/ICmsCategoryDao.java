package com.izhi.cms.dao;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsCategory;
import com.izhi.platform.util.PageParameter;

public interface ICmsCategoryDao {
	int saveCategory(CmsCategory obj);

	int updateCategory(CmsCategory obj);

	boolean deleteCategory(int id);

	boolean deleteCategorys(List<Integer> ids);

	CmsCategory findCategoryById(int id);


	List<Map<String, Object>> findPage(PageParameter pp);

	int findTotalCount();

	List<Map<String, Object>> findJsonById(int id);
}
