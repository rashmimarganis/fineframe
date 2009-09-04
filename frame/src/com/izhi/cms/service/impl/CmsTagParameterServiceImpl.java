package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTagParameterDao;
import com.izhi.cms.model.CmsTagParameter;
import com.izhi.cms.service.ICmsTagParameterService;
import com.izhi.platform.util.PageParameter;
@Service("cmsTagParameterService")
public class CmsTagParameterServiceImpl implements ICmsTagParameterService{

	@Resource(name="cmsTagParameterDao")
	private ICmsTagParameterDao cmsTagParameterDao;
	@Override
	public boolean deleteTagParameter(int id) {
		return cmsTagParameterDao.deleteTagParameter(id);
	}

	@Override
	public boolean deleteTagParameters(List<Integer> ids) {
		return cmsTagParameterDao.deleteTagParameters(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsTagParameterDao.findJsonById(id);
	}

	@Override
	public CmsTagParameter findTagParameterById(int id) {
		return cmsTagParameterDao.findTagParameterById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp,int tagId) {
		return cmsTagParameterDao.findPage(pp,tagId);
	}

	@Override
	public int findTotalCount(int tagId) {
		return cmsTagParameterDao.findTotalCount(tagId);
	}

	@Override
	public int saveTagParameter(CmsTagParameter obj) {
		if(obj!=null){
			if(obj.getParameterId()==0){
				return cmsTagParameterDao.saveTagParameter(obj);
			}else{
				return cmsTagParameterDao.updateTagParameter(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateTagParameter(CmsTagParameter obj) {
		return cmsTagParameterDao.updateTagParameter(obj);
	}

	public ICmsTagParameterDao getCmsTagParameterDao() {
		return cmsTagParameterDao;
	}

	public void setCmsTagParameterDao(ICmsTagParameterDao cmsTagParameterDao) {
		this.cmsTagParameterDao = cmsTagParameterDao;
	}

}
