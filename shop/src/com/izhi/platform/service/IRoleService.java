package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import org.acegisecurity.ConfigAttributeDefinition;

import com.izhi.platform.model.Shop;
import com.izhi.platform.model.Role;
import com.izhi.platform.util.PageParameter;

public interface IRoleService extends IBaseService<Role, Integer> {
	ConfigAttributeDefinition findRolesByUrl(Shop org,String url);
	boolean findIsExist(String oldName);
	Map<String,Object> findPage(PageParameter pp,Shop org);
	Integer findTotalCount(Shop org);
	Map<String,Object> findPage(PageParameter pp,int orgId,int userId );
	Map<String,Object>  findRoleById(int id);
	Role findObjById(int id);
	void deleteUserRole(int userId,List<Integer> roleIds);
	void saveUserRole(int userId,List<Integer> roleIds);
	void saveUsersRoles(String userIds, List<Integer> roleIds);
}