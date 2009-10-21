package com.izhi.cms.dao;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsGroup;
import com.izhi.platform.util.PageParameter;

public interface ICmsGroupDao{
	List<Map<String,Object>>  findPage(PageParameter pp);
	int findTotalCount();
	CmsGroup findObjById(int id);
	List<Map<String,Object>> findJsonById(int id);
	int updateGroup(CmsGroup r);
	boolean deleteGroups(List<Integer> ids);
	boolean deleteGroup(int id);
	int saveGroup(CmsGroup r);
}
