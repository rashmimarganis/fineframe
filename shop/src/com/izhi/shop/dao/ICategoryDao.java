package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.ProductCategory;

public interface ICategoryDao  {
	int saveCategory(ProductCategory obj);
	boolean updateCategory(ProductCategory obj);
	boolean deleteCategory(int id);
	boolean deleteCategorys(List<Integer> ids) ;
	ProductCategory findCategoryById(int id);
	List<ProductCategory> findPage(PageParameter pp);
	List<ProductCategory> findPage(PageParameter pp,int parentId);
	int findTotalCount();
	int findTotalCount(int id);
	List<ProductCategory> findTopAll();
}
