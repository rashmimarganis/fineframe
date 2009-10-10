package com.izhi.cms.dao;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsSite;
import com.izhi.platform.util.PageParameter;

public interface ICmsSiteDao {
	int saveSite(CmsSite obj);

	int updateSite(CmsSite obj);

	boolean deleteSite(int id);

	boolean deleteSites(List<Integer> ids);

	CmsSite findSiteById(int id);


	List<Map<String, Object>> findPage(PageParameter pp);

	int findTotalCount();

	List<Map<String, Object>> findJsonById(int id);
	
	List<Map<String,Object>> findAll();
}
