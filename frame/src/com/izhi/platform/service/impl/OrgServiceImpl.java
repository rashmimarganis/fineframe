package com.izhi.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IOrgDao;
import com.izhi.platform.model.Org;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.IOrgService;
import com.izhi.platform.util.PageParameter;

@Service("orgService")
public class OrgServiceImpl extends BaseService implements IOrgService {
	@Resource(name = "orgDao")
	private IOrgDao orgDao;

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void delete(Integer id) {
		this.delete(this.findById(id));
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void delete(Org obj) {
		orgDao.delete(obj);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public int delete(String ids, String id) {
		return orgDao.delete(ids, id);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public int delete(String ids) {
		return this.delete(ids, "id");
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void deleteAll() {
		orgDao.deleteAll();
	}

	@Override
	@Cacheable(modelId = "orgCaching")
	public Org findById(Integer id) {
		if (orgDao.findIsExist(id)) {
			return orgDao.findById(id);
		} else {
			return new Org();
		}
	}

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public List<Org> findPage(int firstResult, int maxResult, String sortField,
			String sort) {
		return orgDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public Integer save(Org obj) {
		return null;
	}

	public IOrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(IOrgDao dao) {
		this.orgDao = dao;
	}

	

	@Override
	
	@Cacheable(modelId = "orgCaching")
	public List<Org> findChildren(Integer id) {
		List<Org> list = orgDao.findChildren(id);
		return list;
	}


	@Override
	@CacheFlush(modelId = "orgFlushing")
	public Map<String,Object> saveOrg(Org obj) {
		int i=0;
		String action="add";
		if(obj.getOrgId()==0){
			i=orgDao.saveOrg(obj);
		}else{
			i=orgDao.updateOrg(obj);
			action="update";
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", i);
		map.put("action", action);
		map.put("success", i>0);
		return map;
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public void update(Org obj) {
		orgDao.update(obj);

	}

	@Override
	public List<Org> find(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Org> find(String sql, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Org> find(String sql, String[] keys, Object[] objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Cacheable(modelId = "orgCaching")
	public int findTotalCount() {
		return orgDao.findTotalCount();
	}


	@Override
	@Cacheable(modelId = "orgCaching")
	public int findTotalCount(int parentId) {
		return orgDao.findTotalCount(parentId);
	}

	@Override
	@Cacheable(modelId = "orgCaching")
	public List<Org> findPage(PageParameter pp) {
		return orgDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId = "orgCaching")
	public List<Org> findPage(PageParameter pp, int parentId) {
		return orgDao.findPage(pp, parentId);
	}

	@Override	
	@Cacheable(modelId = "orgCaching")
	public List<Org> findTopPage(PageParameter pp) {
		return orgDao.findTopPages(pp);
	}

	@Override
	@Cacheable(modelId = "orgCaching")
	public int findTopTotalCount() {
		return orgDao.findTopTotalCount();
	}

	@Override
	@Cacheable(modelId = "orgCaching")
	public boolean findExist(String name, String oname) {
		return orgDao.findExist(name, oname);
	}

	@Override
	@CacheFlush(modelId = "orgFlushing")
	public boolean deleteOrgs(List<Integer> ids) {
		return orgDao.deleteOrgs(ids);
	}

	@Override
	public List<Map<String, Object>> findOrgs(Integer pid) {
		return orgDao.findOrgs(pid);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return orgDao.findJsonById(id);
	}

	@Override
	public Integer save(Org obj, String oldName) {
		// TODO Auto-generated method stub
		return null;
	}

}
