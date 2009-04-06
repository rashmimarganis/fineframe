package org.springside.examples.miniweb.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.entity.user.User;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;

public interface IUserManager {

	/**
	 * 重载delte函数,演示异常处理及用户行为日志.
	 */
	public abstract void delete(Long id);

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于orgLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public abstract boolean isLoginNameUnique(String loginName,
			String orgLoginName);

	/**
	 * 查找拥有指定角色的用户.
	 */
	@Transactional(readOnly = true)
	public abstract List<User> getUsersByRole(String roleName);
	@Transactional(readOnly = true)
	public User get(Long id);

	public Page<User> getAll(Page<User> page);

	public List<User> getAll();
	
	public void save(User entity) ;
	
	public Page<User> search(Page<User> page, List<PropertyFilter> filters);
}