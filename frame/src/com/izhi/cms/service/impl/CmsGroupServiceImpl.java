package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsGroupDao;
import com.izhi.cms.model.CmsGroup;
import com.izhi.cms.service.ICmsGroupService;
import com.izhi.platform.util.PageParameter;
@Service("cmsGroupService")
public class CmsGroupServiceImpl implements ICmsGroupService{

	@Resource(name="cmsGroupDao")
	private ICmsGroupDao groupDao;
	@Override
	public boolean deleteGroup(Integer id) {
		return groupDao.deleteGroup(id);
	}

	@Override
	public boolean deleteGroups(List<Integer> ids) {
		return groupDao.deleteGroups(ids);
	}

	@Override
	public CmsGroup findById(Integer id) {
		return groupDao.findObjById(id);
	}

	@Override
	public CmsGroup findObjById(int id) {
		return groupDao.findObjById(id);
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		return groupDao.findPage(pp);
	}

	@Override
	public Integer findTotalCount() {
		return groupDao.findTotalCount();
	}

	@Override
	public int saveGroup(CmsGroup obj) {
		if(obj!=null){
			if(obj.getGroupId()==0){
				return groupDao.saveGroup(obj);
			}else{
				return groupDao.updateGroup(obj);
			}
		}
		return 0;
	}

	public ICmsGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(ICmsGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return groupDao.findJsonById(id);
	}

}
