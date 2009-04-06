package com.izhi.platform.dao;

import java.util.List;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.PageParameter;
import com.izhi.platform.model.Person;

public interface IPersonDao extends IBaseDao<Person, Integer> {
	List<Person> findPage(PageParameter pp,Org org);
	List<Person> findPageBySort(PageParameter pp,Org org);
	int findTotalCount(Org org);
	void update(Person p);
	
	

}
