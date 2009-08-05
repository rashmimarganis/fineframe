package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IAreaDao;
import com.izhi.shop.model.Area;
import com.izhi.shop.service.IAreaService;
@Service("areaService")
public class AreaServiceImpl implements IAreaService {

	@Resource(name="areaDao")
	private IAreaDao areaDao;
	public IAreaDao getAreaDao() {
		return areaDao;
	}

	public void setAreaDao(IAreaDao areaDao) {
		this.areaDao = areaDao;
	}

	@Override
	@CacheFlush(modelId="areaFlushing")
	public boolean deleteArea(int id) {
		return areaDao.deleteArea(id);
	}

	@Override
	@CacheFlush(modelId="areaFlushing")
	public boolean deleteAreas(List<Integer> ids) {
		return areaDao.deleteAreas(ids);
	}

	@Override
	@Cacheable(modelId="areaCaching")
	public Area findAreaById(int id) {
		return areaDao.findAreaById(id);
	}

	@Override
	@Cacheable(modelId="areaCaching")
	public List<Area> findPage(PageParameter pp) {
		return areaDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="areaCaching")
	public int findTotalCount() {
		return areaDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="areaFlushing")
	public int saveArea(Area obj) {
		return areaDao.saveArea(obj);
	}

	@Override
	@CacheFlush(modelId="areaFlushing")
	public boolean updateArea(Area obj) {
		return areaDao.updateArea(obj);
	}

}
