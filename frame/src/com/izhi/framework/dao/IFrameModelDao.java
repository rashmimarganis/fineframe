package com.izhi.framework.dao;

import java.util.List;

import com.izhi.framework.model.FrameModel;
import com.izhi.platform.util.PageParameter;

public interface IFrameModelDao {
	int saveModel(FrameModel obj);
	boolean updateModel(FrameModel obj);
	boolean deleteModel(int id);
	boolean deleteModels(List<Integer> ids) ;
	FrameModel findModelById(int id);
	FrameModel findModelByName(String name);
	List<FrameModel> findPage(PageParameter pp);
	int findTotalCount();
}
