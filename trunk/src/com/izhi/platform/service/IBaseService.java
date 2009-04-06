package com.izhi.platform.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T,Id extends Serializable> {
	T findById(Id id);
	Id save(T obj);
	void delete(Id id);
	void delete(T obj);
	List<T> findPage(int firstResult,int maxResult,String sortField,String sort);
	void deleteAll();
	int delete(String ids,String id);
	int delete(String ids);
	List<T> find(String sql);
	List<T> find(String sql,Object obj);
	List<T> find(String sql,String[] keys,Object[] objs);
	Integer save(T obj,String oldName) ;
	void update(T obj);
}
