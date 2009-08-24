package com.izhi.framework.service;

import java.util.List;
import java.util.Map;

import com.izhi.framework.model.FrameModel;
import com.izhi.platform.util.PageParameter;

public interface IFrameModelService {
	int saveModel(FrameModel obj);
	boolean updateModel(FrameModel obj);
	boolean deleteModel(int id);
	boolean deleteModels(List<Integer> ids) ;
	FrameModel findModelById(int id);
	FrameModel findModelByName(String name);
	List<Map<String,Object>> findPage(PageParameter pp);
	int findTotalCount();
	List<Map<String,Object>> findJsonById(int id);
	
	List<FrameModel> findModelByProject(int pid);
}
