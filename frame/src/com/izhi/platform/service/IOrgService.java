package com.izhi.platform.service;

import java.util.List;

import com.izhi.platform.model.Org;
import com.izhi.platform.util.PageParameter;

public interface IOrgService extends IBaseService<Org, Integer> {
	List<Org> findChildren(Integer id);
	String findChildNodes(Integer id);
	boolean saveOrg(Org obj,String oldName); 
	boolean saveOrg(Org obj); 
	boolean findIsExist(String nameFiled,String name);
	int findTotalCount();
	int findTopTotalCount();
	List<Org> findPage(PageParameter pp);
	List<Org> findTopPage(PageParameter pp);
	List<Org> findPage(PageParameter pp,int parentId);
	int findTotalCount(int parentId);
	boolean findExist(String name,String oname);
	boolean deleteOrgs(List<Integer> ids);
	
	
}
