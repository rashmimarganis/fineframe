package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Region;

public interface IRegionDao extends IBaseDao<Region, Integer> {
	List<Map<String,Object>> findChildren(int id);
}
