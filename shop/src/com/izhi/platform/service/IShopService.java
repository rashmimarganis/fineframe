package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Shop;

public interface IShopService extends IBaseService<Shop, Integer> {
	List<Shop> findChildren(Integer id);
	String findChildNodes(Integer id);
	Map<String,Object> saveShop(Shop obj,String oldName); 
	Map<String,Object> saveShop(Shop obj); 
	boolean findIsExist(String nameFiled,String name);
}
