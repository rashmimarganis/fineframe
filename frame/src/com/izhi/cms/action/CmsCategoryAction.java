package com.izhi.cms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.CmsCategory;
import com.izhi.cms.service.ICmsCategoryService;
import com.izhi.platform.action.BaseAction;
@Service
@Scope("prototype")
@Namespace("/cms/category")
public class CmsCategoryAction extends BaseAction {

	private static final long serialVersionUID = 2905095169513378240L;
	@Resource(name="cmsCategoryService")
	private ICmsCategoryService service;
	
	private CmsCategory obj;
	
	private int id;
	
	private List<Integer> ids;
	
	@Action("index")
	public String index(){
		
		return SUCCESS;
	}
	@Action("save")
	public String save(){
		if(obj!=null){
			int i=service.saveCategory(obj);
			this.getRequest().setAttribute("success", i>0);
		}
		return SUCCESS;
	}
	@Action("delete")
	public String delete(){
		if(ids!=null){
			boolean success=service.deleteCategorys(ids);
			this.getRequest().setAttribute("success", success);
		}
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		if(id!=0){
			List<Map<String,Object>>l=this.service.findJsonById(id);
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("succss", true);
			m.put("data", l);
			this.getSession().setAttribute("resutl", JSONObject.fromObject(m).toString());
		}
		return SUCCESS;
	}

	@Action("all")
	public String all(){
		List<Map<String,Object>> l=service.findCategory(0);
		String result=JSONArray.fromObject(l).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	public ICmsCategoryService getService() {
		return service;
	}

	public void setService(ICmsCategoryService service) {
		this.service = service;
	}

	public CmsCategory getObj() {
		return obj;
	}

	public void setObj(CmsCategory obj) {
		this.obj = obj;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

}
