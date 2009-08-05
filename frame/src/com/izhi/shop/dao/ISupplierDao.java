package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Supplier;

public interface ISupplierDao  {
	int saveSupplier(Supplier obj);
	boolean updateSupplier(Supplier obj);
	boolean deleteSupplier(int id);
	boolean deleteSuppliers(List<Integer> ids) ;
	Supplier findSupplierById(int id);
	List<Supplier> findPage(PageParameter pp);
	int findTotalCount();
	
}
