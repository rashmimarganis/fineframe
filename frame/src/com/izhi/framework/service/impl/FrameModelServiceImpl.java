package com.izhi.framework.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameModelDao;
import com.izhi.framework.model.FrameModel;
import com.izhi.framework.service.IFrameModelService;
import com.izhi.platform.util.PageParameter;
@Service("frameModelService")
public class FrameModelServiceImpl implements IFrameModelService {

	@Resource(name="frameModelDao")
	private IFrameModelDao frameModelDao;
	
	@Override
	@CacheFlush(modelId="frameModelFlushing")
	public boolean deleteModel(int id) {
		return frameModelDao.deleteModel(id);
	}

	@Override
	@CacheFlush(modelId="frameModelFlushing")
	public boolean deleteModels(List<Integer> ids) {
		return frameModelDao.deleteModels(ids);
	}

	@Override
	@Cacheable(modelId="frameModelCaching")
	public FrameModel findModelById(int id) {
		return frameModelDao.findModelById(id);
	}

	@Override
	@Cacheable(modelId="frameModelCaching")
	public List<Map<String,Object>> findPage(PageParameter pp) {
		return frameModelDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameModelCaching")
	public int findTotalCount() {
		return frameModelDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameModelFlushing")
	public int saveModel(FrameModel obj) {
		return frameModelDao.saveModel(obj);
	}

	@Override
	@CacheFlush(modelId="frameModelFlushing")
	public boolean updateModel(FrameModel obj) {
		return frameModelDao.updateModel(obj);
	}

	@Override
	@Cacheable(modelId="frameModelCaching")
	public FrameModel findModelByName(String name) {
		return frameModelDao.findModelByName(name);
	}

	public IFrameModelDao getFrameModelDao() {
		return frameModelDao;
	}

	public void setFrameModelDao(IFrameModelDao frameModelDao) {
		this.frameModelDao = frameModelDao;
	}

}
