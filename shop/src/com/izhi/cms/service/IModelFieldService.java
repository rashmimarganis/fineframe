package com.izhi.cms.service;

import java.util.List;

import com.izhi.cms.model.ModelField;
import com.izhi.platform.util.PageParameter;

public interface IModelFieldService {
	int saveModelField(ModelField obj);
	boolean updateModelField(ModelField obj);
	boolean deleteModelField(int id);
	boolean deleteModelFields(List<Integer> ids) ;
	ModelField findModelFieldById(int id);
	ModelField findModelFieldByName(String name);
	List<ModelField> findPage(PageParameter pp);
	int findTotalCount();
}
