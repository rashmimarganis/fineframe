package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.Role;
import com.izhi.platform.util.PageParameter;

public interface IRoleDao extends IBaseDao<Role,Integer> {
	List<Map<String,Object>>  findPage(PageParameter pp,Org org);
	List<Map<String,Object>> findPage(PageParameter pp,int orgId,int userId);
	int findTotalCount(Org org);
	Role findObjById(int id);
	Map<String,Object> findByPk(int id);
	
	void deleteUserRole(int userId,int roleId);
	void saveUserRole(int userId,int roleId);
}
