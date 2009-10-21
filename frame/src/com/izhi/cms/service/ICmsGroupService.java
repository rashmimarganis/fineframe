package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsGroup;
import com.izhi.platform.util.PageParameter;

public interface ICmsGroupService  {
	List<Map<String,Object>> findPage(PageParameter pp);
	Integer findTotalCount();
	CmsGroup findObjById(int id);
	List<Map<String,Object>> findJsonById(int id);
	int saveGroup(CmsGroup r);
	
	boolean deleteGroup(Integer id);
	boolean deleteGroups(List<Integer> ids);
	CmsGroup findById(Integer id);
	
}