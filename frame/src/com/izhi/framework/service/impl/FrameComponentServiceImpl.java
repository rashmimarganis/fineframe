package com.izhi.framework.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameComponentDao;
import com.izhi.framework.model.FrameComponent;
import com.izhi.framework.service.IFrameComponentService;
import com.izhi.platform.util.PageParameter;
@Service("frameComponentService")
public class FrameComponentServiceImpl implements IFrameComponentService {

	@Resource(name="frameComponentDao")
	private IFrameComponentDao frameComponentDao;
	
	@Override
	@CacheFlush(modelId="frameComponentFlushing")
	public boolean deleteComponent(int id) {
		return frameComponentDao.deleteComponent(id);
	}

	@Override
	@CacheFlush(modelId="frameComponentFlushing")
	public boolean deleteComponents(List<Integer> ids) {
		return frameComponentDao.deleteComponents(ids);
	}

	@Override
	@Cacheable(modelId="frameComponentCaching")
	public FrameComponent findComponentById(int id) {
		return frameComponentDao.findComponentById(id);
	}

	@Override
	@Cacheable(modelId="frameComponentCaching")
	public List<Map<String,Object>> findPage(PageParameter pp) {
		return frameComponentDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameComponentCaching")
	public int findTotalCount() {
		return frameComponentDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameComponentFlushing")
	public int saveComponent(FrameComponent obj) {
		return frameComponentDao.saveComponent(obj);
	}

	@Override
	@CacheFlush(modelId="frameComponentFlushing")
	public boolean updateComponent(FrameComponent obj) {
		return frameComponentDao.updateComponent(obj);
	}

	@Override
	@Cacheable(modelId="frameComponentCaching")
	public FrameComponent findComponentByName(String name) {
		return frameComponentDao.findComponentByName(name);
	}

	public IFrameComponentDao getFrameComponentDao() {
		return frameComponentDao;
	}

	public void setFrameComponentDao(IFrameComponentDao frameComponentDao) {
		this.frameComponentDao = frameComponentDao;
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return frameComponentDao.findJsonById(id);
	}

}
