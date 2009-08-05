package com.izhi.cms.service;

import java.util.List;

import com.izhi.cms.model.Category;
import com.izhi.platform.util.PageParameter;

public interface ICategoryService {
	int saveCategory(Category obj);
	boolean updateCategory(Category obj);
	boolean deleteCategory(int id);
	boolean deleteCategorys(List<Integer> ids) ;
	Category findCategoryById(int id);
	Category findCategoryByName(String name);
	List<Category> findPage(PageParameter pp);
	int findTotalCount();
}