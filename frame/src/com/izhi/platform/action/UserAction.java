package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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
@Scope(value = "prototype")
@Namespace("/user")
public class UserAction extends BasePageAction {

	private static final long serialVersionUID = 7910016264537982625L;
	@Resource(name = "userService")
	private IUserService userService;
	private User obj;
	private int orgId = 0;
	private List<String> userNames;
	private List<Integer> ids;
	private String oldName;
	private int id;
	
	private String password;
	
	private String oldPassword;
	
	private String rids;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService service) {
		this.userService = service;
	}

	@Action("list")
	public String list() {
		Map<String, Object> m = userService.findPage(this.getPageParameter(),
				orgId);
		this.out(JSONObject.fromObject(m).toString());
		return SUCCESS;
	}

	@Action("load")
	public String load() {
		if (id != 0) {
			String r = JSONObject.fromObject(userService.findJsonById(id))
					.toString();
			this.getRequest().setAttribute("result", r);
		}
		return SUCCESS;
	}
	@Action("right")
	public String right(){
		
		return SUCCESS;
	}

	@Action("password")
	public String password(){
		if(SecurityUser.isOnline()){
			String username=SecurityUser.getUsername();
			userService.updatePassword(username, password);
		}
		return SUCCESS;
	}
	@Action("roles")
	public String roles(){
		String result=JSONArray.fromObject(userService.findRoles(0, id)).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		String result=JSONObject.fromObject(userService.saveUser(obj)).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("saveRoles")
	public String saveRoles() {
		boolean success=userService.saveUserRoles(id,rids);
		this.getRequest().setAttribute("success", success);
		return SUCCESS;
	}

	@Action("disable")
	public String disable(){
		if(ids!=null){
			boolean r=userService.disableUser(ids);
			this.getRequest().setAttribute("success", r);
		}
		return SUCCESS;
	}
	
	@Action("lock")
	public String lock(){
		if(ids!=null){
			boolean r=userService.lockUser(ids);
			this.getRequest().setAttribute("success", r);
		}
		return SUCCESS;
	}
	
	@Action("unlock")
	public String unlock(){
		if(ids!=null){
			boolean r=userService.unlockUser(ids);
			this.getRequest().setAttribute("success", r);
		}
		return SUCCESS;
	}
	@Action("enable")
	public String enable(){
		if(ids!=null){
			boolean r=userService.enableUser(ids);
			this.getRequest().setAttribute("success", r);
		}
		return SUCCESS;
	}
	
	
	@Action("delete")
	public String delete() {
		userService.deleteUsers(ids);
		int tc = userService.findTotalCount(orgId);
		boolean success = true;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("totalCount", tc);
		m.put("success", success);
		String result=JSONObject.fromObject(m).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}

	@Action("online")
	public String online() {
		Integer count = Integer.parseInt((String) this.getServletContext()
				.getAttribute(UserCounterListener.COUNT_KEY));
		String r = JSONObject.fromObject(
				userService.findOnlinePage(this.getPageParameter(), count))
				.toString();
		this.getRequest().setAttribute("result", r);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action("kick")
	public String kick() {
		boolean result = true;
		int count = 0;
		Integer totalCount = 0;
		if (userNames != null) {
			Map<String, HttpSession> userSession = (Map<String, HttpSession>) this
					.getServletContext().getAttribute(
							UserCounterListener.USERS_SESSION);
			totalCount = Integer.parseInt((String) this.getServletContext()
					.getAttribute(UserCounterListener.COUNT_KEY));
			String username = SecurityUser.getUsername();

			for (String un : userNames) {
				if (!un.equals(username)) {
					if (userSession.containsKey(un)) {
						try {
							userSession.get(un).invalidate();
						} catch (Exception e) {
							log.error("用户[" + un + "]的Session已不存在！");
						} finally {
							result = result && true;
							count++;
							totalCount--;
						}
					}
				}
			}

		}
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("success", result);
		r.put("count", count);
		r.put("totalCount", totalCount);
		this.out(JSONObject.fromObject(r).toString());
		return null;
	}
	@Action("tree")
	public String tree(){
		List<Map<String,Object>> m=userService.findUsers(0);
		String r=JSONArray.fromObject(m).toString();
		this.getRequest().setAttribute("result", r);
		return SUCCESS;
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


	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
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


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRids() {
		return rids;
	}

	public void setRids(String rids) {
		this.rids = rids;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
