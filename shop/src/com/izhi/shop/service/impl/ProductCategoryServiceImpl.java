package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IProductCategoryDao;
import com.izhi.shop.model.ProductCategory;
import com.izhi.shop.service.IProductCategoryService;
@Service("productCategoryService")
public class ProductCategoryServiceImpl implements IProductCategoryService {

	@Resource(name="productCategoryDao")
	private IProductCategoryDao productCategoryDao;
	public IProductCategoryDao getProductCategoryDao() {
		return productCategoryDao;
	}

	public void setProductCategoryDao(IProductCategoryDao categoryDao) {
		this.productCategoryDao = categoryDao;
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public boolean deleteCategory(int id) {
		return productCategoryDao.deleteCategory(id);
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public boolean deleteCategories(List<Integer> ids) {
		return productCategoryDao.deleteCategorys(ids);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public ProductCategory findCategoryById(int id) {
		return productCategoryDao.findCategoryById(id);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<ProductCategory> findPage(PageParameter pp) {
		return productCategoryDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public int findTotalCount() {
		return productCategoryDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public int saveCategory(ProductCategory obj) {
		return productCategoryDao.saveCategory(obj);
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public boolean updateCategory(ProductCategory obj) {
		return productCategoryDao.updateCategory(obj);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<ProductCategory> findPage(PageParameter pp, int parentId) {
		return productCategoryDao.findPage(pp, parentId);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public int findTotalCount(int id) {
		return productCategoryDao.findTotalCount(id);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<ProductCategory> findTopAll() {
		return productCategoryDao.findTopAll();
	}

}
