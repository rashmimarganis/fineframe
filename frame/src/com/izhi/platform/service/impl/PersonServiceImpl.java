package com.izhi.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IPersonDao;
import com.izhi.platform.model.Person;
import com.izhi.platform.service.IPersonService;
import com.izhi.platform.util.PageParameter;
@Service("personService")
public class PersonServiceImpl implements IPersonService{
	@Resource(name="personDao")
	private IPersonDao personDao;
	@Override
	public boolean deletePerson(int id) {
		return personDao.deletePerson(id);
	}

	@Override
	public boolean deletePersons(List<Integer> ids) {
		return personDao.deletePersons(ids);
	}

	@Override
	public Person findById(int id) {
		return personDao.findById(id);
	}

	@Override
	public Map<String, Object> findJsonById(int id) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", personDao.findJsonById(id));
		return map;
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, int orgId) {
		return personDao.findPage(pp, orgId);
	}

	@Override
	public int findTotalCount(int orgId) {
		return personDao.findTotalCount(orgId);
	}

	@Override
	public int savePerson(Person o) {
		int i=0;
		if(o!=null){
			if(o.getPersonId()==0){
				i=personDao.savePerson(o);
			}else{
				i=personDao.updatePerson(o);
			}
		}
		return i;
	}


	public IPersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(IPersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public List<Map<String, Object>> findPersons(Integer pid) {
		return personDao.findPersons(pid);
	}
	
	

}
