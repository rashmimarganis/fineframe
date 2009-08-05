package com.izhi.cms.service;

import java.util.List;

import com.izhi.cms.model.TemplateSuit;
import com.izhi.platform.util.PageParameter;

public interface ITemplateSuitService {
	int saveTemplateSuit(TemplateSuit obj);
	boolean updateTemplateSuit(TemplateSuit obj);
	boolean deleteTemplateSuit(int id);
	boolean deleteTemplateSuits(List<Integer> ids) ;
	TemplateSuit findTemplateSuitById(int id);
	TemplateSuit findTemplateSuitByName(String name);
	List<TemplateSuit> findPage(PageParameter pp);
	int findTotalCount();
}
