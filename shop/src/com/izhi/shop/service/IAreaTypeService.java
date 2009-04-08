package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.AreaType;

public interface IAreaTypeService {
	int saveAreaType(AreaType obj);
	boolean updateAreaType(AreaType obj);
	boolean deleteAreaType(int id);
	boolean deleteAreaTypes(List<Integer> ids) ;
	AreaType findAreaTypeById(int id);
	List<AreaType> findPage(PageParameter pp);
	List<AreaType> findAll();
	int findTotalCount();
}
