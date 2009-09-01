package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Person;
import com.izhi.platform.service.IPersonService;

@Service
@Scope(value = "prototype")
@Namespace("/person")
public class PersonAction extends BasePageAction {

	private static final long serialVersionUID = 4086445761876791307L;
	
	private IPersonService personService;
	
	private Person obj;
	
	private int id;
	
	
	private int orgId;
	
	
	private List<Integer> ids;
	
	@Action("save")
	public String save(){
		boolean r=false;
		if(obj!=null){
			r=personService.savePerson(obj)>0;
		}
		this.getRequest().setAttribute("success", r);
		return SUCCESS;
	}
	
	@Action("list")
	public String list(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("objs", personService.findPage(this.getPageParameter(), orgId));
		map.put("totalCount", personService.findTotalCount(orgId));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean r=false;
		if(ids!=null){
			r=personService.deletePersons(ids);
		}
		this.getRequest().setAttribute("success", r);
		return SUCCESS;
	}
	
	@Action("load")
	public String load(){
		Map<String,Object> m=personService.findJsonById(id);
		String r=JSONObject.fromObject(m).toString();
		this.getRequest().setAttribute("result", r);
		return SUCCESS;
	}
	public IPersonService getPersonService() {
		return personService;
	}
	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}
	public Person getObj() {
		return obj;
	}
	public void setObj(Person obj) {
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	

}
