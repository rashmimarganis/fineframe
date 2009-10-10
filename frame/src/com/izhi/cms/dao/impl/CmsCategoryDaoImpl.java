package com.izhi.cms.dao.impl;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsCategoryDao;
import com.izhi.cms.model.CmsCategory;

@Service("cmsCategoryDao")
public class CmsCategoryDaoImpl extends HibernateDaoSupport implements ICmsCategoryDao{

	@Override
	public boolean deleteCategory(int id) {
		CmsCategory o=(CmsCategory)this.getHibernateTemplate().load(CmsCategory.class, id);
		o.setModel(null);
		o.setSite(null);
		List<CmsCategory> cs=o.getChildren();
		for(CmsCategory c:cs){
			this.deleteCategory(c.getCategoryId());
		}
		o.setChildren(null);
		this.getHibernateTemplate().delete(o);
		
		return true;
	}

	@Override
	public boolean deleteCategorys(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteCategory(id);
		}
		return true;
	}

	@Override
	public CmsCategory findCategoryById(int id) {
		try{
			CmsCategory c=(CmsCategory)this.getHibernateTemplate().load(CmsCategory.class, id);
			return c;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		CmsCategory c=(CmsCategory)this.getHibernateTemplate().load(CmsCategory.class, id);
		String sql="";
		if(c.getType()==CmsCategory.TYPE_CONTENT){
		    sql="select new map(o.categoryId as id,o.name as name,o.title as title ,o.sequence as sequence,o.url as url,o.show as show,o.keywords as keywords,o.description as description,o.allowpost as allowpost,o.parent.categoryId as parentId,o.site.siteId as siteId) from CmsCategory o where o.categoryId=?";
		}else if(c.getType()==CmsCategory.TYPE_LINK){
			sql="select new map(o.categoryId as id,o.name as name,o.url as url,o.sequence as sequence,o.parent.categoryId as parentId,o.site.siteId as siteId) from  CmsCategory o where o.categoryId=?";
		}else if(c.getType()==CmsCategory.TYPE_ONEPAGE){
			sql="select new map(o.categoryId as id,o.name as name,o.title as title ,o.sequence as sequence,o.url as url,o.show as show,o.keywords as keywords,o.description as description,o.allowpost as allowpost,o.parent.categoryId as parentId,o.site.siteId as siteId) from CmsCategory o where o.categoryId=?";
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findCategory( int cid) {
		Integer pid=null;
		if(cid!=0){
			pid=cid;
		}
		String sql="";
		List<Map<String,Object>> list=null;
		if(pid==null){
			sql="select new map(o.categoryId as id,o.name as text,o.url as url,o.sequence as sequence,o.parent.categoryId as parentId,o.site.siteId as siteId) from  CmsCategory o where o.parent is null";
			list=this.getHibernateTemplate().find(sql);
		}else{
		    sql="select new map(o.categoryId as id,o.name as text,o.url as url,o.sequence as sequence,o.parent.categoryId as parentId,o.site.siteId as siteId) from  CmsCategory o where o.parent.categoryId=?";
		    list=this.getHibernateTemplate().find(sql,pid);
		}
		for(Map<String,Object> m:list){
			Integer id=(Integer)m.get("id");
			List<Map<String,Object>> _list=findCategory(id);
			m.put("childeren", _list);
			m.put("leaf", _list.size()==0);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount() {
		String sql="select count(o) from CmsCategory o";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return l.get(0).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(int cid) {
		String sql="select count(o) from CmsCategory o where o.categoryId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,cid);
		return l.get(0).intValue();
	}

	@Override
	public int saveCategory(CmsCategory obj) {
		Integer i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateCategory(CmsCategory obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAll(int id) {
		String sql="select new map(o.categoryId as id,o.name as text,o.url as url,o.sequence as sequence,o.parent.categoryId as parentId,o.site.siteId as siteId) from  CmsCategory o where o.categoryId!=?";
		List<Map<String,Object>> list=this.getHibernateTemplate().find(sql,id);
		for(Map<String,Object> m:list){
			Integer id_=(Integer)m.get("id");
			List<Map<String,Object>> _list=findAll(id_);
			m.put("childeren", _list);
			m.put("leaf", _list.size()>0);
		}
		return list;
	}

}
