package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.Category;
import com.izhi.cms.service.ICategoryService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/category")
public class CategoryAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="categoryService")
	private ICategoryService categoryService;
	private Category obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)categoryService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("categoryId");
		pp.setDir("desc");
		List<Category> l=categoryService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Category();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=categoryService.findCategoryById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=categoryService.deleteCategory(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=categoryService.deleteCategorys(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getCategoryId()==0){
			int i=categoryService.saveCategory(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=categoryService.updateCategory(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public ICategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Category getObj() {
		return obj;
	}
	public void setObj(Category obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
