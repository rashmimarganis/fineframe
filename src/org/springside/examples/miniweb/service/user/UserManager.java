package org.springside.examples.miniweb.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.user.IUserDao;
import org.springside.examples.miniweb.dao.user.UserDao;
import org.springside.examples.miniweb.entity.user.User;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.modules.orm.hibernate.EntityManager;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户的所有业务管理函数.
 * 演示派生DAO层子类的模式,由注入的UserDao封装数据库访问.
 *  
 * 通过范型声明继承EntityManager,默认拥有CRUD管理方法.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager extends EntityManager<User, Long> implements IUserManager {
	@Resource
	private IUserDao userDao;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected UserDao getEntityDao() {
		return (UserDao)userDao;
	}

	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.service.user.IUserManager#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}

		userDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.service.user.IUserManager#isLoginNameUnique(java.lang.String, java.lang.String)
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String loginName, String orgLoginName) {
		return userDao.isPropertyUnique("loginName", loginName, orgLoginName);
	}

	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.service.user.IUserManager#getUsersByRole(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public List<User> getUsersByRole(String roleName) {
		return userDao.getUsersByRole(roleName);
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
