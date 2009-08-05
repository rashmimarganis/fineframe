package com.izhi.web.action;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.web.service.IUserWebService;
import com.opensymphony.xwork2.ActionSupport;
@Service
@Scope(value="prototype")
@Namespace("/member")
public class UserValidateAction extends ActionSupport {

	private static final long serialVersionUID = -14005222292468811L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private String un;
	private String code;
	@Resource(name="userClient")
	private IUserWebService service;
	@Action(value="validateUser")
	public String execute(){
		log.debug("Username:"+un+",Code:"+code);
		if(un==null||"".equals(un)||code==null||"".equals(code)){
			return ERROR;
		}
		String m=service.validateUser(un, code);
		this.getRequest().setAttribute("result", m);
		return SUCCESS;
	}
	
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public IUserWebService getService() {
		return service;
	}
	public void setService(IUserWebService service) {
		this.service = service;
	}
	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}
	

}
