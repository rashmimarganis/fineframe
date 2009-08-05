package com.izhi.platform.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Function;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;
@Service
@Scope("prototype")
@Namespace("/")
public class MainAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Object> site=new HashMap<String, Object>();
	private User user;
	private List<Function> topFunctions=new ArrayList<Function>();
	private Org org;
	@Resource(name="functionService")
	private IFunctionService functionService;
	@Action(value="main")
	public String execute(){
		user=SecurityUser.getUser();
		site.put("name", "FineCMS网站管理系统");
		org=SecurityUser.getOrg();
		topFunctions=functionService.findTopFunctions(org.getOrgId(), user.getUserId());
		if(topFunctions==null){
			topFunctions=new ArrayList<Function>();
		}
		return SUCCESS;
	}
	public User getUser() {
		return user;
	}
	public List<Function> getTopFunctions() {
		return topFunctions;
	}
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}
	public Org getOrg() {
		return org;
	}
	public Map<String, Object> getSite() {
		return site;
	}

}
