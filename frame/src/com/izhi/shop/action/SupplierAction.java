package com.izhi.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Supplier;
import com.izhi.shop.service.ISupplierService;
@Service
@Scope(value="prototype")
@Namespace("/supplier")
public class SupplierAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="supplierService")
	private ISupplierService supplierService;
	private Supplier obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)supplierService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("supplierId");
		pp.setDir("desc");
		List<Supplier> l=supplierService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Supplier();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=supplierService.findSupplierById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=supplierService.deleteSupplier(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=supplierService.deleteSuppliers(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getSupplierId()==0){
			int i=supplierService.saveSupplier(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=supplierService.updateSupplier(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public ISupplierService getSupplierService() {
		return supplierService;
	}
	public void setSupplierService(ISupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Supplier getObj() {
		return obj;
	}
	public void setObj(Supplier obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
