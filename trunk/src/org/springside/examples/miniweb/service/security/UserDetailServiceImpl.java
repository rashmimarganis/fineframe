package org.springside.examples.miniweb.service.security;

import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springside.examples.miniweb.dao.user.IUserDao;
import org.springside.examples.miniweb.entity.user.Authority;
import org.springside.examples.miniweb.entity.user.Role;
import org.springside.examples.miniweb.entity.user.User;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author calvin
 */
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private IUserDao userDao;

	/**
	 * 获取用户Detail信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {

		User user = userDao.loadByLoginName(userName);
		if (user == null)
			throw new UsernameNotFoundException("用户" + userName + " 不存在");

		GrantedAuthority[] grantedAuths = obtainGrantedAuthorities(user);

		// mini-web中无以下属性,暂时全部设为true.
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		/*User userdetail = new org.springframework.security.userdetails.User(
				user.getLoginName(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantedAuths);*/

		return null;
	}

	/**
	 * 获得用户所有角色的权限.
	 */
	private GrantedAuthority[] obtainGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			for (Authority authority : role.getAuths()) {
				authSet.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}
}
