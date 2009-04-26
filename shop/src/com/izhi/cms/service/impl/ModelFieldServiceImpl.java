package com.izhi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.cms.dao.IModelFieldDao;
import com.izhi.cms.model.ModelField;
import com.izhi.cms.service.IModelFieldService;
import com.izhi.platform.util.PageParameter;
@Service("dataModelFieldService")
public class ModelFieldServiceImpl implements IModelFieldService {

	@Resource(name="modelFieldDao")
	private IModelFieldDao modelFieldDao;
	public IModelFieldDao getModelFieldDao() {
		return modelFieldDao;
	}

	public void setModelFieldDao(IModelFieldDao modelFieldDao) {
		this.modelFieldDao = modelFieldDao;
	}

	@Override
	@CacheFlush(modelId="modelFieldFlushing")
	public boolean deleteModelField(int id) {
		return modelFieldDao.deleteModelField(id);
	}

	@Override
	@CacheFlush(modelId="modelFieldFlushing")
	public boolean deleteModelFields(List<Integer> ids) {
		return modelFieldDao.deleteModelFields(ids);
	}

	@Override
	@Cacheable(modelId="modelFieldCaching")
	public ModelField findModelFieldById(int id) {
		return modelFieldDao.findModelFieldById(id);
	}

	@Override
	@Cacheable(modelId="modelFieldCaching")
	public List<ModelField> findPage(PageParameter pp) {
		return modelFieldDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="modelFieldCaching")
	public int findTotalCount() {
		return modelFieldDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="modelFieldFlushing")
	public int saveModelField(ModelField obj) {
		return modelFieldDao.saveModelField(obj);
	}

	@Override
	@CacheFlush(modelId="modelFieldFlushing")
	public boolean updateModelField(ModelField obj) {
		return modelFieldDao.updateModelField(obj);
	}

	@Override
	public ModelField findModelFieldByName(String name) {
		return modelFieldDao.findModelFieldByName(name);
	}

}
