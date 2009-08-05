package com.izhi.cms.dao;

import java.util.List;

import com.izhi.cms.model.Module;
import com.izhi.platform.util.PageParameter;

public interface IModuleDao {
	int saveModule(Module obj);
	boolean updateModule(Module obj);
	boolean deleteModule(int id);
	boolean deleteModules(List<Integer> ids) ;
	Module findModuleById(int id);
	Module findModuleByName(String name);
	List<Module> findPage(PageParameter pp);
	int findTotalCount();
}
