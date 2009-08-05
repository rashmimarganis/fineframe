package com.izhi.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.ProductConfig;
import com.izhi.shop.service.IProductConfigService;
@Service
@Scope(value="prototype")
@Namespace("/productconfig")
public class ProductConfigAction extends BasePageAction{

	private static final long serialVersionUID = 4874920215042349011L;
	@Resource(name="productConfigService")
	private IProductConfigService productConfigService;
	private ProductConfig obj;
	private List<Integer> ids;
	
	private int id;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)productConfigService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("productConfigId");
		pp.setDir("desc");
		List<ProductConfig> l=productConfigService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new ProductConfig();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=productConfigService.findProductConfigById(id);
		return SUCCESS;
	}
	@Action("default")
	public String defaultConfig(){
		boolean i=productConfigService.setDefaultConfig(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=productConfigService.deleteProductConfig(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=productConfigService.deleteProductConfigs(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(obj.getProductConfigId()==0){
			int i=productConfigService.saveProductConfig(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=productConfigService.updateProductConfig(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IProductConfigService getProductConfigService() {
		return productConfigService;
	}
	public void setProductConfigService(IProductConfigService productConfigService) {
		this.productConfigService = productConfigService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public ProductConfig getObj() {
		return obj;
	}
	public void setObj(ProductConfig obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
}
