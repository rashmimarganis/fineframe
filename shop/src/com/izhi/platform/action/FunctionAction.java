package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.model.Function;
import com.izhi.platform.model.Shop;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;
@Service
@Scope(value="prototype")
public class FunctionAction extends BaseAction {

	private static final long serialVersionUID = -2424349622772191575L;

	private Function obj;
	private String oldName;
	private Integer node;
	@Resource(name="functionService")
	private IFunctionService service;
	private String ids;
	private Integer roleId;
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

	public String save() {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = this.getService().saveFunction(obj, oldName);
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}

	public String findByRole() {
		this.out(service.findRoleFunctions(roleId));
		return null;
	}

	public String saveRoleFunction() {
		boolean success = false;
		if (service.saveRoleFunction(roleId, functionIds)) {
			success = true;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", success);
		this.out(JSONObject.fromObject(m).toString());
		return null;
	}

	public String delete() {
		if (obj != null) {
			this.getService().delete(obj.getFunctionId());
		}
		return null;
	}


	public String topFunctions() {
		if (!SecurityUser.isAnonymous()) {
			User user = SecurityUser.getUser();
			Shop org = SecurityUser.getShop();
			this.out(JSONObject.fromObject(service.findTopFunctions(org.getShopId(), user.getUserId())).toString());
		} else {
			this.out("[]");
		}
		return null;
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

}
