package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Area;

public interface IAreaService {
	int saveArea(Area obj);
	boolean updateArea(Area obj);
	boolean deleteArea(int id);
	boolean deleteAreas(List<Integer> ids) ;
	Area findAreaById(int id);
	List<Area> findPage(PageParameter pp);
	int findTotalCount();
}
