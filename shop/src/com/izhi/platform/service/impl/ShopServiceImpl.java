package com.izhi.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IShopDao;
import com.izhi.platform.model.Shop;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.IShopService;
import com.izhi.platform.util.PageParameter;

@Service("shopService")
public class ShopServiceImpl extends BaseService implements IShopService {
	@Resource(name = "shopDao")
	private IShopDao shopDao;

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void delete(Integer id) {
		this.delete(this.findById(id));
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void delete(Shop obj) {
		shopDao.delete(obj);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public int delete(String ids, String id) {
		return shopDao.delete(ids, id);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public int delete(String ids) {
		return this.delete(ids, "id");
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void deleteAll() {
		shopDao.deleteAll();
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public Shop findById(Integer id) {
		if (shopDao.findIsExist(id)) {
			return shopDao.findById(id);
		} else {
			return new Shop();
		}
	}

	@Override
	
	@Cacheable(modelId = "shopCaching")
	public List<Shop> findPage(int firstResult, int maxResult, String sortField,
			String sort) {
		return shopDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public Integer save(Shop obj) {
		return null;
	}

	public IShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(IShopDao dao) {
		this.shopDao = dao;
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public Integer save(Shop obj, String oldName) {
		if (obj == null) {
			return new Integer(-2);
		}

		// add
		if (obj.getShopId() == 0) {
			boolean exist = this.shopDao.findIsExist("name", obj.getShopName());
			if (exist) {
				// name exist
				return new Integer(-1);
			} else {
				if (obj.getParent() == null || obj.getParent().getShopId() == 0) {
					obj.setParent(null);
				}
				return this.shopDao.save(obj);
			}
		} else {
			// update
			if (obj.getShopName().equals(oldName)) {
				this.shopDao.update(obj);
				// success
				return new Integer(1);
			} else if (!this.shopDao.findIsExist("name", obj.getShopName())) {
				this.shopDao.update(obj);
				// success
				return new Integer(1);
			} else {
				// name exist
				return new Integer(-1);
			}
		}
	}

	@Override
	
	@Cacheable(modelId = "shopCaching")
	public List<Shop> findChildren(Integer id) {
		List<Shop> list = shopDao.findChildren(id);
		return list;
	}

	@Override
	
	@Cacheable(modelId = "shopCaching")
	public String findChildNodes(Integer id) {
		List<Map<String, Object>> list = shopDao.findChildNodes(id);
		return JSONArray.fromObject(list).toString();
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public boolean saveShop(Shop obj, String oldName) {
		int i=0;
		if(this.findExist(obj.getShopName(), oldName)){
			i=shopDao.updateShop(obj,oldName);
		}
		return i>0;
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public boolean saveShop(Shop obj) {
		return shopDao.saveShop(obj)>0;
	}

	@Override
	
	@Cacheable(modelId = "shopCaching")
	public boolean findIsExist(String nameFiled, String name) {
		return shopDao.findIsExist("name", name);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void update(Shop obj) {
		shopDao.update(obj);

	}

	@Override
	public List<Shop> find(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Shop> find(String sql, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Shop> find(String sql, String[] keys, Object[] objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public int findTotalCount() {
		return shopDao.findTotalCount();
	}


	@Override
	@Cacheable(modelId = "shopCaching")
	public int findTotalCount(int parentId) {
		return shopDao.findTotalCount(parentId);
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public List<Shop> findPage(PageParameter pp) {
		return shopDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public List<Shop> findPage(PageParameter pp, int parentId) {
		return shopDao.findPage(pp, parentId);
	}

	@Override	
	@Cacheable(modelId = "shopCaching")
	public List<Shop> findTopPage(PageParameter pp) {
		return shopDao.findTopPages(pp);
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public int findTopTotalCount() {
		return shopDao.findTopTotalCount();
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public boolean findExist(String name, String oname) {
		return shopDao.findExist(name, oname);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public boolean deleteShops(List<Integer> ids) {
		return shopDao.deleteShops(ids);
	}

}
