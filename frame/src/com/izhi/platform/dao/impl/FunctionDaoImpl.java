package com.izhi.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IFunctionDao;
import com.izhi.platform.model.Function;
@SuppressWarnings("unchecked")
@Service("functionDao")
public class FunctionDaoImpl extends BaseDaoImpl<Function, Integer> implements IFunctionDao {

	
	@Override
	public List<Function> findTopFunctions(Integer orgId,Integer userId) {
		String sql="select f from Function f join f.roles r join r.users u where  f.menu=true and f.parent is null and u.org.id=? and u.id=? order by f.sequence";
		List<Function> list=this.getHibernateTemplate().find(sql, new Object[]{orgId,userId});
		return list;
	}
	

	@Override
	public List<Function> findChildren(String[] keys,Object[] values) {
		String sql="select m from Function m join m.roles mr,User u join u.roles ur where mr.id=ur.id and m.menu=true and mr.org.id=:orgId and mr.id=ur.id and m.parent.name=:parentName and u.id=:userId order by m.sequence";
		List<Function> list=this.find(sql, keys,values);
		return list;
	}
	
	public void delete(Integer id){
		String sql="delete RoleFunction o where o.roleId =?";
		this.getHibernateTemplate().bulkUpdate(sql,id);
		super.delete(id);
	}
	
	public int updateFunction(Function obj){
		this.getHibernateTemplate().update(obj);
		return 1;
	}


	@Override
	public int deleteRoleFunction(String roleIds) {
		String sql="delete RoleFunction o where o.roleId in("+roleIds+")";
		return this.getHibernateTemplate().bulkUpdate(sql);
	}


	@Override
	public int deleteRoleFunction(int roleId) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int saveRoleFunction(int roleId, int menuId) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<String> findAllUrl() {
		String sql="select f.url from Function f where f.enabled=true";
		List<String> list=this.getHibernateTemplate().find(sql);
		return list;
	}


	@Override
	public List<String> findRolesByUrl(String url) {
		String sql="select r.roleName from Role r left join r.functions f where f.url=?";
		List<String> rs=this.getHibernateTemplate().find(sql, url);
		return rs;
	}


	@Override
	public Function findFunctionByUrl(String url) {
		String sql="from Function f where f.url=?";
		List<Function> list=this.getHibernateTemplate().find(sql,url);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<Map<String, Object>> findTreeNodes(int fid) {
		String sql="select new map(f.functionId as id,f.functionName as functionName,f.url as url) from Function f where f.show=true f.parent.functionId=?";
		List<Map<String, Object>> list=this.getHibernateTemplate().find(sql,fid);
		return list;
	}


	@Override
	public List<Map<String, Object>> findChildren(int id, int roleId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Function> findNextFunctions(int orgId, int userId,
			int pid) {
		String sql="select f from Function f join f.roles r join r.users u where u.org.id=? and u.id=? and f.parent.functionId=? order by f.sequence desc";
		List<Function> list=this.getHibernateTemplate().find(sql, new Object[]{orgId,userId,pid});
		/*for (Function m:list){
			String pname=m.getFunctionName();
			
			m.put("childeren", findNextFunctions(orgId,userId,pname));
		}*/
		return list;
	}


	@Override
	public List<Map<String, Object>> findMenus(int orgId, int userId, int pid) {
		List<Map<String, Object>> childrens=new ArrayList<Map<String,Object>>();
		String sql="select new map(f.functionId as id,f.functionName as text,f.url as url) from Function f join f.roles r join r.users u where u.org.id=? and u.id=? and f.parent.functionId=? order by f.sequence desc ";
		childrens=this.getHibernateTemplate().find(sql, new Object[]{orgId,userId,pid});
		for(Map<String,Object> c:childrens){
			Integer id=(Integer)c.get("id");
			List<Map<String, Object>> cc =findMenus(orgId,userId,id);
			c.put("leaf", cc.size()==0);
			c.put("children", cc);
			c.put("id", "m"+id.toString());
		}
		return childrens;
	}


	@Override
	public List<Map<String, Object>> findFunctions(Integer pid) {

		List<Map<String, Object>> childrens=new ArrayList<Map<String,Object>>();
		String sql="select new map(o.functionId as id,o.functionName as text) from Function o";
		if(pid==0){
			sql+=" where o.parent.functionId is null order by o.sequence desc";
			childrens=this.getHibernateTemplate().find(sql);
		}else{
			sql+=" where o.parent.functionId =? order by o.sequence desc";
			childrens=this.getHibernateTemplate().find(sql,pid);
		}
		
		for(Map<String,Object> c:childrens){
			Integer id=(Integer)c.get("id");
			List<Map<String, Object>> cc =findFunctions(id);
			c.put("leaf", cc.size()==0);
			c.put("children", cc);
		}
		return childrens;
	}
	
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.functionId as functionId,o.functionName as functionName,o.sequence as sequence,o.url as url,o.menu as isMenu,o.log as isLog,o.parent.functionId as parentId) from Function o where o.functionId=?";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,id);
		return l;
	}


	@Override
	public int saveFunction(Function obj) {
		return (Integer)this.getHibernateTemplate().save(obj);
	}
}
