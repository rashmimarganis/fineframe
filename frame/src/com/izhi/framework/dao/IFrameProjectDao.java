package com.izhi.framework.dao;

import java.util.List;
import java.util.Map;

import com.izhi.framework.model.FrameProject;
import com.izhi.platform.util.PageParameter;

public interface IFrameProjectDao {
	int saveProject(FrameProject obj);
	boolean updateProject(FrameProject obj);
	boolean deleteProject(int id);
	boolean deleteProjects(List<Integer> ids) ;
	FrameProject findProjectById(int id);
	FrameProject findProjectByName(String name);
	List<Map<String,Object>> findPage(PageParameter pp);
	int findTotalCount();
	List<Map<String,Object>> findJsonById(int id);
	List<Map<String,Object>> findAll();
	
}
