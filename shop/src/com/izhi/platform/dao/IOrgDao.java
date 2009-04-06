package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Org;

public interface IOrgDao extends IBaseDao<Org, Integer>{
	List<Org> findChildren(Integer parentId);
	List<Map<String,Object>> findChildNodes(Integer id);
	Map<String,Object> saveOrg(Org obj,String oldName); 
	Map<String,Object> saveOrg(Org obj); 
	boolean findIsExist(String nameFiled,String name);
}
