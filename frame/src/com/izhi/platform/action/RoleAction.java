package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Role;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;
import com.izhi.platform.service.IRoleService;
@Service
@Scope(value="prototype")
@Namespace("/role")
public class RoleAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8211666047695628432L;
	private Role obj;
	private String oldName;
	private List<Integer> ids;
	
	@Resource(name="roleService")
	private IRoleService roleService;
	@Resource(name="functionService") 
	private IFunctionService functionService;
	
	private List<Integer> roleIds;
	private int orgId;
	private int userId;
	
	private int id;
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
	@Action(value="index")
	public String execute(){
		return SUCCESS;
	}
	@Action("save")
	public String save(){
		if(obj==null||obj.getOrg()==null||obj.getOrg().getOrgId()==0){
			obj.setOrg(SecurityUser.getOrg());
		}
		Map<String,Object> m=roleService.saveRole(obj);
		this.getRequest().setAttribute("result", JSONObject.fromObject(m).toString());
		return SUCCESS;
	}
	@Action(value="delete")
	public String delete(){
		roleService.deleteRoles(ids);
		int tc=roleService.findTotalCount(orgId);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("success", true);
		map.put("totalCount", tc);
		this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		return SUCCESS;
	}
	@Action("list")
	public String list(){
		if(orgId==0){
			orgId=SecurityUser.getOrg().getOrgId();
		}
		this.getRequest().setAttribute("result",JSONObject.fromObject(roleService.findPage(this.getPageParameter(),orgId)).toString());
		return SUCCESS;
	}
	@Action(value="load")
	public String load(){
		Map<String,Object> m=roleService.findJsonById(id);
		String result=JSONObject.fromObject(m).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	
}
