package com.izhi.framework.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameFieldDao;
import com.izhi.framework.model.FrameField;
import com.izhi.framework.service.IFrameFieldService;
import com.izhi.platform.util.PageParameter;
@Service("frameFieldService")
public class FrameFieldServiceImpl implements IFrameFieldService {

	@Resource(name="frameFieldDao")
	private IFrameFieldDao frameFieldDao;
	
	@Override
	@CacheFlush(modelId="frameFieldFlushing")
	public boolean deleteField(int id) {
		return frameFieldDao.deleteField(id);
	}

	@Override
	@CacheFlush(modelId="frameFieldFlushing")
	public boolean deleteFields(List<Integer> ids) {
		return frameFieldDao.deleteFields(ids);
	}

	@Override
	@Cacheable(modelId="frameFieldCaching")
	public FrameField findFieldById(int id) {
		return frameFieldDao.findFieldById(id);
	}

	@Override
	@Cacheable(modelId="frameFieldCaching")
	public List<FrameField> findPage(PageParameter pp) {
		return frameFieldDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameFieldCaching")
	public int findTotalCount() {
		return frameFieldDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameFieldFlushing")
	public int saveField(FrameField obj) {
		return frameFieldDao.saveField(obj);
	}

	@Override
	@CacheFlush(modelId="frameFieldFlushing")
	public boolean updateField(FrameField obj) {
		return frameFieldDao.updateField(obj);
	}

	@Override
	@Cacheable(modelId="frameFieldCaching")
	public FrameField findFieldByName(String name) {
		return frameFieldDao.findFieldByName(name);
	}

	public IFrameFieldDao getFrameFieldDao() {
		return frameFieldDao;
	}

	public void setFrameFieldDao(IFrameFieldDao frameFieldDao) {
		this.frameFieldDao = frameFieldDao;
	}

}
