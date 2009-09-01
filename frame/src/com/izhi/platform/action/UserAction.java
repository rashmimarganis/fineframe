package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IUserService;
import com.izhi.platform.webapp.listener.UserCounterListener;
@Service
@Scope(value="prototype")
@Namespace("/user")
public class UserAction extends BasePageAction {

	private static final long serialVersionUID = 7910016264537982625L;
	@Resource(name="userService")
	private IUserService userService;
	private User obj;
	private int orgId=0;
	private List<String> userNames;
	private String ids;
	private String oldName;
	private String repassword;
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService service) {
		this.userService = service;
	}
	@Action("list")
	public String list(){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("objs", userService.findPage(this.getPageParameter(),orgId));
		this.out(JSONObject.fromObject(m).toString());
		return SUCCESS;
	}
	/**
	 * 默认情况下查找当前组织下的用户。
	 * @return
	 */
	public String findPageBySort(){
		if( orgId == 0 ){
			orgId = SecurityUser.getOrg().getOrgId();
		}
		Map<String, Object> m = userService.findPageBySort(this.getPageParameter(),orgId);
		//m.put("objs", userService.findPageBySort(this.getPageParameter(),orgId));
		this.out(JSONObject.fromObject(m).toString());
		return null;
	}
	
	public String page(){
		if(orgId==0){
			orgId=SecurityUser.getOrg().getOrgId();
		}
		this.out(JSONObject.fromObject(userService.findPage(this.getPageParameter(), orgId)).toString());
		return null;
	}
	
	public String load(){
		this.out(JSONObject.fromObject(userService.findInfoById(obj.getUserId())).toString());
		return null;
	}
	
	public String findById(){
		this.out(JSONObject.fromObject(userService.loadById(obj.getUserId())).toString());
		return null;
	}
	
	public String save(){
		if(obj!=null){
			if(!repassword.equals(obj.getPassword())){
				return null;
			}
		}
		this.out(JSONObject.fromObject(userService.saveUser(obj, oldName)).toString());
		return null;
	}
	
	public String delete(){
		int tc=userService.findTotalCount(orgId);
		boolean success=true;
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("totalCount", tc);
		m.put("success", success);
		this.out(JSONObject.fromObject(m).toString());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String online(){
		Integer count=Integer.parseInt((String)this.getServletContext().getAttribute(UserCounterListener.COUNT_KEY));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", this.getStart());
		map.put("limit", this.getLimit());
		map.put("sort", this.getSort());
		map.put("dir", this.getDir());
		Map<String,HttpSession> userSession=(Map<String,HttpSession>)this.getServletContext().getAttribute(UserCounterListener.USERS_SESSION);
		Set<String> userSet=userSession.keySet();
		String userNames="";
		for(String un:userSet){
			if(userNames.equals("")){
				userNames="'"+un+"'";
			}else{
				userNames+=",'"+un+"'";
			}
		}
		if(userNames.equals("")){
			userNames="''";
		}
		//this.out(JSONObject.fromObject(userService.findPage(this.getPageParameter(), userNames,count)).toString());
		return null;
	}
	@SuppressWarnings("unchecked")
	public String kick(){
		boolean result=true;
		int count=0;
		Integer totalCount=0;
		if(userNames!=null){
			Map<String,HttpSession> userSession=(Map<String,HttpSession>)this.getServletContext().getAttribute(UserCounterListener.USERS_SESSION);
			totalCount=Integer.parseInt((String)this.getServletContext().getAttribute(UserCounterListener.COUNT_KEY));
			String username=SecurityUser.getUsername();
			
			for(String un:userNames){
				if(!un.equals(username)){
					if(userSession.containsKey(un)){
						try{
							userSession.get(un).invalidate();
						}catch(Exception e){
							log.error("用户["+un+"]的Session已不存在！");
						}finally{
							result=result&&true;
							count++;
							totalCount--;
						}
					}
				}
			}
			
		}
		Map<String,Object> r=new HashMap<String, Object>();
		r.put("success", result);
		r.put("count", count);
		r.put("totalCount", totalCount);
		this.out(JSONObject.fromObject(r).toString());
		return null;
	}
	public User getObj() {
		return obj;
	}
	public void setObj(User obj) {
		this.obj = obj;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public List<String> getUserNames() {
		return userNames;
	}
	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	
}
