package com.izhi.platform.action;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;

@Service
@Scope(value="prototype")
@Namespace("/function")
public class TopMenusAction extends BaseAction {

	private static final long serialVersionUID = 3125395435136429363L;
	@Resource(name="functionService")
	private IFunctionService service;
	private String topMenus="[]";
	public String getTopMenus() {
		return topMenus;
	}
	public void setTopMenus(String topMenus) {
		this.topMenus = topMenus;
	}
	public IFunctionService getService() {
		return service;
	}
	public void setService(IFunctionService service) {
		this.service = service;
	}
	
	@Action(value="topMenus",results={@Result(name="success",location="topMenus.ftl")})
	public String topMenus() {
		if (!SecurityUser.isAnonymous()) {
			User user = SecurityUser.getUser();
			Org org = SecurityUser.getOrg();
			topMenus=JSONObject.fromObject(service.findTopFunctions(org.getOrgId(), user.getUserId())).toString();
		} 
		return SUCCESS;
	}
}
