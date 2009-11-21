package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.User;
import com.izhi.platform.util.PageParameter;
import com.izhi.web.model.WebUser;

public interface IUserDao {
	User findUserByName(String username);
	User findUserByName(String username,int orgId);
	User findById(int id);
	void updateLoginInfo(User obj);
	void updatePassword(String username,String password);
	
	void updateLogout(String username);
	void updateLogout(int userid);
	
	boolean findUserByPersonId(Integer id);
	int findTotalCount(Integer orgId);
	List<Map<String,Object>> findPage(PageParameter pp,Integer orgId);
	boolean findIsExist(String value);
	int updateUser(User obj);
	boolean deleteUser(int id);
	
	boolean deleteUser(String username);
	int saveUser(User obj);
	
	
	List<Map<String,Object>> findJsonById(int id);
	List<Map<String,Object>> findOnlinePage(PageParameter pp);
	
	
	boolean validateUser(String un,String code);
	WebUser findUser(String username);
	List<Map<String,Object>> findRoles(int pid,int userId);
	List<Map<String, Object>> findRolesByOrg(int orgId,int userId);
	
	void updateUserStatus();
	
	boolean lockUser(int id);
	
	boolean disableUser(int id);
	
	boolean unlockUser(int id);
	
	boolean enableUser(int id);
	
	List<Map<String, Object>> findUsers(Integer pid);
	List<Map<String, Object>> findUsersByOrg(int orgId);
}
