package com.izhi.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BaseAction;
import com.izhi.shop.service.ICategoryService;
import com.izhi.shop.service.IProductService;
import com.izhi.shop.service.IProductTypeService;
@Service
@Scope("prototype")
@Action(value="/index")
public class IndexAction extends BaseAction {
	private static final long serialVersionUID = -6428838565411508169L;
	private IProductTypeService productTypeService;
	private ICategoryService categoryService;
	private IProductService productService;
	
	public String execute(){
		
		return SUCCESS;
	}
	public IProductTypeService getProductTypeService() {
		return productTypeService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public ICategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
}
