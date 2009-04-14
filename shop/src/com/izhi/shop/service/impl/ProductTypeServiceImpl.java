package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IProductTypeDao;
import com.izhi.shop.model.ProductType;
import com.izhi.shop.service.IProductTypeService;
@Service("productTypeService")
public class ProductTypeServiceImpl implements IProductTypeService {

	@Resource(name="productTypeDao")
	private IProductTypeDao productTypeDao;
	public IProductTypeDao getProductTypeDao() {
		return productTypeDao;
	}

	public void setProductTypeDao(IProductTypeDao productTypeDao) {
		this.productTypeDao = productTypeDao;
	}

	@Override
	@CacheFlush(modelId="productTypeFlushing")
	public boolean deleteProductType(int id) {
		return productTypeDao.deleteProductType(id);
	}

	@Override
	@CacheFlush(modelId="productTypeFlushing")
	public boolean deleteProductTypes(List<Integer> ids) {
		return productTypeDao.deleteProductTypes(ids);
	}

	@Override
	@Cacheable(modelId="productTypeCaching")
	public ProductType findProductTypeById(int id) {
		return productTypeDao.findProductTypeById(id);
	}

	@Override
	@Cacheable(modelId="productTypeCaching")
	public List<ProductType> findPage(PageParameter pp) {
		return productTypeDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="productTypeCaching")
	public int findTotalCount() {
		return productTypeDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="productTypeFlushing")
	public int saveProductType(ProductType obj) {
		return productTypeDao.saveProductType(obj);
	}

	@Override
	@CacheFlush(modelId="productTypeFlushing")
	public boolean updateProductType(ProductType obj) {
		return productTypeDao.updateProductType(obj);
	}

	@Override
	@Cacheable(modelId="productTypeCaching")
	public List<ProductType> findPage(PageParameter pp, int parentId) {
		return productTypeDao.findPage(pp, parentId);
	}

	@Override
	@Cacheable(modelId="productTypeCaching")
	public int findTotalCount(int id) {
		return productTypeDao.findTotalCount(id);
	}

	@Override
	@Cacheable(modelId="productTypeCaching")
	public List<ProductType> findAll() {
		return productTypeDao.findAll();
	}

}
