package com.izhi.framework.dao;

import java.util.List;
import java.util.Map;

import com.izhi.framework.model.FrameComponent;
import com.izhi.platform.util.PageParameter;

public interface IFrameComponentDao {
	int saveComponent(FrameComponent obj);
	boolean updateComponent(FrameComponent obj);
	boolean deleteComponent(int id);
	boolean deleteComponents(List<Integer> ids) ;
	FrameComponent findComponentById(int id);
	FrameComponent findComponentByName(String name);
	List<Map<String,Object>> findPage(PageParameter pp);
	int findTotalCount();
	List<Map<String,Object>> findJsonById(int id);
	List<FrameComponent> findAll();
	
}
