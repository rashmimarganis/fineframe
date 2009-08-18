package com.izhi.framework.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameControlDao;
import com.izhi.framework.model.FrameControl;
import com.izhi.framework.service.IFrameControlService;
import com.izhi.platform.util.PageParameter;
@Service("frameControlService")
public class FrameControlServiceImpl implements IFrameControlService {

	@Resource(name="frameControlDao")
	private IFrameControlDao frameControlDao;
	
	@Override
	@CacheFlush(modelId="frameControlFlushing")
	public boolean deleteControl(int id) {
		return frameControlDao.deleteControl(id);
	}

	@Override
	@CacheFlush(modelId="frameControlFlushing")
	public boolean deleteControls(List<Integer> ids) {
		return frameControlDao.deleteControls(ids);
	}

	@Override
	@Cacheable(modelId="frameControlCaching")
	public FrameControl findControlById(int id) {
		return frameControlDao.findControlById(id);
	}

	@Override
	@Cacheable(modelId="frameControlCaching")
	public List<Map<String,Object>> findPage(PageParameter pp) {
		return frameControlDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameControlCaching")
	public int findTotalCount() {
		return frameControlDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameControlFlushing")
	public int saveControl(FrameControl obj) {
		return frameControlDao.saveControl(obj);
	}

	@Override
	@CacheFlush(modelId="frameControlFlushing")
	public boolean updateControl(FrameControl obj) {
		return frameControlDao.updateControl(obj);
	}

	@Override
	@Cacheable(modelId="frameControlCaching")
	public FrameControl findControlByName(String name) {
		return frameControlDao.findControlByName(name);
	}

	public IFrameControlDao getFrameControlDao() {
		return frameControlDao;
	}

	public void setFrameControlDao(IFrameControlDao frameControlDao) {
		this.frameControlDao = frameControlDao;
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return frameControlDao.findJsonById(id);
	}

}
