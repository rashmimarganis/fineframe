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
public class CategoryServiceImpl implements IProductCategoryService {

	@Resource(name="productCategoryDao")
	private IProductCategoryDao categoryDao;
	public IProductCategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(IProductCategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public boolean deleteCategory(int id) {
		return categoryDao.deleteCategory(id);
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public boolean deleteCategories(List<Integer> ids) {
		return categoryDao.deleteCategorys(ids);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public ProductCategory findCategoryById(int id) {
		return categoryDao.findCategoryById(id);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<ProductCategory> findPage(PageParameter pp) {
		return categoryDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public int findTotalCount() {
		return categoryDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public int saveCategory(ProductCategory obj) {
		return categoryDao.saveCategory(obj);
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public boolean updateCategory(ProductCategory obj) {
		return categoryDao.updateCategory(obj);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<ProductCategory> findPage(PageParameter pp, int parentId) {
		return categoryDao.findPage(pp, parentId);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public int findTotalCount(int id) {
		return categoryDao.findTotalCount(id);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<ProductCategory> findTopAll() {
		return categoryDao.findTopAll();
	}

}
