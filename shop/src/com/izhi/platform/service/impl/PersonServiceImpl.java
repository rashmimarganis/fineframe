package com.izhi.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IPersonDao;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.PageParameter;
import com.izhi.platform.model.Person;
import com.izhi.platform.service.IPersonService;
import com.izhi.platform.service.IUserService;
@Service("personService")
public class PersonServiceImpl 	implements IPersonService {
	@Resource(name="personDao")
	private IPersonDao personDao;
	@Resource(name="userService")
	private IUserService userService;

	@Override
	public void delete(Integer id) {
		personDao.delete(id);
	}

	@Override
	public void delete(Person obj) {
		personDao.delete(obj);

	}

	@Override
	public int delete(String ids, String id) {
		return personDao.delete(ids, id);

	}

	@Override
	public int delete(String ids) {
		return personDao.delete(ids);

	}

	@Override
	public void deleteAll() {
		personDao.deleteAll();

	}

	@Override
	@Cacheable(modelId="personCaching")
	public List<Person> find(String sql) {
		return personDao.find(sql);
	}

	@Override
	@Cacheable(modelId="personCaching")
	public List<Person> find(String sql, Object obj) {
		return personDao.find(sql, obj);
	}

	@Override
	@Cacheable(modelId="personCaching")
	public List<Person> find(String sql, String[] keys, Object[] objs) {
		return personDao.find(sql, keys, objs);
	}

	@Override
	@Cacheable(modelId="personCaching")
	public Person findById(Integer id) {
		return personDao.findById(id);
	}

	@Override
	@Cacheable(modelId="personCaching")
	public List<Person> findPage(int firstResult, int maxResult,
			String sortField, String sort) {
		return personDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	
	public Integer save(Person obj) {
		if (obj.getId() == 0) {
			return personDao.save(obj);
		} else {
			personDao.update(obj);
			return 1;
		}
	}

	@Override
	public Integer save(Person obj, String oldName) {
		return null;
	}


	public IPersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(IPersonDao dao) {
		this.personDao = dao;
	}

	@Override
	@Cacheable(modelId="personCaching")
	public Map<String, Object> findPage(PageParameter pp) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (pp != null) {
			Integer firstResult = pp.getStart();
			Integer maxResult = pp.getLimit();
			String sortField = pp.getSort();
			String sort = pp.getDir();
			List<Person> lp = personDao.findPage(firstResult, maxResult, sortField,
					sort);
			Integer tc = personDao.findTotalCount();
			map.put("totalCount", tc);
			map.put("objs", lp);
		}
		return map;
	}


	@Override
	@Cacheable(modelId="personCaching")
	public Integer findTotalCount(Org org) {
		return personDao.findTotalCount(org);
	}

	@Override
	@Cacheable(modelId="personCaching")
	public Map<String, Object> findPage(PageParameter pp, Org org) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (pp != null) {
			List<Person> lp = personDao.findPage(pp, org);
			Integer tc = this.findTotalCount(org);
			map.put("totalCount", tc);
			map.put("objs", lp);
		}
		return map;
	}
	@Override
	
	@Cacheable(modelId = "personCaching")
	public Map<String, Object> findPageBySort(PageParameter pp, Org org) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (pp != null) {
			List<Person> lp = personDao.findPage(pp, org);
			Integer tc = this.findTotalCount(org);
			map.put("totalCount", tc);
			map.put("objs", lp);
		}
		return map;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	
	public Map<String, Object> delete(List<Integer> ids, Org org) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean successed = false;
		for (Integer id : ids) {
			/*if(userService.findById(id)!=null){
				userService.delete(id);
			}*/
			personDao.delete(id);
			successed = true;
		}

		map.put("success", successed);
		map.put("totalCount", this.findTotalCount(org));
		return map;

	}

	@Override
	@CacheFlush(modelId="personFlushing")
	public void update(Person obj) {
		personDao.update(obj);
	}

}
