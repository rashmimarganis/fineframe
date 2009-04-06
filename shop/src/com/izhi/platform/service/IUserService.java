package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.izhi.platform.model.User;
import com.izhi.platform.util.PageParameter;
@WebService(name = "UserService")
public interface IUserService extends IBaseService<User, Integer> {
	void updateLoginInfo(User user);
	User findUserByName(String username);
	User findUserByName(String username,int orgId);
	void updatePassword(String username,String password);
	boolean findUserByPersonId(Integer id);
	Map<String,Object> findPage(PageParameter pp,Integer orgId);
	Map<String,Object> findPageBySort(PageParameter pp,Integer orgId);
	int findTotalCount(Integer orgId);
	List<Map<String,Object>> findPage1(PageParameter pp,Integer orgId);
	Map<String,Object> saveUser(User obj,String oldName);
	boolean findIsExist(String value);
	void updateUser(User user);
	Map<String,Object> findPage(PageParameter pp,String onlineIds,int count);
	List<Map<String,Object>> findPage1(PageParameter pp,String onlineIds);
	Map<String,Object> findInfoById(int id);
	User findById(int id);
	Map<String,Object> loadById(int id);
}
