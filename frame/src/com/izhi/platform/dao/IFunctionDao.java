package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Function;

public interface IFunctionDao extends IBaseDao<Function,Integer> {
	List<Function> findTopFunctions(Integer orgId,Integer userId);
	List<Function> findChildren(String[] keys,Object[] values);
	int updateFunction(Function obj);
	int saveFunction(Function obj);
	List<String> findAllUrl();
	List<String> findRolesByUrl( String url);
	int deleteRoleFunction(int roleId);
	int deleteRoleFunction(String roleIds);
	int saveRoleFunction(int roleId,int menuId);
	Function findFunctionByUrl(String url);
	List<Map<String,Object>> findTreeNodes(int fid);
	List<Map<String,Object>> findChildren(int id,int roleId);
	List<Function> findNextFunctions(int orgId, int userId, int pid);
	List<Map<String,Object>> findMenus(int orgId, int userId, int pid);
	List<Map<String,Object>> findFunctions(Integer pid);
	
	List<Map<String, Object>> findJsonById(int id);
	List<Map<String, Object>> findRoleFunctions(int rid,int pid);
}
