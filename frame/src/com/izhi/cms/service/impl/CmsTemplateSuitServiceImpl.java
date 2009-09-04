package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTemplateSuitDao;
import com.izhi.cms.model.CmsTemplateSuit;
import com.izhi.cms.service.ICmsTemplateSuitService;
import com.izhi.platform.util.PageParameter;
@Service("cmsTemplateSuitService")
public class CmsTemplateSuitServiceImpl implements ICmsTemplateSuitService{

	@Resource(name="cmsTemplateSuitDao")
	private ICmsTemplateSuitDao cmsTemplateSuitDao;
	@Override
	public boolean deleteSuit(int id) {
		return cmsTemplateSuitDao.deleteSuit(id);
	}

	@Override
	public boolean deleteSuits(List<Integer> ids) {
		return cmsTemplateSuitDao.deleteSuits(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsTemplateSuitDao.findJsonById(id);
	}

	@Override
	public CmsTemplateSuit findSuitById(int id) {
		return cmsTemplateSuitDao.findSuitById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsTemplateSuitDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsTemplateSuitDao.findTotalCount();
	}

	@Override
	public int saveSuit(CmsTemplateSuit obj) {
		if(obj!=null){
			if(obj.getSuitId()==0){
				return cmsTemplateSuitDao.saveSuit(obj);
			}else{
				return cmsTemplateSuitDao.updateSuit(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateSuit(CmsTemplateSuit obj) {
		return cmsTemplateSuitDao.updateSuit(obj);
	}

	public ICmsTemplateSuitDao getCmsTemplateSuitDao() {
		return cmsTemplateSuitDao;
	}

	public void setCmsTemplateSuitDao(ICmsTemplateSuitDao cmsTemplateSuitDao) {
		this.cmsTemplateSuitDao = cmsTemplateSuitDao;
	}

}
