package com.izhi.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IOrgDao;
import com.izhi.platform.model.Org;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.IOrgService;
import com.izhi.platform.util.PageParameter;

@Service("shopService")
public class OrgServiceImpl extends BaseService implements IOrgService {
	@Resource(name = "orgDao")
	private IOrgDao orgDao;

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void delete(Integer id) {
		this.delete(this.findById(id));
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void delete(Org obj) {
		orgDao.delete(obj);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public int delete(String ids, String id) {
		return orgDao.delete(ids, id);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public int delete(String ids) {
		return this.delete(ids, "id");
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void deleteAll() {
		orgDao.deleteAll();
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public Org findById(Integer id) {
		if (orgDao.findIsExist(id)) {
			return orgDao.findById(id);
		} else {
			return new Org();
		}
	}

	@Override
	
	@Cacheable(modelId = "shopCaching")
	public List<Org> findPage(int firstResult, int maxResult, String sortField,
			String sort) {
		return orgDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public Integer save(Org obj) {
		return null;
	}

	public IOrgDao getShopDao() {
		return orgDao;
	}

	public void setShopDao(IOrgDao dao) {
		this.orgDao = dao;
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public Integer save(Org obj, String oldName) {
		if (obj == null) {
			return new Integer(-2);
		}

		// add
		if (obj.getOrgId() == 0) {
			boolean exist = this.orgDao.findIsExist("name", obj.getOrgName());
			if (exist) {
				// name exist
				return new Integer(-1);
			} else {
				if (obj.getParent() == null || obj.getParent().getOrgId() == 0) {
					obj.setParent(null);
				}
				return this.orgDao.save(obj);
			}
		} else {
			// update
			if (obj.getOrgName().equals(oldName)) {
				this.orgDao.update(obj);
				// success
				return new Integer(1);
			} else if (!this.orgDao.findIsExist("name", obj.getOrgName())) {
				this.orgDao.update(obj);
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
	public List<Org> findChildren(Integer id) {
		List<Org> list = orgDao.findChildren(id);
		return list;
	}

	@Override
	
	@Cacheable(modelId = "shopCaching")
	public String findChildNodes(Integer id) {
		List<Map<String, Object>> list = orgDao.findChildNodes(id);
		return JSONArray.fromObject(list).toString();
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public boolean saveOrg(Org obj, String oldName) {
		int i=0;
		if(this.findExist(obj.getOrgName(), oldName)){
			i=orgDao.updateOrg(obj,oldName);
		}
		return i>0;
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public boolean saveOrg(Org obj) {
		return orgDao.saveOrg(obj)>0;
	}

	@Override
	
	@Cacheable(modelId = "shopCaching")
	public boolean findIsExist(String nameFiled, String name) {
		return orgDao.findIsExist("name", name);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public void update(Org obj) {
		orgDao.update(obj);

	}

	@Override
	public List<Org> find(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Org> find(String sql, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Org> find(String sql, String[] keys, Object[] objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public int findTotalCount() {
		return orgDao.findTotalCount();
	}


	@Override
	@Cacheable(modelId = "shopCaching")
	public int findTotalCount(int parentId) {
		return orgDao.findTotalCount(parentId);
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public List<Org> findPage(PageParameter pp) {
		return orgDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public List<Org> findPage(PageParameter pp, int parentId) {
		return orgDao.findPage(pp, parentId);
	}

	@Override	
	@Cacheable(modelId = "shopCaching")
	public List<Org> findTopPage(PageParameter pp) {
		return orgDao.findTopPages(pp);
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public int findTopTotalCount() {
		return orgDao.findTopTotalCount();
	}

	@Override
	@Cacheable(modelId = "shopCaching")
	public boolean findExist(String name, String oname) {
		return orgDao.findExist(name, oname);
	}

	@Override
	@CacheFlush(modelId = "shopFlushing")
	public boolean deleteOrgs(List<Integer> ids) {
		return orgDao.deleteShops(ids);
	}

}
