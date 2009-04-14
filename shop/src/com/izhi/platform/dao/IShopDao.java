package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Shop;
import com.izhi.platform.util.PageParameter;

public interface IShopDao extends IBaseDao<Shop, Integer>{
	List<Shop> findChildren(Integer parentId);
	List<Map<String,Object>> findChildNodes(Integer id);
	int saveShop(Shop obj); 
	int updateShop(Shop obj,String on);
	boolean findIsExist(String nameFiled,String name);
	
	List<Shop> findPage(PageParameter pp);
	List<Shop> findPage(PageParameter pp,int parentId);
	int findTotalCount(int parentId);
	
	List<Shop> findTopPages(PageParameter pp);
	int findTopTotalCount();
	
	boolean findExist(String name,String oname);
	
	boolean deleteShops(List<Integer> ids);
} 
