package com.izhi.platform.service.impl;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IUserDao;
import com.izhi.platform.service.BaseService;
@Service("userDetailsService")
public class UserDetailsServiceImpl extends BaseService implements UserDetailsService {
	private IUserDao userDao;

	@Override
	@Cacheable(modelId="userCaching")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		return userDao.findUserByName(username);
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao dao) {
		this.userDao = dao;
	}


}
