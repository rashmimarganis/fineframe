package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTagDao;
import com.izhi.cms.model.CmsTag;
import com.izhi.cms.service.ICmsTagService;
import com.izhi.platform.util.PageParameter;
@Service("cmsTagService")
public class CmsTagServiceImpl implements ICmsTagService{

	@Resource(name="cmsTagDao")
	private ICmsTagDao cmsTagDao;
	@Override
	public boolean deleteTag(int id) {
		return cmsTagDao.deleteTag(id);
	}

	@Override
	public boolean deleteTags(List<Integer> ids) {
		return cmsTagDao.deleteTags(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsTagDao.findJsonById(id);
	}

	@Override
	public CmsTag findTagById(int id) {
		return cmsTagDao.findTagById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsTagDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsTagDao.findTotalCount();
	}

	@Override
	public int saveTag(CmsTag obj) {
		if(obj!=null){
			if(obj.getTagId()==0){
				return cmsTagDao.saveTag(obj);
			}else{
				return cmsTagDao.updateTag(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateTag(CmsTag obj) {
		return cmsTagDao.updateTag(obj);
	}

	public ICmsTagDao getCmsTagDao() {
		return cmsTagDao;
	}

	public void setCmsTagDao(ICmsTagDao cmsTagDao) {
		this.cmsTagDao = cmsTagDao;
	}

}
