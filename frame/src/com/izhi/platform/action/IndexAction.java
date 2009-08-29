package com.izhi.platform.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
@Service
@Scope("prototype")
@Namespace("/")
public class IndexAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Object> site=new HashMap<String, Object>();
	private User user;
	private Org org;
	@Action(value="index")
	public String execute(){
		user=SecurityUser.getUser();
		site.put("name", "FineCMS网站管理系统");
		
		return SUCCESS;
	}
	public User getUser() {
		return user;
	}
	
	public Org getOrg() {
		return org;
	}
	public Map<String, Object> getSite() {
		return site;
	}

}
