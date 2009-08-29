package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Org;
import com.izhi.platform.util.PageParameter;

public interface IOrgDao extends IBaseDao<Org, Integer>{
	List<Org> findChildren(Integer parentId);
	List<Map<String,Object>> findChildNodes(Integer id);
	int saveOrg(Org obj); 
	int updateOrg(Org obj,String on);
	boolean findIsExist(String nameFiled,String name);
	
	List<Org> findPage(PageParameter pp);
	List<Org> findPage(PageParameter pp,int parentId);
	int findTotalCount(int parentId);
	
	List<Org> findTopPages(PageParameter pp);
	int findTopTotalCount();
	
	boolean findExist(String name,String oname);
	
	boolean deleteOrgs(List<Integer> ids);
	
	List<Map<String,Object>> findOrgs(Integer pid);
	
	List<Map<String,Object>> findJsonById(int id);
} 
