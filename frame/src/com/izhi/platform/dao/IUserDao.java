package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.User;
import com.izhi.platform.util.PageParameter;
import com.izhi.web.model.WebUser;

public interface IUserDao {
	User findUserByName(String username);
	User findUserByName(String username,int orgId);
	Map<String,Object> loadById(int id);
	User findById(int id);
	void updateLoginInfo(User obj);
	void updatePassword(String username,String password);
	boolean findUserByPersonId(Integer id);
	int findTotalCount(Integer orgId);
	List<Map<String,Object>> findPage(PageParameter pp,Integer orgId);
	boolean findIsExist(String value);
	int updateUser(User obj);
	boolean deleteUser(int id);
	
	boolean deleteUser(String username);
	int saveUser(User obj);
	
	int deleteUserRoles(String userIds);
	Map<String,Object> findInfoById(int id);
	List<Map<String,Object>> findPage(PageParameter pp,String onlineIds);
	
	boolean validateUser(String un,String code);
	WebUser findUser(String username);
	
}
