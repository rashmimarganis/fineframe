package com.izhi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.cms.dao.ITemplateDao;
import com.izhi.cms.model.Template;
import com.izhi.cms.service.ITemplateService;
import com.izhi.platform.util.PageParameter;
@Service("templateService")
public class TemplateServiceImpl implements ITemplateService {

	@Resource(name="templateDao")
	private ITemplateDao templateDao;
	public ITemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	@Override
	@CacheFlush(modelId="templateFlushing")
	public boolean deleteTemplate(int id) {
		return templateDao.deleteTemplate(id);
	}

	@Override
	@CacheFlush(modelId="templateFlushing")
	public boolean deleteTemplates(List<Integer> ids) {
		return templateDao.deleteTemplates(ids);
	}

	@Override
	@Cacheable(modelId="templateCaching")
	public Template findTemplateById(int id) {
		return templateDao.findTemplateById(id);
	}

	@Override
	@Cacheable(modelId="templateCaching")
	public List<Template> findPage(PageParameter pp) {
		return templateDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="templateCaching")
	public int findTotalCount() {
		return templateDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="templateFlushing")
	public int saveTemplate(Template obj) {
		return templateDao.saveTemplate(obj);
	}

	@Override
	@CacheFlush(modelId="templateFlushing")
	public boolean updateTemplate(Template obj) {
		return templateDao.updateTemplate(obj);
	}

	@Override
	public Template findTemplateByName(String name) {
		return templateDao.findTemplateByName(name);
	}

}
