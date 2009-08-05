package com.izhi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.cms.dao.IModuleDao;
import com.izhi.cms.model.Module;
import com.izhi.cms.service.IModuleService;
import com.izhi.platform.util.PageParameter;
@Service("moduleService")
public class ModuleServiceImpl implements IModuleService {

	@Resource(name="moduleDao")
	private IModuleDao moduleDao;
	public IModuleDao getModuleDao() {
		return moduleDao;
	}

	public void setModuleDao(IModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	@Override
	@CacheFlush(modelId="moduleFlushing")
	public boolean deleteModule(int id) {
		return moduleDao.deleteModule(id);
	}

	@Override
	@CacheFlush(modelId="moduleFlushing")
	public boolean deleteModules(List<Integer> ids) {
		return moduleDao.deleteModules(ids);
	}

	@Override
	@Cacheable(modelId="moduleCaching")
	public Module findModuleById(int id) {
		return moduleDao.findModuleById(id);
	}

	@Override
	@Cacheable(modelId="moduleCaching")
	public List<Module> findPage(PageParameter pp) {
		return moduleDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="moduleCaching")
	public int findTotalCount() {
		return moduleDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="moduleFlushing")
	public int saveModule(Module obj) {
		return moduleDao.saveModule(obj);
	}

	@Override
	@CacheFlush(modelId="moduleFlushing")
	public boolean updateModule(Module obj) {
		return moduleDao.updateModule(obj);
	}

	@Override
	public Module findModuleByName(String name) {
		return moduleDao.findModuleByName(name);
	}

}
