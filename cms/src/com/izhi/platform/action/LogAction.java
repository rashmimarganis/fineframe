package com.izhi.platform.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Log;
import com.izhi.platform.service.ILogService;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/log")
public class LogAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1397781561827309123L;
	private int userId=0;
	private int orgId=0;
	private Date beginTime=null;
	private String time;
	private Date endTime=null;
	private String ids;
	private int p=1;
	@Resource(name="logService")
	private ILogService service;
	
	public ILogService getService() {
		return service;
	}

	public void setService(ILogService service) {
		this.service = service;
	}
	@Action(value="delete")
	public String delete(){
		boolean success=(service.delete(ids) >0);
		this.out("{'success':"+success+"}");
		return null;
	}
	@Action(value="list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)service.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("id");
		pp.setDir("desc");
		List<Log> l=service.findPage(pp.getStart(), pp.getLimit(), pp.getSort(), pp.getDir());
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	public String find(){
		Map<String,Object> m=service.findPage(userId, orgId, beginTime, endTime, this.getPageParameter());
		this.out(JSONObject.fromObject(m).toString());
		return null;
	}
	
	public String findByUser(){
		this.out(JSONObject.fromObject(service.findByUserId(userId, time, null)).toString());
		return null;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getP() {
		return p;
	}

	public void setP(int page) {
		this.p = page;
	}

}
