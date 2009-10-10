package com.izhi.cms.dao;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsCategory;

public interface ICmsCategoryDao {
	int saveCategory(CmsCategory obj);

	int updateCategory(CmsCategory obj);

	boolean deleteCategory(int id);

	boolean deleteCategorys(List<Integer> ids);

	CmsCategory findCategoryById(int id);


	List<Map<String, Object>> findCategory(int cid);

	int findTotalCount();
	int findTotalCount(int cid);

	List<Map<String, Object>> findJsonById(int id);
	
	List<Map<String,Object>> findAll(int id);
}
