package com.izhi.shop.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.ProductType;
import com.izhi.shop.service.IProductTypeService;
@Service
@Scope(value="prototype")
@Namespace("/producttype")
public class ProductTypeAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="productTypeService")
	private IProductTypeService productTypeService;
	private ProductType obj;
	private List<Integer> ids;
	private File image;
	private String imageContentType;
	private String imageFileName;
	private int id;
	private List<ProductType> topCategories;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)productTypeService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("productTypeId");
		pp.setDir("desc");
		List<ProductType> l=productTypeService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new ProductType();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=productTypeService.findProductTypeById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=productTypeService.deleteProductType(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("children")
	public String children(){
		obj=productTypeService.findProductTypeById(id);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		boolean i=productTypeService.deleteProductTypes(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(image!=null){
			String imagepath=this.uploadFile(image,this.getImageContentType());
			obj.setImagePath(imagepath);
		}
		if(obj.getProductTypeId()==0){
			int i=productTypeService.saveProductType(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=productTypeService.updateProductType(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IProductTypeService getProductTypeService() {
		return productTypeService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public ProductType getObj() {
		return obj;
	}
	public void setObj(ProductType obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public List<ProductType> getTopCategories() {
		return topCategories;
	}
	public void setTopCategories(List<ProductType> topCategories) {
		this.topCategories = topCategories;
	}
	
}
