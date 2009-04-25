package com.izhi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.cms.dao.ITemplateSuitDao;
import com.izhi.cms.model.TemplateSuit;
import com.izhi.cms.service.ITemplateSuitService;
import com.izhi.platform.util.PageParameter;
@Service("templateSuitService")
public class TemplateSuitServiceImpl implements ITemplateSuitService {

	@Resource(name="templateSuitDao")
	private ITemplateSuitDao templateSuitDao;
	public ITemplateSuitDao getTemplateSuitDao() {
		return templateSuitDao;
	}

	public void setTemplateSuitDao(ITemplateSuitDao templateSuitDao) {
		this.templateSuitDao = templateSuitDao;
	}

	@Override
	@CacheFlush(modelId="templateSuitFlushing")
	public boolean deleteTemplateSuit(int id) {
		return templateSuitDao.deleteTemplateSuit(id);
	}

	@Override
	@CacheFlush(modelId="templateSuitFlushing")
	public boolean deleteTemplateSuits(List<Integer> ids) {
		return templateSuitDao.deleteTemplateSuits(ids);
	}

	@Override
	@Cacheable(modelId="templateSuitCaching")
	public TemplateSuit findTemplateSuitById(int id) {
		return templateSuitDao.findTemplateSuitById(id);
	}

	@Override
	@Cacheable(modelId="templateSuitCaching")
	public List<TemplateSuit> findPage(PageParameter pp) {
		return templateSuitDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="templateSuitCaching")
	public int findTotalCount() {
		return templateSuitDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="templateSuitFlushing")
	public int saveTemplateSuit(TemplateSuit obj) {
		return templateSuitDao.saveTemplateSuit(obj);
	}

	@Override
	@CacheFlush(modelId="templateSuitFlushing")
	public boolean updateTemplateSuit(TemplateSuit obj) {
		return templateSuitDao.updateTemplateSuit(obj);
	}

	@Override
	public TemplateSuit findTemplateSuitByName(String name) {
		return templateSuitDao.findTemplateSuitByName(name);
	}

}
