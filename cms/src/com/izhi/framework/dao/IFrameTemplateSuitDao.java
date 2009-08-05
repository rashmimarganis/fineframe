package com.izhi.framework.dao;

import java.util.List;

import com.izhi.framework.model.FrameTemplateSuit;
import com.izhi.platform.util.PageParameter;

public interface IFrameTemplateSuitDao {
	int saveTemplateSuit(FrameTemplateSuit obj);
	boolean updateTemplateSuit(FrameTemplateSuit obj);
	boolean deleteTemplateSuit(int id);
	boolean deleteTemplateSuits(List<Integer> ids) ;
	FrameTemplateSuit findTemplateSuitById(int id);
	FrameTemplateSuit findDefaultTemplateSuit();
	FrameTemplateSuit findTemplateSuitByName(String name);
	List<FrameTemplateSuit> findPage(PageParameter pp);
	int findTotalCount();
}
