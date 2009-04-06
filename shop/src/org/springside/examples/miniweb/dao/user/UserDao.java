package org.springside.examples.miniweb.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springside.examples.miniweb.entity.user.User;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 继承于HibernateDao的范型DAO子类.
 * 
 * 用于集中定义HQL,封装DAO细节,在Service间解耦并共享DAO操作.
 * 
 * @author calvin
 */
//Spring DAO Bean的标识
@Repository
public class UserDao extends HibernateDao<User, Long> implements IUserDao {

	// 统一定义所有用户的HQL.
	private static final String QUERY_BY_ROLE_HQL = "select user from User user join user.roles as role where role.name=?";

	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.dao.user.IUserDao#loadByLoginName(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.dao.user.IUserDao#loadByLoginName(java.lang.String)
	 */
	public User loadByLoginName(String userName) {
		return findUniqueByProperty("loginName", userName);
	}

	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.dao.user.IUserDao#getUsersByRole(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.dao.user.IUserDao#getUsersByRole(java.lang.String)
	 */
	public List<User> getUsersByRole(String roleName) {
		return find(QUERY_BY_ROLE_HQL, roleName);
	}

	@Override
	public boolean isPropertyUnique(String s1, String s2, String s3) {
		// TODO Auto-generated method stub
		return false;
	}


}
