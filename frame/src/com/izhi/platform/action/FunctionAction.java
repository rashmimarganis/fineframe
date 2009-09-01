package com.izhi.platform.action;

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

import com.izhi.platform.model.Function;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;

@Service
@Scope(value = "prototype")
@Namespace("/function")
public class FunctionAction extends BaseAction {

	private static final long serialVersionUID = -2424349622772191575L;

	private Function obj;
	private String oldName;
	private Integer node;
	@Resource(name = "functionService")
	private IFunctionService service;
	private String ids;
	private Integer roleId;
	private int id;
	private List<Integer> functionIds;

	public IFunctionService getService() {
		return service;
	}

	public void setService(IFunctionService service) {
		this.service = service;
	}

	public Function getObj() {
		return obj;
	}

	public void setObj(Function obj) {
		this.obj = obj;
	}

	@Action(value = "load")
	public String load() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", this.service.findJsonById(id));
		map.put("success", true);
		String result = JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}

	@Action("tree")
	public String tree() {
		String result = "";
		if (SecurityUser.isOnline()) {
			List<Map<String, Object>> orgs = service.findFunctions(0);
			result = JSONArray.fromObject(orgs).toString();
		}
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}

	@Action("save")
	public String save() {
		if (obj != null) {
			Map<String, Object> map = this.service.saveFunction(obj);
			this.getRequest().setAttribute("result",
					JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}


	@Action("delete")
	public String delete() {
		this.getService().delete(id);
		this.getRequest().setAttribute("success", true);
		return SUCCESS;
	}

	@Action("index")
	public String index() {

		return SUCCESS;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String treeNodes() {
		if (node == 0) {
			node = null;
		}
		this.out(service.findTreeNodes(node));
		return null;
	}

	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(List<Integer> functionIds) {
		this.functionIds = functionIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
