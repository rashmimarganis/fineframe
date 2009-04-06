package com.izhi.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IUserDao;
import com.izhi.platform.model.User;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.IUserService;
import com.izhi.platform.util.PageParameter;

@Service("userService")
public class UserServiceImpl extends BaseService implements IUserService,
		UserDetailsService {

	private IUserDao userDao;
	private Md5PasswordEncoder encoder = new Md5PasswordEncoder();

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
	public Integer save(User user) {
		return userDao.save(user);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public void delete(Integer id) {
		userDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public int delete(String ids) {
		return userDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public Integer save(User obj, String oldName) {
		return null;
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
	public Map<String, Object> saveUser(User obj, String oldName) {
		if (obj != null) {
			if (!obj.getPassword().trim().equals("")) {
				String password = encoder.encodePassword(obj.getPassword(),
						null);
				obj.setPassword(password);
			}
			boolean success = false;
			boolean exist = false;
			if (obj.getUserId() == 0) {
				if (!this.findIsExist(obj.getUsername())) {
					success = userDao.save(obj) > 0;
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

	@Override
	@CacheFlush(modelId = "userFlushing")
	public void updateUser(User user) {

	}

	public Md5PasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Md5PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	// @Override

	@Cacheable(modelId = "userCacheing")
	public Map<String, Object> findPage(PageParameter pp, String onlineIds,
			int count) {
		Map<String, Object> m = new HashMap<String, Object>();
		List<Map<String, Object>> lm = this.findPage1(pp, onlineIds);
		m.put("objs", lm);
		m.put("totalCount", count);
		return m;
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public List<Map<String, Object>> findPage1(PageParameter pp,
			String onlineIds) {
		return userDao.findPage(pp, onlineIds);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public Map<String, Object> findInfoById(int id) {
		return userDao.findInfoById(id);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public Map<String, Object> loadById(int id) {
		return userDao.loadById(id);
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
	@CacheFlush(modelId = "userFlushing")
	public void delete(User obj) {
		userDao.delete(obj);

	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public int delete(String ids, String id) {
		return userDao.delete(ids, id);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public void deleteAll() {
		userDao.deleteAll();
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public List<User> find(String sql) {
		return userDao.find(sql);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public List<User> find(String sql, Object obj) {
		return userDao.find(sql, obj);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public List<User> find(String sql, String[] keys, Object[] objs) {
		return userDao.find(sql, keys, objs);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public User findById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public List<User> findPage(int firstResult, int maxResult,
			String sortField, String sort) {
		return userDao.findPage(firstResult, maxResult, sortField, sort);
	}

}
