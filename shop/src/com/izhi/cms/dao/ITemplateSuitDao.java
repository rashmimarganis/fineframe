package com.izhi.cms.dao;

import java.util.List;

import com.izhi.cms.model.TemplateSuit;
import com.izhi.platform.util.PageParameter;

public interface ITemplateSuitDao {
	int saveTemplateSuit(TemplateSuit obj);
	boolean updateTemplateSuit(TemplateSuit obj);
	boolean deleteTemplateSuit(int id);
	boolean deleteTemplateSuits(List<Integer> ids) ;
	TemplateSuit findTemplateSuitById(int id);
	TemplateSuit findDefaultTemplateSuit();
	TemplateSuit findTemplateSuitByName(String name);
	List<TemplateSuit> findPage(PageParameter pp);
	int findTotalCount();
}
