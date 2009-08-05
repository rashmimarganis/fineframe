package com.izhi.platform.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IRoleDao;
import com.izhi.platform.model.Shop;
import com.izhi.platform.model.Role;
import com.izhi.platform.util.PageParameter;
@Service("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role, Integer> implements IRoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp, Shop org) {
		String sort=pp.getDir();
		String sortField=pp.getSort();
		String sql="select new map(o.id as id,o.name as name,o.title as title,o.org.title as orgTitle) from Role o where o.org.id=:orgId order by o."+sortField+" "+sort;
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("orgId", org.getShopId());
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}
	@Override
	public int delete(String ids){
		String sql1="delete RoleFunction o where o.roleId in("+ids+")";
		String sql2="delete RoleResource o where o.roleId in("+ids+")";
		String sql3="delete UserRole o where o.roleId in("+ids+")";
		this.getHibernateTemplate().bulkUpdate(sql1);
		this.getHibernateTemplate().bulkUpdate(sql2);
		this.getHibernateTemplate().bulkUpdate(sql3);
		return super.delete(ids);
	}

	@Override
	public int findTotalCount(Shop org) {
		int i=0;
		Long i1=(Long)(this.getHibernateTemplate().find("select count(obj) from Role obj where obj.org.id = ?",org.getShopId()).get(0));
		i=i1.intValue();
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, int orgId,
			int userId) {
		String sql="select new map(r.id as id,r.title as title,count(u.id) as right) from Role r left join r.users u with u.id=:userId where r.org.id=:orgId  group by r.id  order by r."+pp.getSort()+" "+pp.getDir()+ "";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("userId",	userId);
		q.setInteger("orgId", orgId);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}

	@Override
	public void deleteUserRole(int userId, int roleId) {
		String sql="delete from UserRole o where o.userId=? and o.roleId=?";
		this.getHibernateTemplate().bulkUpdate(sql, new Object[]{userId,roleId});
	}

	@Override
	public void saveUserRole(int userId, int roleId) {
		/*UserRole ur=new UserRole();
		ur.setRoleId(roleId);
		ur.setUserId(userId);
		this.getHibernateTemplate().save(ur);*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findByPk(int id) {
		String sql="select new map(o.id as id,o.name as name,o.title as title,o.org.title as orgTitle,o.org.id as orgId) from Role o where o.id=:id";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String,Object>> m=q.list();
		if(m.size()>0){
			return m.get(0);
		}
		return new HashMap<String, Object>();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Role findObjById(int id) {
		String sql="select o from Role o where o.id=?";
		List<Role> l=this.getHibernateTemplate().find(sql, id);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	
}
