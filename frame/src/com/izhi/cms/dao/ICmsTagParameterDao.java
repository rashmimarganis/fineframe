package com.izhi.cms.dao;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsTagParameter;
import com.izhi.platform.util.PageParameter;

public interface ICmsTagParameterDao {
	int saveTagParameter(CmsTagParameter obj);

	int updateTagParameter(CmsTagParameter obj);

	boolean deleteTagParameter(int id);

	boolean deleteTagParameters(List<Integer> ids);

	CmsTagParameter findTagParameterById(int id);


	List<Map<String, Object>> findPage(PageParameter pp,int tagId);

	int findTotalCount(int tagId);

	List<Map<String, Object>> findJsonById(int id);
}
