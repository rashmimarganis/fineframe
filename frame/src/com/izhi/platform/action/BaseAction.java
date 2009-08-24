package com.izhi.platform.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izhi.platform.security.support.Constants;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 769088237790376718L;
	protected Logger log=LoggerFactory.getLogger(this.getClass());
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
	protected String getWebappPath(){
		String path=this.getServletContext().getRealPath("/");
		return path;
	}
	protected String getUploadPath(){
		String path=this.getServletContext().getRealPath("/UserFiles/");
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return path;
	}
	protected String uploadFile(File file,String conentType){
		String targetDirectory = getUploadPath();
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		SimpleDateFormat psdf=new SimpleDateFormat("yyyy/MM/dd");
		String pn=psdf.format(date);
		targetDirectory+="/"+pn;
		File pfile=new File(targetDirectory);
		if(!pfile.exists()){
			pfile.mkdirs();
		}

		String targetFileName = sdf.format(date)+"."+this.getFileExt(conentType);
        File target = new File(targetDirectory, targetFileName);
        try {
			FileUtils.copyFile(file, target);
		} catch (IOException e) {
			e.printStackTrace();
		}            
        return "/UserFiles/"+pn+"/"+targetFileName;//保存文件的存放路径
	}
	
	protected String getFileExt(String ct){
		String ext=null;
		//String[] exts=new String[]{"image/bmp","image/png","image/gif","image/jpeg"};
		if("image/bmp".equals(ct)){
			ext= "bmp";
		}else if("image/png".equals(ct)){
			ext="png";
		}else if("image/gif".equals(ct)){
			ext="gif";
		}else if("image/jpeg".equals(ct)){
			ext="jpg";
		}else{
			ext="txt";
		}
		return ext;
		
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
