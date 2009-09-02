package com.izhi.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IUserDao;
import com.izhi.platform.model.Role;
import com.izhi.platform.model.User;
import com.izhi.platform.service.IRoleService;
import com.izhi.platform.service.IUserService;
import com.izhi.platform.util.PageParameter;

@Service("userService")
public class UserServiceImpl  implements IUserService,
		UserDetailsService {

	@Resource(name="userDao")
	private IUserDao userDao;
	@Resource(name="roleService")
	private IRoleService roleService;
	@Resource(name="passwordEncoder")
	private Md5PasswordEncoder encoder;
	@Resource(name="saltSource")
	private SaltSource saltSource;

	@Override
	@Cacheable(modelId = "userCacheing")
	public User findUserByName(String username) {
		return userDao.findUserByName(username);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public void updateLoginInfo(User user) {
		user.setLoginTimes(user.getLoginTimes() + 1);
		user.setLastLoginTime(new Date());
		userDao.updateLoginInfo(user);
	}


	@Override
	@CacheFlush(modelId = "userFlushing")
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public void deleteUser(Integer id) {
		userDao.deleteUser(id);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public void updatePassword(String username, String password) {
		password = encoder.encodePassword(password, null);
		userDao.updatePassword(username, password);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public boolean findUserByPersonId(Integer id) {
		return userDao.findUserByPersonId(id);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public Map<String, Object> findPage(PageParameter pp, Integer orgId) {
		List<Map<String, Object>> u = this.findPage1(pp, orgId);
		int totalCount = userDao.findTotalCount(orgId);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("objs", u);
		m.put("totalCount", totalCount);
		return m;
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public Map<String, Object> findPageBySort(PageParameter pp, Integer orgId) {
		pp.setSort("person.sort");
		List<Map<String, Object>> u = userDao.findPage(pp, orgId);
		int totalCount = userDao.findTotalCount(orgId);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("objs", u);
		m.put("totalCount", totalCount);
		return m;
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public int findTotalCount(Integer orgId) {
		return userDao.findTotalCount(orgId);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public List<Map<String, Object>> findPage1(PageParameter pp, Integer orgId) {
		return userDao.findPage(pp, orgId);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public Map<String, Object> saveUser(User obj) {
		String oldName=obj.getOldName();
		if (obj != null) {
			if (!obj.getPassword().trim().equals("")) {
				String password = encoder.encodePassword(obj.getPassword(),
						saltSource.getSalt(obj));
				obj.setPassword(password);
			}
			boolean success = false;
			boolean exist = false;
			if (obj.getUserId() == 0) {
				if (!this.findIsExist(obj.getUsername())) {
					success = userDao.saveUser(obj) > 0;
				} else {
					exist = true;
				}
			} else {
				if (obj.getUsername().equals(oldName)) {
					User obj1 = this.findById(obj.getUserId());
					obj1.setUsername(obj1.getUsername());
					userDao.updateUser(obj);
					success = true;
				} else {
					if (this.findIsExist(obj.getUsername())) {
						userDao.updateUser(obj);
						success = true;
					} else {
						exist = true;
					}
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", success);
			map.put("exist", exist);
			return map;

		}
		return new HashMap<String, Object>();
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public boolean findIsExist(String value) {
		return userDao.findIsExist(value);
	}


	public Md5PasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Md5PasswordEncoder encoder) {
		this.encoder = encoder;
	}


	@Override
	@Cacheable(modelId = "userCacheing")
	public Map<String, Object> findOnlinePage(PageParameter pp,int onlineCount) {
		Map<String, Object> m = new HashMap<String, Object>();
		List<Map<String, Object>> lm = userDao.findOnlinePage(pp);
		m.put("objs", lm);
		m.put("totalCount", onlineCount);
		return m;
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public Map<String, Object> findJsonById(int id) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("data", userDao.findJsonById(id));
		map.put("success", true);
		return map;
	}


	@Override
	@Cacheable(modelId = "userCacheing")
	public User findUserByName(String username, int orgId) {
		return userDao.findUserByName(username, orgId);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		return userDao.findUserByName(username);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public User findById(int id) {
		return userDao.findById(id);
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}


	@Override
	@Cacheable(modelId = "userCacheing")
	public User findById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	public void updateLogout(String username) {
		userDao.updateLogout(username);
		
	}

	@Override
	public void updateLogout(int userid) {
		userDao.updateLogout(userid);
		
	}

	@Override
	public List<Map<String, Object>> findRoles(int pid, int userId) {
		return userDao.findRoles(pid,userId);
	}

	@Override
	public List<Map<String, Object>> findRolesByOrg(int orgId, int userId) {
		return userDao.findRolesByOrg(orgId, userId);
	}

	@Override
	public boolean saveUserRoles(int userId, String rids) {
		User u=this.findById(userId);
		if(rids==null||rids.trim().equals("")){
			u.setRoles(null);
		}else{
			String[] ridsStr=rids.split(",");
			Set<Role> roles=new HashSet<Role>();
			for(String idStr:ridsStr){
				Integer roleId=Integer.parseInt(idStr);
				Role role=roleService.findById(roleId);
				roles.add(role);
			}
			u.setRoles(roles);
		}
		return userDao.updateUser(u)>0;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public SaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	@Override
	public void updateUserStatus() {
		userDao.updateUserStatus();
	}

	@Override
	public void deleteUsers(List<Integer> ids) {
		if(ids!=null){
			for(Integer id:ids){
				userDao.deleteUser(id);
			}
		}
		
	}

	@Override
	public boolean disableUser(List<Integer> ids) {
		if(ids!=null){
			for(Integer id:ids){
				userDao.disableUser(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean enableUser(List<Integer> ids) {
		if(ids!=null){
			for(Integer id:ids){
				userDao.enableUser(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean lockUser(List<Integer> ids) {
		if(ids!=null){
			for(Integer id:ids){
				userDao.lockUser(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean unlockUser(List<Integer> ids) {
		if(ids!=null){
			for(Integer id:ids){
				userDao.unlockUser(id);
			}
			return true;
		}
		return false;
	}


}
