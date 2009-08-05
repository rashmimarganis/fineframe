package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.Role;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;
import com.izhi.platform.service.IRoleService;
@Service
@Scope(value="prototype")
@Namespace("/role")
@Results({@Result(name="success",location="success.ftl"),@Result(name="input",location="input.ftl")})
public class RoleAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8211666047695628432L;
	private Role obj;
	private String oldName;
	private String ids;
	
	@Resource(name="roleService")
	private IRoleService roleService;
	@Resource(name="functionService") 
	private IFunctionService functionService;
	
	private List<Integer> roleIds;
	private int orgId;
	private int userId;
	/**
	 * 多个用户分配同一角色；
	 */
	private String userIds ;
	
	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Action(value="index",results={@Result(name="success",type="myfreemarker")})
	public String execute(){
		return SUCCESS;
	}
	@Action(value="save")
	public String save(){
		if(obj==null||obj.getOrg()==null||obj.getOrg().getOrgId()==0){
			obj.setOrg(SecurityUser.getOrg());
		}
		int r=roleService.save(obj, oldName);
		this.out(""+r);
		return null;
	}
	@Action(value="delete")
	public String delete(){
		roleService.delete(ids);
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("success", true);
		m.put("totalCount", roleService.findTotalCount(obj.getOrg()));
		this.out(JSONObject.fromObject(m).toString());
		return null;
	}
	@Action(value="page")
	public String page(){
		Org org=null;
		if(obj==null||obj.getOrg()==null||obj.getOrg().getOrgId()==0){
			org=SecurityUser.getOrg();
		}else{
			org=obj.getOrg();
		}
		
		this.out(JSONObject.fromObject(roleService.findPage(this.getPageParameter(),org)).toString());
		return null;
	}
	@Action(value="load",results={@Result(name="input",location="input.ftl",type="myfreemarker")})
	public String load(){
		obj=roleService.findObjById(obj.getRoleId());
		//this.out(JSONObject.fromObject(roleService.findRoleById(obj.getId())).toString());
		return "input";
	}

	@Action(value="findUserRoles")
	public String findUserRoles(){
		this.out(JSONObject.fromObject(roleService.findPage(this.getPageParameter(), orgId, userId)).toString());
		return null;
	}
	@Action(value="deleteUserRoles")
	public String deleteUserRoles(){
		roleService.deleteUserRole(userId, roleIds);
		return null;
	}
	@Action(value="saveUserRoles")
	public String saveUserRoles(){
		roleService.saveUserRole(userId, roleIds);
		return null;
	}
	@Action(value="saveUsersRoles")
	public String saveUsersRoles(){
		roleService.saveUsersRoles(userIds, roleIds);
		return null;
	}
	
	public Role getObj() {
		return obj;
	}

	public void setObj(Role obj) {
		this.obj = obj;
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

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IFunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}
	
	
}
