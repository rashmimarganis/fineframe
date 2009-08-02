package com.izhi.framework.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameTemplateSuitDao;
import com.izhi.framework.model.FrameTemplateSuit;
import com.izhi.framework.service.IFrameTemplateSuitService;
import com.izhi.platform.util.PageParameter;
@Service("frameTemplateSuitService")
public class FrameTemplateSuitServiceImpl implements IFrameTemplateSuitService {

	@Resource(name="frameTemplateSuitDao")
	private IFrameTemplateSuitDao templateSuitDao;
	

	@Override
	@CacheFlush(modelId="frameTemplateSuitFlushing")
	public boolean deleteTemplateSuit(int id) {
		return templateSuitDao.deleteTemplateSuit(id);
	}

	@Override
	@CacheFlush(modelId="frameTemplateSuitFlushing")
	public boolean deleteTemplateSuits(List<Integer> ids) {
		return templateSuitDao.deleteTemplateSuits(ids);
	}

	@Override
	@Cacheable(modelId="frameTemplateSuitCaching")
	public FrameTemplateSuit findTemplateSuitById(int id) {
		return templateSuitDao.findTemplateSuitById(id);
	}

	@Override
	@Cacheable(modelId="templateSuitCaching")
	public List<FrameTemplateSuit> findPage(PageParameter pp) {
		return templateSuitDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="templateSuitCaching")
	public int findTotalCount() {
		return templateSuitDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="templateSuitFlushing")
	public int saveTemplateSuit(FrameTemplateSuit obj) {
		return templateSuitDao.saveTemplateSuit(obj);
	}

	@Override
	@CacheFlush(modelId="templateSuitFlushing")
	public boolean updateTemplateSuit(FrameTemplateSuit obj) {
		return templateSuitDao.updateTemplateSuit(obj);
	}

	@Override
	public FrameTemplateSuit findTemplateSuitByName(String name) {
		return templateSuitDao.findTemplateSuitByName(name);
	}

	
}
