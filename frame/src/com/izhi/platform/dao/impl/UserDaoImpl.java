package com.izhi.platform.dao.impl;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IUserDao;
import com.izhi.platform.model.Role;
import com.izhi.platform.model.User;
import com.izhi.platform.util.PageParameter;
import com.izhi.web.model.WebUser;
@Service("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements IUserDao {

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByName(String username) {
		String sql="select o from User o join o.roles r where  o.username=? ";
		List<User> l= this.getHibernateTemplate().find(sql,username);
		if(l!=null&&l.size()>0){
			return l.get(0);
		}
		return null;
	}


	@Override
	public void updateLoginInfo(User obj) {
		String sql="update User u set u.lastLoginIp=?,u.loginTimes=?,u.lastLoginTime=? where u.username=?";
		Object[] vs=new Object[]{obj.getLastLoginIp(),obj.getLoginTimes(),obj.getLastLoginTime(),obj.getUsername()};
		this.getHibernateTemplate().bulkUpdate(sql, vs);
	}

	@Override
	public void updatePassword(String username, String password) {
		String sql="update User u set u.password=? where u.username=?";
		String[] vs=new String[]{password,username};
		this.getHibernateTemplate().bulkUpdate(sql, vs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean findUserByPersonId(Integer id) {
		String sql="from User o where o.person.id=?";
		List<User> l= this.getHibernateTemplate().find(sql,id.intValue());
		return l.size()>0;
	}


	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(Integer orgId) {
		String sql="select count(o) from User o where o.person.org.id=?";
		List<Long> l=this.getHibernateTemplate().find(sql, orgId);
		return l.get(0).intValue();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp, Integer orgId) {
		UserByOrg ubo=new UserByOrg(pp,orgId);
		return this.getHibernateTemplate().executeFind(ubo);
	}


	@Override
	public boolean findIsExist( String value) {
		return this.findIsExist("username", value);
	}


	@Override
	public void updateUser(User obj) {
		String sql="";
		Object[] vs=null;
		if(obj.getPassword().trim().equals("")){
			sql="update User o set o.username=?,o.enabled=?,o.email=?,o.nonLocked=?,o.nonExpired=?,o.credentialsNonExpired=? where o.usreId=?";
			vs=new Object[]{obj.getUsername(),obj.getEnabled(),obj.getEmail(),obj.getLocked(),obj.getExpired(),obj.isCredentialsNonExpired(),obj.getUserId()};
		}else{
			sql="update User o set o.username=?,o.enabled=?,o.email=?,o.password=?,o.locked=?,o.expired=?,o.credentialsExpired=? where o.userId=?";
			vs=new Object[]{obj.getUsername(),obj.getEnabled(),obj.getEmail(),obj.getPassword(),obj.getLocked(),obj.getExpired(),obj.getCredentialsNonExpired(),obj.getUserId()};
		}
		this.getHibernateTemplate().bulkUpdate(sql, vs);
	}


	@Override
	public int deleteUserRoles(String userIds) {
		String sql="delete UserRole o where o.userId in("+userIds+")";
		return this.getHibernateTemplate().bulkUpdate(sql);
	}

	
	public int delete(String ids){
		String sql="delete UserRole o where o.userId in("+ids+")";
		return super.delete(ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, String onlineIds) {
		String sql="select new map(o.userId as id,o.username as username,o.person.realname as realname,o.email as email,o.lastLoginTime as lastLoginTime,o.lastLoginIp as lastLoginIp,o.loginTimes as loginTimes,o.person.org.title as orgTitle) from User o where o.username in("+onlineIds+") order by o."+pp.getSort()+" "+pp.getDir();
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findInfoById(int id) {
		String sql="select new map(o.id as id,o.username as username,o.person.realname as realname,o.email as email,o.enabled as enabled,o.nonExpired as nonExpired,o.nonLocked as nonLocked,o.credentialsNonExpired as credentialsNonExpired,o.person.id as personId) from User o where o.id=:id";
		Session s =this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		return (Map<String,Object>)q.list().get(0);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadById(int id) {
		String sql="select new map(o.id as id,o.username as username,o.person as person) from User o where o.id=:id";
		Session s =this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		return (Map<String,Object>)q.list().get(0);
	}


	@SuppressWarnings("unchecked")
	@Override
	public User findUserByName(String username, int orgId) {
		String sql="select o from User o where o.username=? ";
		List<User> l= this.getHibernateTemplate().find(sql,username);
		String sql1="select r from UserRole o ,Role r,User u where u.id=o.userId and o.roleId=r.id and r.org.id=? and u.username=?";
		List<Role> r=this.getHibernateTemplate().find(sql1,new Object[]{orgId,username});
		Set<Role> roles=new HashSet<Role>(r);
		if(l!=null&&l.size()>0){
			User u=l.get(0);
			u.setRoles(roles);
			return u;
		}
		return null;
	}


	@Override
	public WebUser findUser(String username) {
		String sql="select o from User o where  o.username=? ";
		List<User> l= this.getHibernateTemplate().find(sql,username);
		if(l!=null&&l.size()>0){
			User u=l.get(0);
			WebUser wu=new WebUser();
			wu.setUserId(u.getUserId());
			wu.setUsername(u.getUsername());
			wu.setRealname(u.getRealname());
			wu.setAge(u.getAge());
			wu.setAddress(u.getAddress());
			wu.setGender(u.getGender());
			wu.setPostcode(u.getPostcode());
			wu.setHintQuestion(u.getHintQuestion());
			wu.setHintAnswer(u.getHintAnswer());
			wu.setValidateCode(u.getValidateCode());
			wu.setValidated(u.isValidated());
			return wu;
		}
		return null;
	}


	@Override
	public boolean validateUser(String un, String code) {
		String sql="update User u set u.validated=true where u.username=? and u.validateCode=?";
		int i=this.getHibernateTemplate().bulkUpdate(sql, new Object[]{un,code});
		return i>0;
	}
}

class UserByOrg implements HibernateCallback{
	private PageParameter pp;
	private Integer orgId;
	
	public UserByOrg(PageParameter pp, Integer orgId){
		this.pp=pp;
		this.orgId=orgId;
	}
	@Override
	public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
		String sql="select new map(o.id as id,o.username as username,o.person.realname as realname,o.email as email,o.lastLoginTime as lastLoginTime,o.lastLoginIp as lastLoginIp,o.loginTimes as loginTimes,o.enabled as enabled,o.nonExpired as nonExpired,o.nonLocked as nonLocked,o.credentialsNonExpired as credentialsNonExpired,o.person.org.title as orgTitle,o.person.id as personId) from User o where o.person.org.id=:orgId order by o."+pp.getSort()+" "+pp.getDir();
		Query q=session.createQuery(sql);
		q.setInteger("orgId", orgId);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}
	public PageParameter getPp() {
		return pp;
	}
	public void setPp(PageParameter pp) {
		this.pp = pp;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
}
