package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.ISupplierDao;
import com.izhi.shop.model.Supplier;
import com.izhi.shop.service.ISupplierService;
@Service("supplierService")
public class SupplierServiceImpl implements ISupplierService {

	@Resource(name="supplierDao")
	private ISupplierDao supplierDao;
	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	@Override
	@CacheFlush(modelId="supplierFlushing")
	public boolean deleteSupplier(int id) {
		return supplierDao.deleteSupplier(id);
	}

	@Override
	@CacheFlush(modelId="supplierFlushing")
	public boolean deleteSuppliers(List<Integer> ids) {
		return supplierDao.deleteSuppliers(ids);
	}

	@Override
	@Cacheable(modelId="supplierCaching")
	public Supplier findSupplierById(int id) {
		return supplierDao.findSupplierById(id);
	}

	@Override
	@Cacheable(modelId="supplierCaching")
	public List<Supplier> findPage(PageParameter pp) {
		return supplierDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="supplierCaching")
	public int findTotalCount() {
		return supplierDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="supplierFlushing")
	public int saveSupplier(Supplier obj) {
		return supplierDao.saveSupplier(obj);
	}

	@Override
	@CacheFlush(modelId="supplierFlushing")
	public boolean updateSupplier(Supplier obj) {
		return supplierDao.updateSupplier(obj);
	}

}
