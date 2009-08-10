package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Function;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;

public interface IFunctionService extends IBaseService<Function,Integer> {
	List<Function> findTopFunctions(int orgId,int userid) ;
	List<Function> findNextFunctions(int orgId, int userId,int pid);
	List<Function> findChildren(Org org,User user,String parntname); 
	String findTreeNodes(Integer id);
	
	Map<String,Object> saveFunction(Function obj,String oldName);
	Map<String,Object> saveFunction(Function obj);
	
	Boolean findIsExist(String n,String n1);
	
	String findRoleFunctions(int roleId);
	int deleteRoleFunction(String roleIds);
	boolean saveRoleFunction(int roleId,List<Integer> ids);
	
	List<String> findAllUrl();
	List<String> findRolesByUrl( String url);
	Function findFunctionByUrl(String url);
	
	List<Map<String,Object>> findMenus(int orgId, int userId, int pid);
}
