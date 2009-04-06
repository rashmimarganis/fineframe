package com.izhi.platform.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IOrgDao;
import com.izhi.platform.model.Org;
@Service("orgDao")
public class OrgDaoImpl extends BaseDaoImpl<Org, Integer> implements IOrgDao {

	@Override
	public List<Org> findChildren(Integer parentId) {
		String sql="";
		if(parentId==0){
			sql="select o from Org o where o.parent.id is null";
			return this.find(sql);
		}else{
			sql="select o from Org o where o.parent.id=?";
			return this.find(sql, parentId);
		}
	}

	@Override
	public List<Map<String,Object>> findChildNodes(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findIsExist(String nameFiled, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> saveOrg(Org obj, String oldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> saveOrg(Org obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
