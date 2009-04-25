package com.izhi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.cms.dao.IDataModelDao;
import com.izhi.cms.model.DataModel;
import com.izhi.cms.service.IDataModelService;
import com.izhi.platform.util.PageParameter;
@Service("dataModelService")
public class DataModelServiceImpl implements IDataModelService {

	@Resource(name="dataModelDao")
	private IDataModelDao dataModelDao;
	public IDataModelDao getDataModelDao() {
		return dataModelDao;
	}

	public void setDataModelDao(IDataModelDao dataModelDao) {
		this.dataModelDao = dataModelDao;
	}

	@Override
	@CacheFlush(modelId="dataModelFlushing")
	public boolean deleteDataModel(int id) {
		return dataModelDao.deleteDataModel(id);
	}

	@Override
	@CacheFlush(modelId="dataModelFlushing")
	public boolean deleteDataModels(List<Integer> ids) {
		return dataModelDao.deleteDataModels(ids);
	}

	@Override
	@Cacheable(modelId="dataModelCaching")
	public DataModel findDataModelById(int id) {
		return dataModelDao.findDataModelById(id);
	}

	@Override
	@Cacheable(modelId="dataModelCaching")
	public List<DataModel> findPage(PageParameter pp) {
		return dataModelDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="dataModelCaching")
	public int findTotalCount() {
		return dataModelDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="dataModelFlushing")
	public int saveDataModel(DataModel obj) {
		return dataModelDao.saveDataModel(obj);
	}

	@Override
	@CacheFlush(modelId="dataModelFlushing")
	public boolean updateDataModel(DataModel obj) {
		return dataModelDao.updateDataModel(obj);
	}

	@Override
	public DataModel findDataModelByName(String name) {
		return dataModelDao.findDataModelByName(name);
	}

}
