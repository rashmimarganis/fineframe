package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsTemplateSuit;
import com.izhi.platform.util.PageParameter;

public interface ICmsTemplateSuitService {
	int saveSuit(CmsTemplateSuit obj);

	int updateSuit(CmsTemplateSuit obj);

	boolean deleteSuit(int id);

	boolean deleteSuits(List<Integer> ids);

	CmsTemplateSuit findSuitById(int id);


	List<Map<String, Object>> findPage(PageParameter pp);

	int findTotalCount();

	List<Map<String, Object>> findJsonById(int id);
	List<Map<String, Object>> findAll() ;
	
	boolean findPackageExist(String name);
	boolean findPackageExist(String name,String oldName);
}
