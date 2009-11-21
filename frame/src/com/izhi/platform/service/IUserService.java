package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.izhi.platform.model.User;
import com.izhi.platform.util.PageParameter;

@WebService(name = "UserService")
public interface IUserService {
	void updateLoginInfo(User user);

	User findUserByName(String username);

	User findUserByName(String username, int orgId);

	void updatePassword(String username, String password);

	boolean findUserByPersonId(Integer id);

	Map<String, Object> findPage(PageParameter pp, Integer orgId);

	Map<String, Object> findPageBySort(PageParameter pp, Integer orgId);

	int findTotalCount(Integer orgId);

	List<Map<String, Object>> findPage1(PageParameter pp, Integer orgId);

	boolean findIsExist(String value);

	void updateUser(User user);

	Map<String, Object> findOnlinePage(PageParameter pp, int onlineCount);

	Map<String, Object> findJsonById(int id);

	User findById(int id);

	Map<String, Object> saveUser(User user);

	void deleteUser(Integer id);

	void deleteUsers(List<Integer> id);

	User findById(Integer id);

	void updateLogout(String username);

	void updateLogout(int userid);

	List<Map<String, Object>> findRoles(int pid, int userId);

	List<Map<String, Object>> findRolesByOrg(int orgId, int userId);

	boolean saveUserRoles(int userId, String rids);

	void updateUserStatus();

	boolean lockUser(List<Integer> ids);

	boolean disableUser(List<Integer> ids);

	boolean unlockUser(List<Integer> ids);

	boolean enableUser(List<Integer> ids);
	
	public List<Map<String, Object>> findUsers(Integer pid);
}
