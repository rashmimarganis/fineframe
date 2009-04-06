package org.springside.examples.miniweb.dao.user;

import java.util.List;

import org.springside.examples.miniweb.entity.user.User;

public interface IUserDao {

	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.dao.user.IUserDao#loadByLoginName(java.lang.String)
	 */
	User loadByLoginName(String userName);

	/* (non-Javadoc)
	 * @see org.springside.examples.miniweb.dao.user.IUserDao#getUsersByRole(java.lang.String)
	 */
	List<User> getUsersByRole(String roleName);
	boolean isPropertyUnique(String s1, String s2, String s3);
	void delete(Long id);
}