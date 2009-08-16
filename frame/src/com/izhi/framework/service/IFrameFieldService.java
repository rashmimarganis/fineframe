package com.izhi.framework.service;

import java.util.List;

import com.izhi.framework.model.FrameField;
import com.izhi.platform.util.PageParameter;

public interface IFrameFieldService {
	int saveField(FrameField obj);
	boolean updateField(FrameField obj);
	boolean deleteField(int id);
	boolean deleteFields(List<Integer> ids) ;
	FrameField findFieldById(int id);
	FrameField findFieldByName(String name);
	List<FrameField> findPage(PageParameter pp);
	int findTotalCount();
}
