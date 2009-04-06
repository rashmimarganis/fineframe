package com.izhi.platform.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.json.HibernateJSONObject;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IOrgService;
@Service
@Scope(value="prototype")
public class OrgAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3796085807778344395L;
	@Resource(name="service")
	private IOrgService service;
	private Org obj;
	private int node=0;
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

	public String load() {
		if(obj.getId()==0){
			obj.setId(SecurityUser.getCurrentOrg().getId());
		}
		obj = this.service.findById(obj.getId());
		this.out(HibernateJSONObject.fromObject(obj));
		return null;
	}

	public String tree() {
		if(node==0){
			node=SecurityUser.getCurrentOrg().getId();
		}
		log.debug("Current_ID : " + node);
		this.out(this.service.findChildNodes(node));
		return null;
	}
	
	public String treeAll(){
		if( node == 0 ){
			node = 1;
		}
		this.out(this.service.findChildNodes(node));
		return null;
	}
	
	/*public String changeOrgTree(){
		if(node==0){
			node=SecurityUser.getCurrentUser().getPerson().getOrg().getId();
		}
		this.out(this.service.findChildNodes(node));
		return null;
	}
*/
	public String save() {
		if(obj.getParent()==null){
			return null;
		}
		if(obj.getParent().getId()==0){
			obj.setParent(SecurityUser.getCurrentOrg());
		}
		this.out(HibernateJSONObject.fromObject(this.service.saveOrg(obj, oldName)).toString());
		return null;
	}

	public String delete() {
		boolean success = false;
		String msg = "";
		if (obj != null) {
			this.service.delete(obj.getId());
			success = true;
			msg = "删除成功！";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}

	public IOrgService getService() {
		return service;
	}

	public void setService(IOrgService service) {
		this.service = service;
	}

	public Org getObj() {
		return obj;
	}

	public void setObj(Org obj) {
		this.obj = obj;
	}

	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

}
