package com.izhi.platform.action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class LogoutAction extends BasePageAction {

	private static final long serialVersionUID = -3423461303234471461L;

	@Action("/logout")
	public String execute(){
		
		return SUCCESS;
	}
}
