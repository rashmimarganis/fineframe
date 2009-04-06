package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.Person;
import com.izhi.platform.model.json.HibernateJSONObject;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IPersonService;
@Service
@Scope(value="prototype")
public class PersonAction extends BasePageAction {

	private static final long serialVersionUID = 2059935038738844180L;
	@Resource(name="personService")
	private IPersonService service;
	private List<Integer> ids;
	private Person obj;
	public IPersonService getService() {
		return service;
	}
	public void setService(IPersonService service) {
		this.service = service;
	}
	
	public Person getObj() {
		return obj;
	}
	public void setObj(Person obj) {
		this.obj = obj;
	}
	public String load(){
		this.out(HibernateJSONObject.fromObject(service.findById(obj.getId())));
		return null;
	}
	
	public String delete(){
		Map<String,Object> m=service.delete(ids,obj.getOrg());
		this.out(JSONObject.fromObject(m).toString());
		return null;
	}
	
	public String save(){
		if(obj.getOrg().getId()==0){
			Org o=SecurityUser.getCurrentOrg();
			obj.setOrg(o);
		}
		boolean r=service.save(obj)>0;
		Map<String,Boolean> m=new HashMap<String, Boolean>();
		m.put("success", r);
		this.out(JSONObject.fromObject(m).toString());
		return null;
	}
	
	public String page(){
		Org org=null;
		if(obj==null||obj.getOrg()==null||obj.getOrg().getId()==0){
			org=SecurityUser.getCurrentOrg();
		}else{
			org=obj.getOrg();
		}
		this.out(HibernateJSONObject.fromObject(service.findPage(this.getPageParameter(),org)));
		return null;
	}
	public String pageBySort(){
		Org org=null;
		if(obj==null||obj.getOrg()==null||obj.getOrg().getId()==0){
			org=SecurityUser.getCurrentOrg();
		}else{
			org=obj.getOrg();
		}
		this.out(HibernateJSONObject.fromObject(service.findPageBySort(this.getPageParameter(),org)));
		return null;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
}
