package com.izhi.platform.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
@Service
@Scope("prototype")
@Namespace("/")
public class StyleAction extends BaseAction{

	private static final long serialVersionUID = -1727802178908658217L;

	@Action(value="style")
	public String execute(){
		return SUCCESS;
	}
	
}
