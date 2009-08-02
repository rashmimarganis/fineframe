package com.izhi.framework.service;

import java.util.List;

import com.izhi.framework.model.FrameTemplateSuit;
import com.izhi.platform.util.PageParameter;

public interface IFrameTemplateSuitService {
	int saveTemplateSuit(FrameTemplateSuit obj);
	boolean updateTemplateSuit(FrameTemplateSuit obj);
	boolean deleteTemplateSuit(int id);
	boolean deleteTemplateSuits(List<Integer> ids) ;
	FrameTemplateSuit findTemplateSuitById(int id);
	FrameTemplateSuit findTemplateSuitByName(String name);
	List<FrameTemplateSuit> findPage(PageParameter pp);
	int findTotalCount();
}
