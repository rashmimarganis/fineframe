package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Product;

public interface IProductDao  {
	int saveProduct(Product obj);
	boolean updateProduct(Product obj);
	boolean deleteProduct(int id);
	boolean deleteProducts(List<Integer> ids) ;
	Product findProductById(int id);
	List<Product> findPage(PageParameter pp);
	List<Product> findPageByType(PageParameter pp,int typeId);
	List<Product> findPageByCategory(PageParameter pp,int catId);
	int findTotalCount();
	int findTotalCountByType(int id);
	int findTotalCountByCategory(int id);
	boolean onlineProducts(List<Integer> ids,boolean online);
}
