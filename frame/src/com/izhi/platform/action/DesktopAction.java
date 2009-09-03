package com.izhi.platform.action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class DesktopAction extends BasePageAction{

	private static final long serialVersionUID = 7602751022829645777L;
	
	@Action("/desktop")
	public String execute(){
		
		return SUCCESS;
	}

}
