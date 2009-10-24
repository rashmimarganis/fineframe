package com.izhi.cms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsCategoryDao;
import com.izhi.cms.model.CmsCategory;
import com.izhi.cms.service.ICmsCategoryService;
import com.izhi.cms.service.ICmsSiteService;
@Service("cmsCategoryService")
public class CmsCategoryServiceImpl implements ICmsCategoryService{

	@Resource(name="cmsSiteService")
	private ICmsSiteService cmsSiteService;
	@Resource(name="cmsCategoryDao")
	private ICmsCategoryDao categoryDao;
	@Override
	public boolean deleteCategory(int id) {
		return categoryDao.deleteCategory(id);
	}

	@Override
	public boolean deleteCategorys(List<Integer> ids) {
		return categoryDao.deleteCategorys(ids);
	}

	@Override
	public List<Map<String, Object>> findAll(int sid,int id) {
		return categoryDao.findAll(sid,id);
	}

	@Override
	public List<Map<String, Object>> findCategory(int cid) {
		return categoryDao.findCategory(cid);
	}

	@Override
	public CmsCategory findCategoryById(int id) {
		return categoryDao.findCategoryById(id);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return categoryDao.findJsonById(id);
	}

	@Override
	public int findTotalCount() {
		return categoryDao.findTotalCount();
	}

	@Override
	public int findTotalCount(int cid) {
		return categoryDao.findTotalCount(cid);
	}

	@Override
	public int saveCategory(CmsCategory obj) {
		int id=0;
		if(obj!=null){
			int _id=obj.getCategoryId();
			if(_id==0){
				id=categoryDao.saveCategory(obj);
			}else{
				id=categoryDao.updateCategory(obj);
			}
		}
		return id;
	}

	@Override
	public int updateCategory(CmsCategory obj) {
		return categoryDao.updateCategory(obj);
	}

	public ICmsCategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(ICmsCategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public List<Map<String, Object>> findAll() {
		List<Map<String,Object>> list=cmsSiteService.findAll();
		for(Map<String,Object> m:list){
			int id=(Integer)m.get("id");
			List<Map<String,Object>> children=categoryDao.findAll(id, 0);
			m.put("children", children);
			m.put("leaf", children.size()==0);
			m.put("id", "s_"+id);
		}
		return list;
	}

	public ICmsSiteService getCmsSiteService() {
		return cmsSiteService;
	}

	public void setCmsSiteService(ICmsSiteService cmsSiteService) {
		this.cmsSiteService = cmsSiteService;
	}

}
