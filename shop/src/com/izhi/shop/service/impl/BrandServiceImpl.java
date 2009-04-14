package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IBrandDao;
import com.izhi.shop.model.Brand;
import com.izhi.shop.service.IBrandService;
@Service("brandService")
public class BrandServiceImpl implements IBrandService {

	@Resource(name="brandDao")
	private IBrandDao brandDao;
	public IBrandDao getBrandDao() {
		return brandDao;
	}

	public void setBrandDao(IBrandDao brandDao) {
		this.brandDao = brandDao;
	}

	@Override
	@CacheFlush(modelId="brandFlushing")
	public boolean deleteBrand(int id) {
		return brandDao.deleteBrand(id);
	}

	@Override
	@CacheFlush(modelId="brandFlushing")
	public boolean deleteBrands(List<Integer> ids) {
		return brandDao.deleteBrands(ids);
	}

	@Override
	@Cacheable(modelId="brandCaching")
	public Brand findBrandById(int id) {
		return brandDao.findBrandById(id);
	}

	@Override
	@Cacheable(modelId="brandCaching")
	public List<Brand> findPage(PageParameter pp) {
		return brandDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="brandCaching")
	public int findTotalCount() {
		return brandDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="brandFlushing")
	public int saveBrand(Brand obj) {
		return brandDao.saveBrand(obj);
	}

	@Override
	@CacheFlush(modelId="brandFlushing")
	public boolean updateBrand(Brand obj) {
		return brandDao.updateBrand(obj);
	}

	@Override
	public List<Brand> findAll() {
		return brandDao.findAll();
	}

}
