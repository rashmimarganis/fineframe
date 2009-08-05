package com.izhi.web.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BaseAction;
import com.izhi.web.model.WebUser;
import com.izhi.web.service.IUserWebService;
@Service
@Scope("prototype")
@Namespace("/member")
public class UserAvailableAction extends BaseAction {

	private static final long serialVersionUID = -4970061671842585221L;
	@Resource(name="userClient")
	private IUserWebService service;
	private WebUser obj;
	@Action(value="available")
	public String execute(){
		boolean s=!service.findExist(obj.getUsername());
		this.getRequest().setAttribute("available", s);
		return SUCCESS;
	}

	public IUserWebService getService() {
		return service;
	}

	public void setService(IUserWebService service) {
		this.service = service;
	}


	public WebUser getObj() {
		return obj;
	}

	public void setObj(WebUser obj) {
		this.obj = obj;
	}

}
