package com.izhi.framework.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameTemplateDao;
import com.izhi.framework.model.FrameTemplateFile;
import com.izhi.framework.service.IFrameTemplateService;
import com.izhi.platform.util.PageParameter;
@Service("frameTemplateService")
public class FrameTemplateServiceImpl implements IFrameTemplateService {

	@Resource(name="frameTemplateDao")
	private IFrameTemplateDao frameTemplateDao;
	
	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public boolean deleteTemplate(int id) {
		return frameTemplateDao.deleteTemplate(id);
	}

	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public boolean deleteTemplates(List<Integer> ids) {
		return frameTemplateDao.deleteTemplates(ids);
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public FrameTemplateFile findTemplateById(int id) {
		return frameTemplateDao.findTemplateById(id);
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public List<FrameTemplateFile> findPage(PageParameter pp) {
		return frameTemplateDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public int findTotalCount() {
		return frameTemplateDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public int saveTemplate(FrameTemplateFile obj) {
		return frameTemplateDao.saveTemplate(obj);
	}

	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public boolean updateTemplate(FrameTemplateFile obj) {
		return frameTemplateDao.updateTemplate(obj);
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public FrameTemplateFile findTemplateByName(String name) {
		return frameTemplateDao.findTemplateByName(name);
	}

	public IFrameTemplateDao getFrameTemplateDao() {
		return frameTemplateDao;
	}

	public void setFrameTemplateDao(IFrameTemplateDao frameTemplateDao) {
		this.frameTemplateDao = frameTemplateDao;
	}

}
