package com.izhi.platform.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.model.Region;
import com.izhi.platform.model.json.HibernateJSONObject;
import com.izhi.platform.service.IRegionService;
@Service
@Scope(value="prototype")
public class RegionAction extends BaseAction {

	private static final long serialVersionUID = -3744528718978089715L;

	@Resource(name="regionService")
	private IRegionService service;
	private Region obj;
	private String oldCode;
	private Integer node;

	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String save(){
		this.out(HibernateJSONObject.fromObject(this.service.saveRegion(obj, oldCode)).toString());
		return null;
	}
	public String load(){
		obj = this.service.findById(obj.getId());
		this.out(HibernateJSONObject.fromObject(obj));
		return null;
	}
	
	public String delete(){
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
	
	public String tree(){
		if (node != null) {
			this.out(this.service.findChildNodes(node));
		}
		return null;
	}

	public IRegionService getService() {
		return service;
	}

	public void setService(IRegionService service) {
		this.service = service;
	}

	public Region getObj() {
		return obj;
	}

	public void setObj(Region obj) {
		this.obj = obj;
	}
}
