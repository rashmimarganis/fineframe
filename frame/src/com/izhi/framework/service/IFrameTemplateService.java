package com.izhi.framework.service;

import java.util.List;
import java.util.Map;

import com.izhi.framework.model.FrameTemplate;
import com.izhi.platform.util.PageParameter;

public interface IFrameTemplateService {
	int saveTemplate(FrameTemplate obj);
	boolean updateTemplate(FrameTemplate obj);
	boolean deleteTemplate(int id);
	boolean deleteTemplates(List<Integer> ids) ;
	FrameTemplate findTemplateById(int id);
	FrameTemplate findTemplateByName(String name);
	List<Map<String,Object>> findJsonById(int id);
	List<Map<String,Object>> findPage(PageParameter pp);
	int findTotalCount();
	
	String loadFile(FrameTemplate obj);
	String loadFile(String path);
	boolean saveFile(String path,String content);
	
	boolean isTemplateNameExist(FrameTemplate obj);
	List<Map<String,Object>> findJsonByType(String type);
	int findTotalCountByType(String type);
}
