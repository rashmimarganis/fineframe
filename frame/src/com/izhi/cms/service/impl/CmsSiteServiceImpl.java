package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsSiteDao;
import com.izhi.cms.model.CmsSite;
import com.izhi.cms.service.ICmsSiteService;
import com.izhi.platform.util.PageParameter;
@Service("cmsSiteService")
public class CmsSiteServiceImpl implements ICmsSiteService{

	@Resource(name="cmsSiteDao")
	private ICmsSiteDao cmsSiteDao;
	@Override
	public boolean deleteSite(int id) {
		return cmsSiteDao.deleteSite(id);
	}

	@Override
	public boolean deleteSites(List<Integer> ids) {
		return cmsSiteDao.deleteSites(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsSiteDao.findJsonById(id);
	}

	@Override
	public CmsSite findSiteById(int id) {
		return cmsSiteDao.findSiteById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsSiteDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsSiteDao.findTotalCount();
	}

	@Override
	public int saveSite(CmsSite obj) {
		if(obj!=null){
			if(obj.getSiteId()==0){
				return cmsSiteDao.saveSite(obj);
			}else{
				return cmsSiteDao.updateSite(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateSite(CmsSite obj) {
		return cmsSiteDao.updateSite(obj);
	}

	public ICmsSiteDao getCmsSiteDao() {
		return cmsSiteDao;
	}

	public void setCmsSiteDao(ICmsSiteDao cmsSiteDao) {
		this.cmsSiteDao = cmsSiteDao;
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return cmsSiteDao.findAll();
	}

}
