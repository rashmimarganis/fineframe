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

@Service("orgService")
public class OrgServiceImpl extends BaseService implements IOrgService {
	@Resource(name = "orgDao")
	private IOrgDao orgDao;

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void delete(Integer id) {
		this.delete(this.findById(id));
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void delete(Org obj) {
		orgDao.delete(obj);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public int delete(String ids, String id) {
		return orgDao.delete(ids, id);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public int delete(String ids) {
		return this.delete(ids, "id");
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void deleteAll() {
		orgDao.deleteAll();
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public List<Org> find(String sql) {
		return orgDao.find(sql);
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public List<Org> find(String sql, Object obj) {
		return orgDao.find(sql, obj);
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public List<Org> find(String sql, String[] keys, Object[] objs) {
		return orgDao.find(sql, keys, objs);
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public Org findById(Integer id) {
		if (orgDao.findIsExist(id)) {
			return orgDao.findById(id);
		} else {
			return new Org();
		}
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public List<Org> findPage(int firstResult, int maxResult, String sortField,
			String sort) {
		return orgDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public Integer save(Org obj) {
		return null;
	}

	public IOrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(IOrgDao dao) {
		this.orgDao = dao;
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public Integer save(Org obj, String oldName) {
		if (obj == null) {
			return new Integer(-2);
		}

		// add
		if (obj.getId() == 0) {
			boolean exist = this.orgDao.findIsExist("name", obj.getName());
			if (exist) {
				// name exist
				return new Integer(-1);
			} else {
				if (obj.getParent() == null || obj.getParent().getId() == 0) {
					obj.setParent(null);
				}
				return this.orgDao.save(obj);
			}
		} else {
			// update
			if (obj.getName().equals(oldName)) {
				this.orgDao.update(obj);
				// success
				return new Integer(1);
			} else if (!this.orgDao.findIsExist("name", obj.getName())) {
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
	
	@Cacheable(modelId = "orgCaching")
	public List<Org> findChildren(Integer id) {
		List<Org> list = orgDao.findChildren(id);
		return list;
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public String findChildNodes(Integer id) {
		List<Map<String, Object>> list = orgDao.findChildNodes(id);
		return JSONArray.fromObject(list).toString();
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public Map<String, Object> saveOrg(Org obj, String oldName) {
		boolean exist = false;
		if (obj.getId() == 0) {
			exist = this.findIsExist("name", obj.getName());
			if (!exist) {
				if (obj.getParent() == null || obj.getParent().getId() == 0) {
					obj.setParent(null);
				}
				Integer id = this.orgDao.save(obj);
				obj.setId(id);
			} else {
				exist = true;
			}
		} else {
			// update
			if (obj.getName().equals(oldName)) {
				this.orgDao.update(obj);

			} else if (!this.findIsExist("name", obj.getName())) {
				this.orgDao.update(obj);
			} else {
				exist = true;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exist", exist);
		map.put("obj", obj);
		return map;
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public Map<String, Object> saveOrg(Org obj) {

		boolean exist = false;
		exist = this.findIsExist("name", obj.getName());
		if (!exist) {
			Integer id = this.orgDao.save(obj);
			obj.setId(id);
		} else {
			exist = true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exist", exist);
		map.put("obj", obj);
		return map;
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public boolean findIsExist(String nameFiled, String name) {
		return orgDao.findIsExist("name", name);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void update(Org obj) {
		orgDao.update(obj);

	}

}
