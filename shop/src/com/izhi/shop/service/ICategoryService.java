package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Category;

public interface ICategoryService {
	int saveCategory(Category obj);
	boolean updateCategory(Category obj);
	boolean deleteCategory(int id);
	boolean deleteCategories(List<Integer> ids) ;
	Category findCategoryById(int id);
	List<Category> findPage(PageParameter pp);
	List<Category> findPage(PageParameter pp,int parentId);
	int findTotalCount();
	int findTotalCount(int id);
	
	List<Category> findTopAll();
}
