package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsModelDao;
import com.izhi.cms.model.CmsModel;
import com.izhi.cms.service.ICmsModelService;
import com.izhi.platform.util.PageParameter;
@Service("cmsModelService")
public class CmsModelServiceImpl implements ICmsModelService{

	@Resource(name="cmsModelDao")
	private ICmsModelDao cmsModelDao;
	@Override
	public boolean deleteModel(int id) {
		return cmsModelDao.deleteModel(id);
	}

	@Override
	public boolean deleteModels(List<Integer> ids) {
		return cmsModelDao.deleteModels(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsModelDao.findJsonById(id);
	}

	@Override
	public CmsModel findModelById(int id) {
		return cmsModelDao.findModelById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsModelDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsModelDao.findTotalCount();
	}

	@Override
	public int saveModel(CmsModel obj) {
		if(obj!=null){
			if(obj.getModelId()==0){
				return cmsModelDao.saveModel(obj);
			}else{
				return cmsModelDao.updateModel(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateModel(CmsModel obj) {
		return cmsModelDao.updateModel(obj);
	}

	public ICmsModelDao getCmsModelDao() {
		return cmsModelDao;
	}

	public void setCmsModelDao(ICmsModelDao cmsModelDao) {
		this.cmsModelDao = cmsModelDao;
	}

	@Override
	public List<Map<String, Object>> findTree() {
		return cmsModelDao.findTree();
	}

}
