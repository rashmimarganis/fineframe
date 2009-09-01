package com.izhi.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IRoleDao;
import com.izhi.platform.model.Function;
import com.izhi.platform.model.Role;
import com.izhi.platform.util.PageParameter;

@Service("roleDao")
public class RoleDaoImpl extends HibernateDaoSupport implements IRoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, int orgId) {
		String sort = pp.getDir();
		String sortField = pp.getSort();
		String sql = "select new map(o.id as roleId,o.roleName as roleName,o.title as title,o.org.name as orgName,o.sequence as sequence) from Role o where o.org.id=:orgId order by o."
				+ sortField + " " + sort;
		Session s = this.getSession();
		Query q = s.createQuery(sql);
		q.setInteger("orgId", orgId);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}

	

	@Override
	public int findTotalCount(int orgId) {
		int i = 0;
		Long i1 = (Long) (this.getHibernateTemplate().find(
				"select count(obj) from Role obj where obj.org.id = ?", orgId)
				.get(0));
		i = i1.intValue();
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, int orgId,
			int userId) {
		String sql = "select new map(r.id as id,r.title as title,count(u.id) as right) from Role r left join r.users u with u.id=:userId where r.org.id=:orgId  group by r.id  order by r."
				+ pp.getSort() + " " + pp.getDir() + "";
		Session s = this.getSession();
		Query q = s.createQuery(sql);
		q.setInteger("userId", userId);
		q.setInteger("orgId", orgId);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}

	@Override
	public void deleteUserRole(int userId, int roleId) {
		String sql = "delete from UserRole o where o.userId=? and o.roleId=?";
		this.getHibernateTemplate().bulkUpdate(sql,
				new Object[] { userId, roleId });
	}

	@Override
	public void saveUserRole(int userId, int roleId) {
		/*
		 * UserRole ur=new UserRole(); ur.setRoleId(roleId);
		 * ur.setUserId(userId); this.getHibernateTemplate().save(ur);
		 */
	}

	public int updateRole(Role r){
		this.getHibernateTemplate().update(r);
		return 1;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Role findObjById(int id) {
		String sql = "select o from Role o where o.id=?";
		List<Role> l = this.getHibernateTemplate().find(sql, id);
		if (l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql = "select new map(o.roleId as roleId,o.roleName as roleName,o.sequence as sequence,o.title as title,o.org.name as orgName,o.org.id as orgId) from Role o where o.roleId=?";
		List<Map<String, Object>> m = this.getHibernateTemplate().find(sql, id);
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean findExist(Role o) {
		String sql = "select count(o) from Role o where o.roleName=:name";

		if (o.getRoleId() != 0) {
			sql += " and o.roleId!=:id";
		}
		Session se = this.getSession();
		Query q = se.createQuery(sql);
		q.setString("name", o.getRoleName());
		if (o.getRoleId() != 0) {
			q.setInteger("id", o.getRoleId());
		}
		List<Long> l = q.list();

		return l.get(0) > 0;
	}
	@Override
	public boolean deleteRoles(List<Integer> ids) {
		for(int id:ids){
			 deleteRole(id);
		}
		/*
		String sql="delete from Role o where o.roleId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();*/
		return true;
	}



	@Override
	public boolean deleteRole(int id) {
		Role r=(Role)this.getHibernateTemplate().load(Role.class, id);
		r.setFunctions(null);
		r.setOrg(null);
		r.setUsers(null);
		this.getHibernateTemplate().delete(r);
		return true;
	}



	@Override
	public int saveRole(Role r) {
		Integer i=(Integer)this.getHibernateTemplate().save(r);
		return i;
	}


	public boolean saveRoleFunctions(int rId,List<Integer> fIds){
		Role obj=(Role)this.getHibernateTemplate().load(Role.class, rId);
		List<Function> list=new ArrayList<Function>();
		for(int fid:fIds){
			list.add((Function)this.getHibernateTemplate().load(Function.class, fid));
		}
		obj.setFunctions(list);
		this.getHibernateTemplate().update(obj);
		return true;
	}

	
}
