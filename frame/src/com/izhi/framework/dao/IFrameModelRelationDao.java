package com.izhi.framework.dao;

import java.util.List;
import java.util.Map;

import com.izhi.framework.model.FrameModelRelation;
import com.izhi.platform.util.PageParameter;

public interface IFrameModelRelationDao {
	int saveRelation(FrameModelRelation obj);
	boolean updateRelation(FrameModelRelation obj);
	boolean deleteRelation(int id);
	boolean deleteRelations(List<Integer> ids) ;
	FrameModelRelation findRelationById(int id);
	FrameModelRelation findRelationByName(String name);
	List<Map<String,Object>> findPage(PageParameter pp);
	int findTotalCount();
	List<Map<String,Object>> findJsonById(int id);
	
	List<Map<String,Object>> findPageByModel(int mid,PageParameter pp);
	int findTotalCount(int mid);
	
	List<Map<String,Object>> findNoRelation(int mid);
	int findNoRelationTotalCount(int mid);
}
