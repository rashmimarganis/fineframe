package com.izhi.platform.service;

import java.util.Map;

import com.izhi.platform.model.Region;

public interface IRegionService extends IBaseService<Region, Integer> {
	String findChildNodes(Integer id);
	Map<String,Object> saveRegion(Region obj,String oldName); 
	Map<String,Object> saveRegion(Region obj); 
	boolean findIsExist(String nameFiled,String name);

}
