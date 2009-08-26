package com.izhi.framework.service;

import java.util.List;
import java.util.Map;

import com.izhi.framework.model.FrameAttribute;
import com.izhi.platform.util.PageParameter;

public interface IFrameAttributeService {
	int saveAttribute(FrameAttribute obj);
	boolean updateAttribute(FrameAttribute obj);
	boolean deleteAttribute(int id);
	boolean deleteAttributes(List<Integer> ids) ;
	FrameAttribute findAttributeById(int id);
	FrameAttribute findAttributeByName(String name);
	List<Map<String,Object>> findPage(PageParameter pp);
	int findTotalCount();
	List<Map<String,Object>> findJsonById(int id);
	
	List<Map<String,Object>> findPageByModel(int mid,PageParameter pp);
	int findTotalCount(int mid);
}
