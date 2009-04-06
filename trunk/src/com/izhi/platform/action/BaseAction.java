package com.izhi.platform.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izhi.platform.security.support.Constants;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {
	protected final Logger log=LoggerFactory.getLogger(this.getClass());
	private String _dc;
	private String suit="default";

	@SuppressWarnings("unchecked")
	protected void saveMessage(String msg) {
		List<String> messages = (List<String>) getRequest().getSession()
				.getAttribute("messages");
		if (messages == null) {
			messages = new ArrayList<String>();
		}
		messages.add(msg);
		getRequest().getSession().setAttribute("messages", messages);
	}

	@SuppressWarnings("unchecked")
	protected Map getConfiguration() {
		Map<String, Object> config = (HashMap) getSession().getServletContext()
				.getAttribute(Constants.CONFIG);
		if (config == null)
			return new HashMap<String, Object>();
		else
			return config;
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

	protected void out(String str) {
		write(str);
	}
	public void write(String str){
		this.write(str, "utf-8");
	}
	public void write(String str,String encoding){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/html; charset="+encoding);
		PrintWriter pw;
		try {
			pw = res.getWriter();
			pw.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String get_dc() {
		return _dc;
	}
	public void set_dc(String _dc) {
		this._dc = _dc;
	}

	public String getSuit() {
		return suit;
	}
}
