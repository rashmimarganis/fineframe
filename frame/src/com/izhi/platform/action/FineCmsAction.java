package com.izhi.platform.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.Site;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.ISiteService;
@Service
@Scope("prototype")
public class FineCmsAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Site site;
	private User user;
	private Org org;
	@Resource(name="siteService")
	private ISiteService siteService;
	@Action("/finecms")
	public String home(){
		if(SecurityUser.isOnline()){
			user=SecurityUser.getUser();
			
			site=siteService.getSite();
			org=SecurityUser.getOrg();
		}
		System.out.println("==========================User:"+user);
		log.debug("User:"+user);
		return SUCCESS;
	}
	public User getUser() {
		return user;
	}
	
	public Org getOrg() {
		return org;
	}
	
	public ISiteService getSiteService() {
		return siteService;
	}
	public void setSiteService(ISiteService siteService) {
		this.siteService = siteService;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}

}
