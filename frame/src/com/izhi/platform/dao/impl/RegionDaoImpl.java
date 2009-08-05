package com.izhi.platform.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IRegionDao;
import com.izhi.platform.model.Region;
@SuppressWarnings("unchecked")
@Service("regionDao")
public class RegionDaoImpl extends BaseDaoImpl<Region,Integer> implements IRegionDao {

	@Override
	public List<Map<String, Object>> findChildren(int id) {
		String sql="select new map(r.id as id ,r.name as name,r.title as title) from Region r where r.parent.id=?";
		List<Map<String, Object>> list=this.getHibernateTemplate().find(sql, id);
		return list;
	}

	

}
