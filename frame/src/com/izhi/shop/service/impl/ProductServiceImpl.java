package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IProductDao;
import com.izhi.shop.model.Product;
import com.izhi.shop.service.IProductService;
@Service("productService")
public class ProductServiceImpl implements IProductService {

	@Resource(name="productDao")
	private IProductDao productDao;
	public IProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	@CacheFlush(modelId="productFlushing")
	public boolean deleteProduct(int id) {
		return productDao.deleteProduct(id);
	}

	@Override
	@CacheFlush(modelId="productFlushing")
	public boolean deleteProducts(List<Integer> ids) {
		return productDao.deleteProducts(ids);
	}

	@Override
	@Cacheable(modelId="productCaching")
	public Product findProductById(int id) {
		return productDao.findProductById(id);
	}

	@Override
	@Cacheable(modelId="productCaching")
	public List<Product> findPage(PageParameter pp) {
		return productDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="productCaching")
	public int findTotalCount() {
		return productDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="productFlushing")
	public int saveProduct(Product obj) {
		return productDao.saveProduct(obj);
	}

	@Override
	@CacheFlush(modelId="productFlushing")
	public boolean updateProduct(Product obj) {
		return productDao.updateProduct(obj);
	}

	@Override
	@Cacheable(modelId="productCaching")
	public List<Product> findPageByType(PageParameter pp, int parentId) {
		return productDao.findPageByType(pp, parentId);
	}

	@Override
	@Cacheable(modelId="productCaching")
	public int findTotalCountByType(int id) {
		return productDao.findTotalCountByType(id);
	}

	@Override
	@Cacheable(modelId="productCaching")
	public List<Product> findPageByCategory(PageParameter pp, int catId) {
		return productDao.findPageByType(pp, catId);
	}

	@Override
	@Cacheable(modelId="productCaching")
	public int findTotalCountByCategory(int catId) {
		return productDao.findTotalCountByCategory(catId);
	}

	@Override
	@CacheFlush(modelId="productFlushing")
	public boolean onlineProducts(List<Integer> ids, boolean online) {
		return productDao.onlineProducts(ids, online);
	}

}
