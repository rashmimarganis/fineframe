package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Brand;

public interface IBrandDao  {
	int saveBrand(Brand obj);
	boolean updateBrand(Brand obj);
	boolean deleteBrand(int id);
	boolean deleteBrands(List<Integer> ids) ;
	Brand findBrandById(int id);
	List<Brand> findPage(PageParameter pp);
	List<Brand> findAll();
	int findTotalCount();
	
}
