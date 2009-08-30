package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Org;
import com.izhi.platform.util.PageParameter;

public interface IOrgService extends IBaseService<Org, Integer> {
	List<Org> findChildren(Integer id);
	Map<String,Object> saveOrg(Org obj); 
	int findTotalCount();
	int findTopTotalCount();
	List<Org> findPage(PageParameter pp);
	List<Org> findTopPage(PageParameter pp);
	List<Org> findPage(PageParameter pp,int parentId);
	int findTotalCount(int parentId);
	boolean findExist(String name,String oname);
	boolean deleteOrgs(List<Integer> ids);
	
	public List<Map<String, Object>> findOrgs(Integer pid);
	
	List<Map<String,Object>> findJsonById(int id);
}
