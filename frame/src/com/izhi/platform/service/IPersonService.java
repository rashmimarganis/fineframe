package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Person;
import com.izhi.platform.util.PageParameter;

public interface IPersonService {
	List<Map<String,Object>>  findPage(PageParameter pp,int orgId);
	int findTotalCount(int orgId);
	Person findById(int id);
	Map<String,Object> findJsonById(int id);
	boolean deletePersons(List<Integer> ids);
	boolean deletePerson(int id);
	int savePerson(Person o);
	List<Map<String, Object>> findPersons(Integer pid);
}
