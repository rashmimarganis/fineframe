package com.izhi.platform.dao;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao <T,Id extends Serializable> {
	T findById(Id id);
	Id save(T obj);
	void update(T obj);
	void saveOrUpdate(T obj);
	void delete(Id id);
	void delete(T obj);
	List<T> findPage(int firstResult,int maxResult,String sortField,String sort);
	int deleteAll();
	int delete(String ids,String id);
	int delete(String ids);
	List<T> find(String sql);
	List<T> find(String sql,Object obj);
	List<T> find(String sql,String[] keys,Object[] objs);
	boolean findIsExist(String name,Object obj);
	int findTotalCount();
	boolean findIsExist(Integer id);
}
