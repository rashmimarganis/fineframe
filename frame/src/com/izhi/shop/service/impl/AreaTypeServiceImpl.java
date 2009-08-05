package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IAreaTypeDao;
import com.izhi.shop.model.AreaType;
import com.izhi.shop.service.IAreaTypeService;
@Service("areaTypeService")
public class AreaTypeServiceImpl implements IAreaTypeService {

	@Resource(name="areaTypeDao")
	private IAreaTypeDao areaTypeDao;
	public IAreaTypeDao getAreaTypeDao() {
		return areaTypeDao;
	}

	public void setAreaTypeDao(IAreaTypeDao areaTypeDao) {
		this.areaTypeDao = areaTypeDao;
	}

	@Override
	@CacheFlush(modelId="areaTypeFlushing")
	public boolean deleteAreaType(int id) {
		return areaTypeDao.deleteAreaType(id);
	}

	@Override
	@CacheFlush(modelId="areaTypeFlushing")
	public boolean deleteAreaTypes(List<Integer> ids) {
		return areaTypeDao.deleteAreaTypes(ids);
	}

	@Override
	@Cacheable(modelId="areaTypeCaching")
	public AreaType findAreaTypeById(int id) {
		return areaTypeDao.findAreaTypeById(id);
	}

	@Override
	@Cacheable(modelId="areaTypeCaching")
	public List<AreaType> findPage(PageParameter pp) {
		return areaTypeDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="areaTypeCaching")
	public int findTotalCount() {
		return areaTypeDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="areaTypeFlushing")
	public int saveAreaType(AreaType obj) {
		return areaTypeDao.saveAreaType(obj);
	}

	@Override
	@CacheFlush(modelId="areaTypeFlushing")
	public boolean updateAreaType(AreaType obj) {
		return areaTypeDao.updateAreaType(obj);
	}

	@Override
	public List<AreaType> findAll() {
		return areaTypeDao.findAll();
	}

}
