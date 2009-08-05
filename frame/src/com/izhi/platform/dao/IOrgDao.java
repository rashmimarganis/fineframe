package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Org;
import com.izhi.platform.util.PageParameter;

public interface IOrgDao extends IBaseDao<Org, Integer>{
	List<Org> findChildren(Integer parentId);
	List<Map<String,Object>> findChildNodes(Integer id);
	int saveShop(Org obj); 
	int updateShop(Org obj,String on);
	boolean findIsExist(String nameFiled,String name);
	
	List<Org> findPage(PageParameter pp);
	List<Org> findPage(PageParameter pp,int parentId);
	int findTotalCount(int parentId);
	
	List<Org> findTopPages(PageParameter pp);
	int findTopTotalCount();
	
	boolean findExist(String name,String oname);
	
	boolean deleteShops(List<Integer> ids);
} 
