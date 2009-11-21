package com.izhi.oa.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.oa.dao.IOAMessageDao;
import com.izhi.oa.model.OAMessage;
import com.izhi.platform.util.PageParameter;
@Service("oaMessageDao")
public class OAMessageDaoImpl extends HibernateDaoSupport implements IOAMessageDao {

	@Override
	public boolean deleteMessage(int id) {
		OAMessage msg=this.findMessageById(id);
		if(msg!=null){
			this.getHibernateTemplate().delete(msg);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteMessages(List<Integer> ids) {
		boolean success=true;
		for(int id:ids){
			boolean s=this.deleteMessage(id);
			success=success&&s;
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.messageId as messageId,o.title as title,o.content as content,o.fromUser.userId as fromUserId,o.fromUser.person.realname as fromUsername,o.toUser.userId as toUserId,o.toUser.person.realname as toUsername,o.state as state) from OAMessage o where o.messageId=?";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,id);
		if(l.size()>0){
			return l;
		}
		return null;
	}

	@Override
	public com.izhi.oa.model.OAMessage findMessageById(int id) {
		OAMessage msg=(OAMessage)this.getHibernateTemplate().load(OAMessage.class, id);
		return msg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, int userId,
			int direct) {
		int limit=10;
		int start=0;
		if(pp!=null){
			limit=pp.getLimit();
			start=pp.getStart();
		}
		String sql="select new map(o.messageId as messageId,o.title as title,o.fromUser.person.realname as fromUsername,o.toUser.person.realname as toUsername,o.state as state) from OAMessage o where ";
		if(direct==OAMessage.DIRECT_FROM){
			sql+="o.fromUser.userId=:userId";
		}else{
			sql+="o.toUser.userId=:userId";
		}
		Query q=this.getSession().createQuery(sql);
		q.setInteger("userId", userId);
		q.setMaxResults(limit);
		q.setFirstResult(start);
		
		return q.list();
	}

	@Override
	public int findTotalCount(int userId, int direct) {
		String sql="select count(*) from OAMessage o where ";
		if(direct==OAMessage.DIRECT_FROM){
			sql+="o.fromUser.userId=?";
		}else{
			sql+="o.toUser.userId=?";
		}
		Long t=(Long)this.getHibernateTemplate().find(sql, userId).get(0);
		return t.intValue();
	}

	@Override
	public int saveMessage(com.izhi.oa.model.OAMessage obj) {
		if(obj!=null){
			return (Integer)this.getHibernateTemplate().save(obj);
		}
		return 0;
	}

	@Override
	public boolean changeState(int msgId, int state) {
		String sql="update OAMessage o set o.state=? where o.messageId=?";
		int r=this.getHibernateTemplate().bulkUpdate(sql, new Object[]{state,msgId});
		return r>0;
	}


}
