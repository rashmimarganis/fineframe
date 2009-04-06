package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Shop;

public interface IShopDao extends IBaseDao<Shop, Integer>{
	List<Shop> findChildren(Integer parentId);
	List<Map<String,Object>> findChildNodes(Integer id);
	Map<String,Object> saveShop(Shop obj,String oldName); 
	Map<String,Object> saveShop(Shop obj); 
	boolean findIsExist(String nameFiled,String name);
}
