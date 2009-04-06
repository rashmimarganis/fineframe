package com.izhi.platform.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Shop;
import com.izhi.platform.model.json.HibernateJSONObject;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IShopService;
@Service
@Scope(value="prototype")
public class OrgAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3796085807778344395L;
	@Resource(name="service")
	private IShopService service;
	private Shop obj;
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
		if(obj.getShopId()==0){
			obj.setShopId(SecurityUser.getShop().getShopId());
		}
		obj = this.service.findById(obj.getShopId());
		this.out(HibernateJSONObject.fromObject(obj));
		return null;
	}

	public String tree() {
		if(node==0){
			node=SecurityUser.getShop().getShopId();
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
		if(obj.getParent().getShopId()==0){
			obj.setParent(SecurityUser.getShop());
		}
		this.out(HibernateJSONObject.fromObject(this.service.saveShop(obj, oldName)).toString());
		return null;
	}

	public String delete() {
		boolean success = false;
		String msg = "";
		if (obj != null) {
			this.service.delete(obj.getShopId());
			success = true;
			msg = "删除成功！";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}

	public IShopService getService() {
		return service;
	}

	public void setService(IShopService service) {
		this.service = service;
	}

	public Shop getObj() {
		return obj;
	}

	public void setObj(Shop obj) {
		this.obj = obj;
	}

	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

}
