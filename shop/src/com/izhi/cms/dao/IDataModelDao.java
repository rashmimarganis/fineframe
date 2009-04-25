package com.izhi.cms.dao;

import java.util.List;

import com.izhi.cms.model.DataModel;
import com.izhi.platform.util.PageParameter;

public interface IDataModelDao {
	int saveDataModel(DataModel obj);
	boolean updateDataModel(DataModel obj);
	boolean deleteDataModel(int id);
	boolean deleteDataModels(List<Integer> ids) ;
	DataModel findDataModelById(int id);
	DataModel findDataModelByName(String name);
	List<DataModel> findPage(PageParameter pp);
	int findTotalCount();
}
