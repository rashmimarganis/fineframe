package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTemplateDao;
import com.izhi.cms.model.CmsTemplate;
import com.izhi.cms.service.ICmsTemplateService;
import com.izhi.platform.util.PageParameter;
@Service("cmsTemplateService")
public class CmsTemplateServiceImpl implements ICmsTemplateService{

	@Resource(name="cmsTemplateDao")
	private ICmsTemplateDao cmsTemplateDao;
	@Override
	public boolean deleteTemplate(int id) {
		return cmsTemplateDao.deleteTemplate(id);
	}

	@Override
	public boolean deleteTemplates(List<Integer> ids) {
		return cmsTemplateDao.deleteTemplates(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsTemplateDao.findJsonById(id);
	}

	@Override
	public CmsTemplate findTemplateById(int id) {
		return cmsTemplateDao.findTemplateById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsTemplateDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsTemplateDao.findTotalCount();
	}

	@Override
	public int saveTemplate(CmsTemplate obj) {
		if(obj!=null){
			if(obj.getTemplateId()==0){
				return cmsTemplateDao.saveTemplate(obj);
			}else{
				return cmsTemplateDao.updateTemplate(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateTemplate(CmsTemplate obj) {
		return cmsTemplateDao.updateTemplate(obj);
	}

	public ICmsTemplateDao getCmsTemplateDao() {
		return cmsTemplateDao;
	}

	public void setCmsTemplateDao(ICmsTemplateDao cmsTemplateDao) {
		this.cmsTemplateDao = cmsTemplateDao;
	}

}
