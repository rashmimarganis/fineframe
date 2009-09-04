package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsTag;
import com.izhi.platform.util.PageParameter;

public interface ICmsTagService {
	int saveTag(CmsTag obj);

	int updateTag(CmsTag obj);

	boolean deleteTag(int id);

	boolean deleteTags(List<Integer> ids);

	CmsTag findTagById(int id);


	List<Map<String, Object>> findPage(PageParameter pp);

	int findTotalCount();

	List<Map<String, Object>> findJsonById(int id);
}
