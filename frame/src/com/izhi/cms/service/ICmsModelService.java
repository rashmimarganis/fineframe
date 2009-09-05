package com.izhi.cms.service;

import java.util.List;
import java.util.Map;

import com.izhi.cms.model.CmsModel;
import com.izhi.platform.util.PageParameter;

public interface ICmsModelService {
	int saveModel(CmsModel obj);

	int updateModel(CmsModel obj);

	boolean deleteModel(int id);

	boolean deleteModels(List<Integer> ids);

	CmsModel findModelById(int id);


	List<Map<String, Object>> findPage(PageParameter pp);

	int findTotalCount();

	List<Map<String, Object>> findJsonById(int id);
	
	List<Map<String,Object>> findTree();
}
