package com.izhi.platform.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IShopDao;
import com.izhi.platform.model.Shop;
@Service("orgDao")
public class ShopDaoImpl extends BaseDaoImpl<Shop, Integer> implements IShopDao {

	@Override
	public List<Shop> findChildren(Integer parentId) {
		String sql="";
		if(parentId==0){
			sql="select o from Shop o where o.parent.id is null";
			return this.find(sql);
		}else{
			sql="select o from Shop o where o.parent.id=?";
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
	public Map<String, Object> saveShop(Shop obj, String oldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> saveShop(Shop obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
