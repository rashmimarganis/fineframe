package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsAttributeDao;
import com.izhi.cms.model.CmsAttribute;
import com.izhi.cms.service.ICmsAttributeService;
import com.izhi.platform.util.PageParameter;
@Service("cmsAttributeService")
public class CmsAttributeServiceImpl implements ICmsAttributeService{

	@Resource(name="cmsAttributeDao")
	private ICmsAttributeDao cmsAttributeDao;
	@Override
	public boolean deleteAttribute(int id) {
		return cmsAttributeDao.deleteAttribute(id);
	}

	@Override
	public boolean deleteAttributes(List<Integer> ids) {
		return cmsAttributeDao.deleteAttributes(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsAttributeDao.findJsonById(id);
	}

	@Override
	public CmsAttribute findAttributeById(int id) {
		return cmsAttributeDao.findAttributeById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsAttributeDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsAttributeDao.findTotalCount();
	}

	@Override
	public int saveAttribute(CmsAttribute obj) {
		if(obj!=null){
			if(obj.getAttributeId()==0){
				return cmsAttributeDao.saveAttribute(obj);
			}else{
				return cmsAttributeDao.updateAttribute(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateAttribute(CmsAttribute obj) {
		return cmsAttributeDao.updateAttribute(obj);
	}

	public ICmsAttributeDao getCmsAttributeDao() {
		return cmsAttributeDao;
	}

	public void setCmsAttributeDao(ICmsAttributeDao cmsAttributeDao) {
		this.cmsAttributeDao = cmsAttributeDao;
	}

}
