package com.izhi.framework.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameProjectDao;
import com.izhi.framework.model.FrameProject;
import com.izhi.platform.util.PageParameter;

@Service("frameProjectDao")
public class FrameProjectDaoImpl extends HibernateDaoSupport implements
		IFrameProjectDao {

	@Override
	public boolean deleteProject(int id) {
		String sql = "delete from FrameProject o where o.projectId=? ";
		int i = this.getHibernateTemplate().bulkUpdate(sql, id);
		return i > 0;
	}

	@Override
	public boolean deleteProjects(List<Integer> ids) {
		String sql = "delete from FrameProject o where o.projectId in(:ids)";
		Session session = this.getSession();
		Query q = session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i = q.executeUpdate();
		return i > 0;
	}

	@Override
	public FrameProject findProjectById(int id) {
		return (FrameProject) this.getHibernateTemplate().load(
				FrameProject.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		String sql = "select new map(o.projectId as projectId,o.title as title,o.packageName as packageName,o.sourcePath as sourcePath,o.webPath as webPath,o.name as name,o.encode as encode,o.basePath as basePath,o.databaseName as databaseName,o.databaseUser as databaseUser,o.databasePassword as databasePassword,o.databaseType as databaseType,o.databaseUrl as databaseUrl,o.driverClass as driverClass,o.javascriptPath as javascriptPath) from FrameProject o where 1=1";

		String sortField = pp.getSort();
		String sort = pp.getDir();
		sql += " order by o." + sortField + " " + sort;
		Session s = this.getSession();

		Query q = s.createQuery(sql);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());

		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount() {
		String sql = "select count(*) from FrameProject";
		List l = this.getHibernateTemplate().find(sql);
		return ((Long) l.get(0)).intValue();
	}

	@Override
	public int saveProject(FrameProject obj) {
		int i = (Integer) this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateProject(FrameProject obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameProject findProjectByName(String name) {
		String sql = "from FrameProject o where o.name=?";
		List<FrameProject> l = this.getHibernateTemplate().find(sql, name);
		if (l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql = "select new map(o.projectId as projectId,o.title as title,o.packageName as packageName,o.sourcePath as sourcePath,o.webPath as webPath,o.name as name,o.encode as encode,o.basePath as basePath,o.databaseName as databaseName,o.databaseUser as databaseUser,o.databasePassword as databasePassword,o.databaseType as databaseType,o.databaseUrl as databaseUrl,o.driverClass as driverClass,o.javascriptPath as javascriptPath) from FrameProject o where o.projectId=:id";

		Session s = this.getSession();

		Query q = s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String, Object>> l = q.list();

		return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAll() {
		String sql = "select new map(o.projectId as projectId,o.title as title,o.packageName as packageName,o.sourcePath as sourcePath,o.webPath as webPath,o.name as name,o.encode as encode,o.basePath as basePath,o.databaseName as databaseName,o.databaseUser as databaseUser,o.databasePassword as databasePassword,o.databaseType as databaseType,o.databaseUrl as databaseUrl,o.driverClass as driverClass,o.javascriptPath as javascriptPath) from FrameProject o order by o.projectId desc";
		Session s = this.getSession();
		Query q = s.createQuery(sql);
		return q.list();
	}
}
