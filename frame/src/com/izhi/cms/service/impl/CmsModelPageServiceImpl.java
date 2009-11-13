package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsModelPageDao;
import com.izhi.cms.model.CmsModelPage;
import com.izhi.cms.service.ICmsModelPageService;
import com.izhi.platform.util.PageParameter;
@Service("cmsModelPageService")
public class CmsModelPageServiceImpl implements ICmsModelPageService{

	@Resource(name="cmsModelPageDao")
	private ICmsModelPageDao cmsModelPageDao;
	@Override
	public boolean deleteModelPage(int id) {
		return cmsModelPageDao.deleteModelPage(id);
	}

	@Override
	public boolean deleteModelPages(List<Integer> ids) {
		return cmsModelPageDao.deleteModelPages(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsModelPageDao.findJsonById(id);
	}

	@Override
	public CmsModelPage findModelPageById(int id) {
		return cmsModelPageDao.findModelPageById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp,int modelId) {
		return cmsModelPageDao.findPage(pp,modelId);
	}

	@Override
	public int findTotalCount(int modelId) {
		return cmsModelPageDao.findTotalCount(modelId);
	}

	@Override
	public int saveModelPage(CmsModelPage obj) {
		if(obj!=null){
			if(obj.getPageId()==0){
				return cmsModelPageDao.saveModelPage(obj);
			}else{
				return cmsModelPageDao.updateModelPage(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateModelPage(CmsModelPage obj) {
		return cmsModelPageDao.updateModelPage(obj);
	}

	public ICmsModelPageDao getCmsModelPageDao() {
		return cmsModelPageDao;
	}

	public void setCmsModelPageDao(ICmsModelPageDao cmsModelPageDao) {
		this.cmsModelPageDao = cmsModelPageDao;
	}

	@Override
	public List<Map<String, Object>> findAll(int modelId) {
		return cmsModelPageDao.findAll(modelId);
	}

}
