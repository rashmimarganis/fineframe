package com.izhi.platform.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.model.PageParameter;
import com.izhi.platform.model.User;

public interface IUserDao extends IBaseDao<User,Integer> {
	User findUserByName(String username);
	User findUserByName(String username,int orgId);
	Map<String,Object> loadById(int id);
	void updateLoginInfo(User obj);
	void updatePassword(String username,String password);
	boolean findUserByPersonId(Integer id);
	int findTotalCount(Integer orgId);
	List<Map<String,Object>> findPage(PageParameter pp,Integer orgId);
	boolean findIsExist(String value);
	void updateUser(User obj);
	int deleteUserRoles(String userIds);
	Map<String,Object> findInfoById(int id);
	List<Map<String,Object>> findPage(PageParameter pp,String onlineIds);
}
