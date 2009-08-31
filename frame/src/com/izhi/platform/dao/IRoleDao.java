package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Role;
import com.izhi.platform.util.PageParameter;

public interface IRoleDao{
	List<Map<String,Object>>  findPage(PageParameter pp,int orgId);
	List<Map<String,Object>> findPage(PageParameter pp,int orgId,int userId);
	int findTotalCount(int orgId);
	Role findObjById(int id);
	
	List<Map<String,Object>> findJsonById(int id);
	
	void deleteUserRole(int userId,int roleId);
	void saveUserRole(int userId,int roleId);
	
	boolean findExist(Role o);
	int updateRole(Role r);
	boolean deleteRoles(List<Integer> ids);
	
	boolean deleteRole(int id);
	
	int saveRole(Role r);
}
