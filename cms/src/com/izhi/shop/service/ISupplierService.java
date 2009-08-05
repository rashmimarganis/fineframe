package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Supplier;

public interface ISupplierService {
	int saveSupplier(Supplier obj);
	boolean updateSupplier(Supplier obj);
	boolean deleteSupplier(int id);
	boolean deleteSuppliers(List<Integer> ids) ;
	Supplier findSupplierById(int id);
	List<Supplier> findPage(PageParameter pp);
	int findTotalCount();
}
