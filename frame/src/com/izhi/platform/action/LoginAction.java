package com.izhi.platform.action;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Site;
import com.izhi.platform.service.ISiteService;
import com.izhi.platform.webapp.filter.CookieRememberFilter;

@Service
@Scope("prototype")

public class LoginAction extends BasePageAction {
	private static final long serialVersionUID = -8952011881116458453L;

	@Resource(name="siteService")
	private ISiteService siteService;
	private Site site;
	
	private String rememberMe;
	
	private String username;
	@Action("/login")
	public String execute(){
		site=this.getSiteService().getSite();
		username=getUsername();
		rememberMe=getRememberMe();
		return SUCCESS;
	}
	
	private String getUsername(){
		Cookie[] cks=this.getRequest().getCookies();
		username="";
		if(cks!=null){
			for(Cookie ck:cks){
				if(CookieRememberFilter.Cookie_Username.equals(ck.getName())){
					username=ck.getValue();
				}
				return username;
			}
		}
		return "";
	}

	
	public String getRememberMe() {
		Cookie[] cks=this.getRequest().getCookies();
		String remember="false";
		if(cks!=null){
			for(Cookie ck:cks){
				if(CookieRememberFilter.Cookie_Check.equals(ck.getName())){
					remember=ck.getValue();
				}
				break;
			}
		}
		if(remember.equals("true")){
			rememberMe="checked";
		}else{
			rememberMe="";
		}
		return rememberMe;
	}
	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public ISiteService getSiteService() {
		return siteService;
	}
	public void setSiteService(ISiteService siteService) {
		this.siteService = siteService;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
}
