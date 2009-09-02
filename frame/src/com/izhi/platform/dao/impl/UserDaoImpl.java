package com.izhi.platform.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
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
		String sql="select o from User o join o.roles r where o.org.orgId=r.org.orgId and o.username=? ";
		List<User> l= this.getHibernateTemplate().find(sql,username);
		if(l!=null&&l.size()>0){
			return l.get(0);
		}
		return null;
	}


	@Override
	public void updateLoginInfo(User obj) {
		String sql="update User u set u.lastLoginIp=?,u.loginTimes=?,u.lastLoginTime=?,u.online=true where u.username=?";
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
		String sql="select count(o) from User o where o.org.id=?";
		List<Long> l=this.getHibernateTemplate().find(sql, orgId);
		return l.get(0).intValue();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp, Integer orgId) {
		String sql="select new map(o.userId as userId,o.username as username,o.online as online,o.org.name as orgName,o.enabled as enabled,o.locked as locked,o.person.realname as realname,o.lastLoginIp as lastLoginIp,o.loginTimes as loginTimes,o.lastLoginTime as lastLoginTime,o.lastLogoutTime as lastLogoutTime) from User o where o.org.orgId=:orgId";
		if(pp!=null){
			if(pp.getSort()!=null){
				sql+=" order by o."+pp.getSort();
				if(pp.getDir()!=null){
					sql+=" "+pp.getDir();
				}
			}
		}
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("orgId", orgId);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}


	@Override
	public boolean findIsExist( String value) {
		return this.findIsExist("username", value);
	}


	@Override
	public int updateUser(User obj) {
		String sql="";
		Object[] vs=null;
		if(obj.getPassword().trim().equals("")){
			sql="update User o set o.username=?,o.enabled=?,o.email=?,o.locked=?,o.expired=? where o.userId=?";
			vs=new Object[]{obj.getUsername(),obj.getEnabled(),obj.getEmail(),obj.getLocked(),obj.getExpired(),obj.getUserId()};
		}else{
			sql="update User o set o.username=?,o.enabled=?,o.email=?,o.password=?,o.locked=?,o.expired=? where o.userId=?";
			vs=new Object[]{obj.getUsername(),obj.getEnabled(),obj.getEmail(),obj.getPassword(),obj.getLocked(),obj.getExpired(),obj.getUserId()};
		}
		int i=this.getHibernateTemplate().bulkUpdate(sql, vs);
		
		return i;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findOnlinePage(PageParameter pp) {
		String sql="select new map(o.userId as userId,o.username as username,o.person.realname as realname,o.email as email,o.lastLoginTime as lastLoginTime,o.lastLoginIp as lastLoginIp,o.loginTimes as loginTimes,o.org.title as orgTitle) from User o where o.online=true  order by o."+pp.getSort()+" "+pp.getDir();
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.id as userId,o.username as username,o.person.realname as realname,o.email as email,o.enabled as enabled,o.expired as expired,o.locked as locked,o.person.id as personId,o.person.realname as realname,o.org.orgId as orgId ,o.concurrentMax as concurrentMax,o.sequence as sequence,o.org.name as orgName) from User o where o.id=:id";
		Session s =this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		return q.list();
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


	@SuppressWarnings("unchecked")
	@Override
	public WebUser findUser(String username) {
		String sql="select o from User o where  o.username=? ";
		List<User> l= this.getHibernateTemplate().find(sql,username);
		if(l!=null&&l.size()>0){
			User u=l.get(0);
			WebUser wu=new WebUser();
			wu.setUserId(u.getUserId());
			wu.setUsername(u.getUsername());
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


	@Override
	public boolean deleteUser(int id) {
		User u=(User)this.getHibernateTemplate().load(User.class, id);
		u.setOrg(null);
		u.setRoles(null);
		u.setPerson(null);
		this.getHibernateTemplate().delete(u);
		return true;
	}


	@Override
	public int saveUser(User obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean deleteUser(String username) {
		User u=this.findUserByName(username);
		u.setRoles(null);
		u.setPerson(null);
		u.setOrg(null);
		this.getHibernateTemplate().delete(u);
		return true;
	}

	@Override
	public User findById(int id) {
		return (User)this.getHibernateTemplate().load(User.class, id);
	}

	@Override
	public void updateLogout(String username) {
		Date date=new Date();
		boolean online=false;
		String sql="update User o set o.online=?,o.lastLogoutTime=? where o.username=?";
		this.getHibernateTemplate().bulkUpdate(sql, new Object[]{online,date,username});
		
	}
	
	@Override
	public void updateLogout(int userid) {
		Date date=new Date();
		boolean online=false;
		String sql="update User o set o.online=?,o.lastLogoutTime=? where o.userId=?";
		this.getHibernateTemplate().bulkUpdate(sql, new Object[]{online,date,userid});
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findRoles(int pid1,int userId) {
		Integer pid=null;
		if(pid1!=0){
			pid=pid1;
		}
		List<Map<String, Object>> childrens=new ArrayList<Map<String,Object>>();
		String sql="select new map('o_'||o.orgId as id,o.name as text,0 as role,o.orgId as pid) from Org o";
		if(pid==null){
			sql+=" where o.parent.orgId is null order by o.sort desc";
			childrens=this.getHibernateTemplate().find(sql);
		}else{
			sql+=" where o.parent.orgId =? order by o.sort desc";
			childrens=this.getHibernateTemplate().find(sql,pid);
		}
		
		for(Map<String,Object> c:childrens){
			Integer _id=(Integer)c.get("pid");
			List<Map<String, Object>> cc =findRoles(_id,userId);
			cc.addAll(this.findRolesByOrg(_id,userId));
			c.put("leaf", cc.size()==0);
			c.put("children", cc);
		}
		return childrens;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findRolesByOrg(int orgId,int userId) {
		String sql="select new map(o.roleId as id,o.title as text,1 as role,'true' as leaf,'userRole' as iconCls) from Role o where o.org.orgId=?";
		List<Map<String,Object>> list=this.getHibernateTemplate().find(sql,orgId);
		for(Map<String,Object> m:list){
			Integer roleId=(Integer)m.get("id");
			m.put("checked", this.findChecked(userId, roleId));
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public boolean findChecked(int userId,int roleId){
		String sql="select count(*) from User u join u.roles r where u.userId=? and r.roleId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,new Object[]{userId,roleId});
		return l.get(0)>0;
	}


	@Override
	public void updateUserStatus() {
		String sql="update User u set u.online=false where u.online=true";
		this.getHibernateTemplate().bulkUpdate(sql);
	}


	@Override
	public boolean disableUser(int id) {
		User u=(User)this.getHibernateTemplate().load(User.class, id);
		int r=0;
		if(u.getEnabled()){
			String sql="update User o set o.enabled=false where o.userId=?";
			r=this.getHibernateTemplate().bulkUpdate(sql, id);
		}
		return r>0;
	}


	@Override
	public boolean lockUser(int id) {
		User u=(User)this.getHibernateTemplate().load(User.class, id);
		int r=0;
		if(!u.getLocked()){
			String sql="update User o set o.locked=true where o.userId=?";
			r=this.getHibernateTemplate().bulkUpdate(sql, id);
		}
		return r>0;
	}


	@Override
	public boolean enableUser(int id) {
		User u=(User)this.getHibernateTemplate().load(User.class, id);
		int r=0;
		if(!u.getEnabled()){
			String sql="update User o set o.enabled=true where o.userId=?";
			r=this.getHibernateTemplate().bulkUpdate(sql, id);
		}
		return r>0;
	}


	@Override
	public boolean unlockUser(int id) {
		User u=(User)this.getHibernateTemplate().load(User.class, id);
		int r=0;
		if(u.getLocked()){
			String sql="update User o set o.locked=false where o.userId=?";
			r=this.getHibernateTemplate().bulkUpdate(sql, id);
		}
		return r>0;
	}
}

