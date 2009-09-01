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
		String sql="select f from User u join u.roles r join r.functions f where  f.menu=true and f.parent is null and u.org.id=? and u.id=? order by f.sequence";
		List<Function> list=this.getHibernateTemplate().find(sql, new Object[]{orgId,userId});
		return list;
	}
	

	@Override
	public List<Function> findChildren(String[] keys,Object[] values) {
		String sql="select m from Role mr join mr.functions,User u join u.roles ur where mr.id=ur.id and m.menu=true and mr.org.id=:orgId and mr.id=ur.id and m.parent.name=:parentName and u.id=:userId order by m.sequence";
		List<Function> list=this.find(sql, keys,values);
		return list;
	}
	
	public void delete(Integer id){
		Function f=this.findById(id);
		f.setRoles(null);
		f.setParent(null);
		this.getHibernateTemplate().delete(f);
	}
	
	public int updateFunction(Function obj){
		String sql="update Function o set o.functionName=?,o.log=?,o.menu=?,o.sequence=?,o.url=? where o.functionId=?";
		int i=this.getHibernateTemplate().bulkUpdate(sql, new Object[]{obj.getFunctionName(),obj.getLog(),obj.getMenu(),obj.getSequence(),obj.getUrl(),obj.getFunctionId()});
		return i;
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
		String sql="select new map(f.functionId as id,f.functionName as text,f.url as url) from User u join u.roles r join r.functions  f where u.org.id=? and u.id=? and f.parent.functionId=? order by f.sequence desc ";
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
	
	private boolean isChecked(int rid,int fid){
		String sql="select count(f) from Function f join f.roles r where r.roleId=? and f.functionId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,new Object[]{rid,fid});
		return l.get(0)>0;
	}
	public List<Map<String, Object>> findRoleFunctions(int rid,int pid) {
		
		List<Map<String, Object>> childrens=new ArrayList<Map<String,Object>>();
		String sql="select new map(f.functionId as id,f.functionName as text) from Function as f";
		if(pid==0){
			sql+=" where f.parent.functionId is null order by f.sequence desc";
			childrens=this.getHibernateTemplate().find(sql);
		}else{
			sql+=" where f.parent.functionId =? order by f.sequence desc";
			childrens=this.getHibernateTemplate().find(sql,pid);
		}
		
		for(Map<String,Object> c:childrens){
			Integer id=(Integer)c.get("id");
			c.put("checked",isChecked(rid,id));
			List<Map<String, Object>> cc =findRoleFunctions(rid,id);
			c.put("leaf", cc.size()==0);
			c.put("children", cc);
		}
		return childrens;
	}
}
