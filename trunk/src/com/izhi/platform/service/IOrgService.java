package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Org;

public interface IOrgService extends IBaseService<Org, Integer> {
	List<Org> findChildren(Integer id);
	String findChildNodes(Integer id);
	Map<String,Object> saveOrg(Org obj,String oldName); 
	Map<String,Object> saveOrg(Org obj); 
	boolean findIsExist(String nameFiled,String name);
}
