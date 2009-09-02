package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import org.acegisecurity.ConfigAttributeDefinition;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.Role;
import com.izhi.platform.util.PageParameter;

public interface IRoleService  {
	ConfigAttributeDefinition findRolesByUrl(Org org,String url);
	boolean findExist(Role o);
	Map<String,Object> findPage(PageParameter pp,int orgId);
	Integer findTotalCount(int orgId);
	Map<String,Object> findPage(PageParameter pp,int orgId,int userId );
	Role findObjById(int id);
	
	Map<String,Object> saveRole(Role r);
	
	Map<String,Object> findJsonById(int id);
	boolean deleteRole(Integer id);
	boolean deleteRoles(List<Integer> ids);
	Role findById(Integer id);
	
	boolean saveRoleFunctions(int rId,List<Integer> fIds);
}