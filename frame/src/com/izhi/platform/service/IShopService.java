package com.izhi.platform.service;

import java.util.List;

import com.izhi.platform.model.Shop;
import com.izhi.platform.util.PageParameter;

public interface IShopService extends IBaseService<Shop, Integer> {
	List<Shop> findChildren(Integer id);
	String findChildNodes(Integer id);
	boolean saveShop(Shop obj,String oldName); 
	boolean saveShop(Shop obj); 
	boolean findIsExist(String nameFiled,String name);
	int findTotalCount();
	int findTopTotalCount();
	List<Shop> findPage(PageParameter pp);
	List<Shop> findTopPage(PageParameter pp);
	List<Shop> findPage(PageParameter pp,int parentId);
	int findTotalCount(int parentId);
	boolean findExist(String name,String oname);
	boolean deleteShops(List<Integer> ids);
	
	
}
