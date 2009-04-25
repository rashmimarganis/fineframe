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
import com.izhi.shop.model.ProductCategory;
import com.izhi.shop.service.IProductCategoryService;
@Service
@Scope(value="prototype")
@Namespace("/category")
public class CategoryAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="productCategoryService")
	private IProductCategoryService categoryService;
	private ProductCategory obj;
	private List<Integer> ids;
	private File image;
	private String imageContentType;
	private String imageFileName;
	private int id;
	private List<ProductCategory> topCategories;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)categoryService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("categoryId");
		pp.setDir("desc");
		List<ProductCategory> l=categoryService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new ProductCategory();
		topCategories=categoryService.findTopAll();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=categoryService.findCategoryById(id);
		topCategories=categoryService.findTopAll();
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=categoryService.deleteCategory(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("children")
	public String children(){
		obj=categoryService.findCategoryById(id);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=categoryService.deleteCategories(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(image!=null){
			String imagepath=this.uploadFile(image,this.getImageContentType());
			obj.setImagePath(imagepath);
		}
		if(obj.getParent().getCategoryId()==0){
			obj.setParent(null);
		}
		if(obj.getCategoryId()==0){
			int i=categoryService.saveCategory(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=categoryService.updateCategory(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IProductCategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(IProductCategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public ProductCategory getObj() {
		return obj;
	}
	public void setObj(ProductCategory obj) {
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
	public List<ProductCategory> getTopCategories() {
		return topCategories;
	}
	public void setTopCategories(List<ProductCategory> topCategories) {
		this.topCategories = topCategories;
	}
	
}
