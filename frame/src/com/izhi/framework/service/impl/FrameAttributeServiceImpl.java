package com.izhi.framework.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameAttributeDao;
import com.izhi.framework.model.FrameAttribute;
import com.izhi.framework.service.IFrameAttributeService;
import com.izhi.platform.util.PageParameter;
@Service("frameAttributeService")
public class FrameAttributeServiceImpl implements IFrameAttributeService {

	@Resource(name="frameAttributeDao")
	private IFrameAttributeDao frameAttributeDao;
	
	@Override
	@CacheFlush(modelId="frameAttributeFlushing")
	public boolean deleteAttribute(int id) {
		return frameAttributeDao.deleteAttribute(id);
	}

	@Override
	@CacheFlush(modelId="frameAttributeFlushing")
	public boolean deleteAttributes(List<Integer> ids) {
		return frameAttributeDao.deleteAttributes(ids);
	}

	@Override
	@Cacheable(modelId="frameAttributeCaching")
	public FrameAttribute findAttributeById(int id) {
		return frameAttributeDao.findAttributeById(id);
	}

	@Override
	@Cacheable(modelId="frameAttributeCaching")
	public List<Map<String,Object>> findPage(PageParameter pp) {
		return frameAttributeDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameAttributeCaching")
	public int findTotalCount() {
		return frameAttributeDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameAttributeFlushing")
	public int saveAttribute(FrameAttribute obj) {
		return frameAttributeDao.saveAttribute(obj);
	}

	@Override
	@CacheFlush(modelId="frameAttributeFlushing")
	public boolean updateAttribute(FrameAttribute obj) {
		return frameAttributeDao.updateAttribute(obj);
	}

	@Override
	@Cacheable(modelId="frameAttributeCaching")
	public FrameAttribute findAttributeByName(String name) {
		return frameAttributeDao.findAttributeByName(name);
	}

	public IFrameAttributeDao getFrameAttributeDao() {
		return frameAttributeDao;
	}

	public void setFrameAttributeDao(IFrameAttributeDao frameAttributeDao) {
		this.frameAttributeDao = frameAttributeDao;
	}

}
