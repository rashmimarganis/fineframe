package com.izhi.platform.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.izhi.platform.dao.ILogDao;
import com.izhi.platform.model.Log;
import com.izhi.platform.util.PageParameter;
@Service("logDao")
public class LogDaoImpl extends BaseDaoImpl<Log, Integer> implements ILogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(int userId, int orgId,
			Date beginTime, Date endTime,PageParameter pp) {
		String sql="select new map(o.id as id,o.ip as ip,o.time as time,o.operation as operation,o.org.title as orgTitle,o.user.username as username,o.user.person.realname as realname) from Log o where 1=1";
		if(userId!=0){
			sql+=" and o.user.id="+userId;
		}
		if(orgId!=0){
			sql+=" and o.org.id="+orgId;
		}
		if(beginTime!=null){
			sql+=" and o.time>='"+dateToString(beginTime)+"'";
		}
		if(endTime!=null){
			sql+=" and o.time<=s'"+dateToString(endTime)+"'";
		}
		sql+=" order by "+pp.getSort()+" "+pp.getDir();
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}
	@Override
	public Integer save(Log obj){
		obj.setTime(new Date());
		return super.save(obj);
	}

	@Override
	public long findTotalCount(int userId, int orgId, Date beginTime,
			Date endTime) {
		String sql="select count(o) from Log o where 1=1";
		if(userId!=0){
			sql+=" and o.user.id="+userId;
		}
		if(orgId!=0){
			sql+=" and o.org.id="+orgId;
		}
		if(beginTime!=null){
			sql+=" and o.time>'"+dateToString(beginTime)+"'";
		}
		if(endTime!=null){
			sql+=" and o.time<'"+dateToString(endTime)+"'";
		}
		return (Long)this.getHibernateTemplate().find(sql).get(0);
	}

	private String dateToString(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("YY-MM-dd");
		return sdf.format(date);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findByUserId(int id,String beginTime,String endTime) {
		String sql="select new map(o.id as id,o.ip as ip,o.time as time,o.operation as operation,o.org.title as orgTitle) from Log o where o.user.id=:id and o.time>:beginTime";
		if(endTime!=null){
			sql+=" and o.time<'"+endTime+"'";
		}
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		q.setString("beginTime", beginTime);
		return q.list();
	}
	@Override
	public long findTotalCount(int userId, String beginTime, String endTime) {
		String sql="select count(o) from Log o where o.user.id=:userId and o.time>:beginTime";
		
		if(endTime!=null){
			sql+=" and o.time<'"+endTime+"'";
		}
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("userId", userId);
		q.setString("beginTime", beginTime);
		return (Long)q.list().get(0);
	}
}
