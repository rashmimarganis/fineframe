package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Org;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IShopService;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/shop")
public class ShopAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3796085807778344395L;
	@Resource(name="shopService")
	private IShopService service;
	private Org obj;
	private int id=0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String oldName;
	private String ids;


	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	@Action(value="load")
	public String load() {
		if(id==0){
			id=SecurityUser.getShop().getOrgId();
		}
		obj = this.service.findById(id);
		return SUCCESS;
	}
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)service.findTopTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("shopId");
		pp.setDir("desc");
		List<Org> l=service.findTopPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	
	@Action("children")
	public String children(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)service.findTopTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("shopId");
		pp.setDir("desc");
		List<Org> l=service.findTopPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	
	@Action(value="save")
	public String save() {
		boolean s=false;
		if(obj.getOrgId()==0){
			s=this.service.saveShop(obj);
		}else{
			s=this.service.saveShop(obj,oldName);
		}
		this.getRequest().setAttribute("success", s);
		return SUCCESS;
	}
	@Action(value="delete")
	public String delete() {
		this.service.delete(id);
		this.getRequest().setAttribute("success", true);
		return SUCCESS;
	}
	@Action(value="deletes")
	public String deletes() {
		this.service.delete(id);
		this.getRequest().setAttribute("success", true);
		return SUCCESS;
	}

	public IShopService getService() {
		return service;
	}

	public void setService(IShopService service) {
		this.service = service;
	}

	public Org getObj() {
		return obj;
	}

	public void setObj(Org obj) {
		this.obj = obj;
	}

}
