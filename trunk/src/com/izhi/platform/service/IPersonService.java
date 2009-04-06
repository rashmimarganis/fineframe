package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.PageParameter;
import com.izhi.platform.model.Person;

public interface IPersonService extends IBaseService<Person, Integer> {

	Map<String,Object> findPage(PageParameter pp);
	Map<String,Object> findPage(PageParameter pp,Org org);
	Map<String,Object> findPageBySort(PageParameter pp,Org org);
	Integer findTotalCount(Org org);
	Map<String,Object> delete(List<Integer> ids,Org org);
	
}
