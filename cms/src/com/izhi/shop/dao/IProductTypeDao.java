package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.ProductType;

public interface IProductTypeDao  {
	int saveProductType(ProductType obj);
	boolean updateProductType(ProductType obj);
	boolean deleteProductType(int id);
	boolean deleteProductTypes(List<Integer> ids) ;
	ProductType findProductTypeById(int id);
	List<ProductType> findPage(PageParameter pp);
	List<ProductType> findPage(PageParameter pp,int parentId);
	int findTotalCount();
	int findTotalCount(int id);
	List<ProductType> findAll();
}
