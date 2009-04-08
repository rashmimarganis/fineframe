package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IProductConfigDao;
import com.izhi.shop.model.ProductConfig;
import com.izhi.shop.service.IProductConfigService;
@Service("productConfigService")
public class ProductConfigServiceImpl implements IProductConfigService {

	@Resource(name="productConfigDao")
	private IProductConfigDao productConfigDao;
	public IProductConfigDao getProductConfigDao() {
		return productConfigDao;
	}

	public void setProductConfigDao(IProductConfigDao productConfigDao) {
		this.productConfigDao = productConfigDao;
	}

	@Override
	@CacheFlush(modelId="productConfigFlushing")
	public boolean deleteProductConfig(int id) {
		return productConfigDao.deleteProductConfig(id);
	}

	@Override
	@CacheFlush(modelId="productConfigFlushing")
	public boolean deleteProductConfigs(List<Integer> ids) {
		return productConfigDao.deleteProductConfigs(ids);
	}

	@Override
	@Cacheable(modelId="productConfigCaching")
	public ProductConfig findProductConfigById(int id) {
		return productConfigDao.findProductConfigById(id);
	}

	@Override
	@Cacheable(modelId="productConfigCaching")
	public List<ProductConfig> findPage(PageParameter pp) {
		return productConfigDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="productConfigCaching")
	public int findTotalCount() {
		return productConfigDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="productConfigFlushing")
	public int saveProductConfig(ProductConfig obj) {
		return productConfigDao.saveProductConfig(obj);
	}

	@Override
	@CacheFlush(modelId="productConfigFlushing")
	public boolean updateProductConfig(ProductConfig obj) {
		return productConfigDao.updateProductConfig(obj);
	}

	@Override
	@Cacheable(modelId="productConfigCaching")
	public ProductConfig findDefaultProductConfig() {
		return productConfigDao.findDefaultProductConfig();
	}

	@Override
	@CacheFlush(modelId="productConfigFlushing")
	public boolean setDefaultConfig(int id) {
		return productConfigDao.setDefaultConfig(id);
	}

}
