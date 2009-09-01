package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Person;
import com.izhi.platform.util.PageParameter;

public interface IPersonDao {
	List<Map<String,Object>>  findPage(PageParameter pp,int orgId);
	int findTotalCount(int orgId);
	Person findById(int id);
	List<Map<String,Object>> findJsonById(int id);
	int updatePerson(Person o);
	boolean deletePersons(List<Integer> ids);
	boolean deletePerson(int id);
	int savePerson(Person o);
}
