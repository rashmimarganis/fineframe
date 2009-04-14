package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.ICategoryDao;
import com.izhi.shop.model.Category;
import com.izhi.shop.service.ICategoryService;
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

	@Resource(name="categoryDao")
	private ICategoryDao categoryDao;
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(ICategoryDao categoryDao) {
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
	public Category findCategoryById(int id) {
		return categoryDao.findCategoryById(id);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<Category> findPage(PageParameter pp) {
		return categoryDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public int findTotalCount() {
		return categoryDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public int saveCategory(Category obj) {
		return categoryDao.saveCategory(obj);
	}

	@Override
	@CacheFlush(modelId="categoryFlushing")
	public boolean updateCategory(Category obj) {
		return categoryDao.updateCategory(obj);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<Category> findPage(PageParameter pp, int parentId) {
		return categoryDao.findPage(pp, parentId);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public int findTotalCount(int id) {
		return categoryDao.findTotalCount(id);
	}

	@Override
	@Cacheable(modelId="categoryCaching")
	public List<Category> findTopAll() {
		return categoryDao.findTopAll();
	}

}
