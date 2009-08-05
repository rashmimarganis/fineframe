package com.izhi.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IRegionDao;
import com.izhi.platform.model.Region;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.IRegionService;

@Service("regionService")
public class RegionServiceImpl extends BaseService implements IRegionService {
	@Resource(name = "regionDao")
	private IRegionDao regionDao;

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public void delete(Integer id) {
		regionDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public void delete(Region obj) {
		regionDao.delete(obj);
	}

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public int delete(String ids, String id) {
		return regionDao.delete(ids, id);
	}

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public int delete(String ids) {
		return regionDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public void deleteAll() {
		regionDao.deleteAll();
	}

	@Override
	@Cacheable(modelId = "regionCaching")
	public List<Region> find(String sql) {
		return regionDao.find(sql);
	}

	@Override
	@Cacheable(modelId = "regionCaching")
	public List<Region> find(String sql, Object obj) {
		return regionDao.find(sql, obj);
	}

	@Override
	@Cacheable(modelId = "regionCaching")
	public List<Region> find(String sql, String[] keys, Object[] objs) {
		return regionDao.find(sql, keys, objs);
	}

	@Override
	@Cacheable(modelId = "regionCaching")
	public Region findById(Integer id) {
		return regionDao.findById(id);
	}

	@Cacheable(modelId = "regionCaching")
	public List<Region> findPage(int firstResult, int maxResult,
			String sortField, String sort) {
		return regionDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public Integer save(Region obj) {
		return regionDao.save(obj);
	}

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public Integer save(Region obj, String oldName) {
		if (obj == null) {
			return new Integer(-2);
		}

		// add
		if (obj.getId() == 0) {
			boolean exist = this.regionDao.findIsExist("name", obj.getName());
			if (exist) {
				// name exist
				return new Integer(-1);
			} else {
				if (obj.getParent() == null || obj.getParent().getId() == 0) {
					obj.setParent(null);
				}
				return this.regionDao.save(obj);
			}
		} else {
			// update
			if (obj.getName().equals(oldName)) {
				this.regionDao.update(obj);
				// success
				return new Integer(1);
			} else if (!this.regionDao.findIsExist("name", obj.getName())) {
				this.regionDao.update(obj);
				// success
				return new Integer(1);
			} else {
				// name exist
				return new Integer(-1);
			}
		}
	}

	public IRegionDao getDao() {
		return regionDao;
	}

	public void setDao(IRegionDao dao) {
		this.regionDao = dao;
	}

	@Override
	@Cacheable(modelId = "regionCaching")
	public String findChildNodes(Integer id) {
		List<Map<String, Object>> list = regionDao.findChildren(id);
		return JSONArray.fromObject(list).toString();
	}

	@Override
	@Cacheable(modelId = "regionCaching")
	public boolean findIsExist(String nameFiled, String name) {
		return regionDao.findIsExist(nameFiled, name);
	}

	@Override
	@CacheFlush(modelId = "regionFlushing")
	public Map<String, Object> saveRegion(Region obj, String oldName) {
		boolean exist = false;
		if (obj.getId() == 0) {
			exist = this.findIsExist("code", obj.getName());
			if (!exist) {
				if (obj.getParent() == null || obj.getParent().getId() == 0) {
					obj.setParent(null);
				}
				Integer id = this.regionDao.save(obj);
				obj.setId(id);
			} else {
				exist = true;
			}
		} else {
			// update
			if (obj.getName().equals(oldName)) {
				this.regionDao.update(obj);

			} else if (!this.findIsExist("code", obj.getName())) {
				this.regionDao.update(obj);
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
	@CacheFlush(modelId = "regionFlushing")
	public Map<String, Object> saveRegion(Region obj) {
		boolean exist = false;
		exist = this.findIsExist("code", obj.getName());
		if (!exist) {
			Integer id = this.regionDao.save(obj);
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
	@CacheFlush(modelId = "regionFlushing")
	public void update(Region obj) {
		regionDao.update(obj);

	}

}
