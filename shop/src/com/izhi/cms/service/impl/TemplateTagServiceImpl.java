package com.izhi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.cms.dao.ITemplateTagDao;
import com.izhi.cms.model.TemplateTag;
import com.izhi.cms.service.ITemplateTagService;
import com.izhi.platform.util.PageParameter;
@Service("templateTagService")
public class TemplateTagServiceImpl implements ITemplateTagService {

	@Resource(name="templateTagDao")
	private ITemplateTagDao templateTagDao;
	public ITemplateTagDao getTemplateTagDao() {
		return templateTagDao;
	}

	public void setTemplateTagDao(ITemplateTagDao templateTagDao) {
		this.templateTagDao = templateTagDao;
	}

	@Override
	@CacheFlush(modelId="templateTagFlushing")
	public boolean deleteTemplateTag(int id) {
		return templateTagDao.deleteTemplateTag(id);
	}

	@Override
	@CacheFlush(modelId="templateTagFlushing")
	public boolean deleteTemplateTags(List<Integer> ids) {
		return templateTagDao.deleteTemplateTags(ids);
	}

	@Override
	@Cacheable(modelId="templateTagCaching")
	public TemplateTag findTemplateTagById(int id) {
		return templateTagDao.findTemplateTagById(id);
	}

	@Override
	@Cacheable(modelId="templateTagCaching")
	public List<TemplateTag> findPage(PageParameter pp) {
		return templateTagDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="templateTagCaching")
	public int findTotalCount() {
		return templateTagDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="templateTagFlushing")
	public int saveTemplateTag(TemplateTag obj) {
		return templateTagDao.saveTemplateTag(obj);
	}

	@Override
	@CacheFlush(modelId="templateTagFlushing")
	public boolean updateTemplateTag(TemplateTag obj) {
		return templateTagDao.updateTemplateTag(obj);
	}

	@Override
	public TemplateTag findTemplateTagByName(String name) {
		return templateTagDao.findTemplateTagByName(name);
	}

}
