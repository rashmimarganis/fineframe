package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.ProductConfig;

public interface IProductConfigService {
	int saveProductConfig(ProductConfig obj);
	boolean updateProductConfig(ProductConfig obj);
	boolean deleteProductConfig(int id);
	boolean deleteProductConfigs(List<Integer> ids) ;
	ProductConfig findProductConfigById(int id);
	List<ProductConfig> findPage(PageParameter pp);
	int findTotalCount();
	ProductConfig findDefaultProductConfig();
	 boolean setDefaultConfig(int id);
}
