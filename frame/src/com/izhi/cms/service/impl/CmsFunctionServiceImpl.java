package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsFunctionDao;
import com.izhi.cms.model.CmsFunction;
import com.izhi.cms.service.ICmsFunctionService;
import com.izhi.platform.util.PageParameter;
@Service("cmsFunctionService")
public class CmsFunctionServiceImpl implements ICmsFunctionService{

	@Resource(name="cmsFunctionDao")
	private ICmsFunctionDao cmsFunctionDao;
	@Override
	public boolean deleteFunction(int id) {
		return cmsFunctionDao.deleteFunction(id);
	}

	@Override
	public boolean deleteFunctions(List<Integer> ids) {
		return cmsFunctionDao.deleteFunctions(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsFunctionDao.findJsonById(id);
	}

	@Override
	public CmsFunction findFunctionById(int id) {
		return cmsFunctionDao.findFunctionById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsFunctionDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsFunctionDao.findTotalCount();
	}

	@Override
	public int saveFunction(CmsFunction obj) {
		if(obj!=null){
			if(obj.getFunctionId()==0){
				return cmsFunctionDao.saveFunction(obj);
			}else{
				return cmsFunctionDao.updateFunction(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateFunction(CmsFunction obj) {
		return cmsFunctionDao.updateFunction(obj);
	}

	public ICmsFunctionDao getCmsFunctionDao() {
		return cmsFunctionDao;
	}

	public void setCmsFunctionDao(ICmsFunctionDao cmsFunctionDao) {
		this.cmsFunctionDao = cmsFunctionDao;
	}

}
