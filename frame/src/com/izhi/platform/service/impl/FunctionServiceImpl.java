package com.izhi.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IFunctionDao;
import com.izhi.platform.model.Function;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.IFunctionService;

@Service("functionService")
public class FunctionServiceImpl extends BaseService implements
		IFunctionService {
	@Resource(name = "functionDao")
	private IFunctionDao functionDao;

	@Override
	@CacheFlush(modelId = "functionFlushing")
	
	public void delete(Integer id) {
		this.getFunctionDao().delete(id);
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public void delete(Function obj) {
		this.getFunctionDao().delete(obj);
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public int delete(String ids, String id) {
		return this.getFunctionDao().delete(ids, "id");
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public int delete(String ids) {
		return this.getFunctionDao().delete(ids);
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public void deleteAll() {
		this.getFunctionDao().deleteAll();
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<Function> find(String sql) {
		return this.getFunctionDao().find(sql);
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<Function> find(String sql, Object obj) {
		return this.getFunctionDao().find(sql, obj);
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<Function> find(String sql, String[] keys, Object[] objs) {
		return this.getFunctionDao().find(sql, keys, objs);
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public Function findById(Integer id) {
		return this.functionDao.findById(id);
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<Function> findPage(int firstResult, int maxResult,
			String sortField, String sort) {
		return this.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public Integer save(Function obj, String oldName) {
		// obj=null
		if (obj == null) {
			return new Integer(-2);
		}
		// add
		if (obj.getFunctionId() == 0) {
			boolean exist = this.functionDao.findIsExist("name", obj
					.getFunctionName());
			log.info("Exist:" + exist);
			if (exist) {
				// name exist
				return new Integer(-1);
			} else {
				return this.functionDao.save(obj);
			}
		} else {
			// update
			if (obj.getFunctionName().equals(oldName)) {
				this.functionDao.update(obj);
				// success
				return new Integer(1);
			} else if (!this.functionDao.findIsExist("name", obj
					.getFunctionName())) {
				this.functionDao.update(obj);
				// success
				return new Integer(1);
			} else {
				// name exist
				return new Integer(-1);
			}
		}
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public Integer save(Function obj) {
		return functionDao.save(obj);
	}

	public IFunctionDao getFunctionDao() {
		return functionDao;
	}

	public void setFunctionDao(IFunctionDao menuDao) {
		this.functionDao = menuDao;
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<Function> findTopFunctions(int orgId, int userId) {
		List<Function> list = new ArrayList<Function>();
		if (orgId != 0 && userId != 0) {
			list = functionDao.findTopFunctions(orgId, userId);
		}
		return list;
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<Function> findNextFunctions(int orgId, int userId, int pid) {
		List<Function> nextNodes = functionDao.findNextFunctions(
				orgId, userId, pid);
		return nextNodes;
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<Function> findChildren(Org org, User user, String parentName) {
		if (org == null || user == null || parentName == null
				|| parentName.equals("")) {
			return new ArrayList<Function>();
		}
		Integer userId = user.getUserId();
		Integer orgId = org.getOrgId();
		String[] keys = new String[] { "orgId", "userId", "parentName" };
		Object[] values = new Object[] { orgId, userId, parentName };
		List<Function> nextMenus = functionDao.findChildren(keys, values);
		return nextMenus;
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public String findTreeNodes(Integer id) {
		List<Map<String, Object>> list = functionDao.findTreeNodes(id);
		return JSONArray.fromObject(list).toString();
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public Map<String, Object> saveFunction(Function obj, String oldName) {
		// add
		boolean exist = false;
		if (obj.getFunctionId() == 0) {
			exist = this.findIsExist("name", obj.getFunctionName());
			if (!exist) {
				if (obj.getParent() == null
						|| obj.getParent().getFunctionId() == 0) {
					obj.setParent(null);
				}
				Integer id = this.functionDao.save(obj);
				obj.setFunctionId(id);
			} else {
				exist = true;
			}
		} else {
			// update
			if (obj.getFunctionName().equals(oldName)) {
				this.functionDao.update(obj);

			} else if (!this.findIsExist("name", obj.getFunctionName())) {
				this.functionDao.update(obj);
			} else {
				exist = true;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exist", exist);
		map.put("obj", obj);
		return map;
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public Map<String, Object> saveFunction(Function obj) {
		boolean exist = false;
		exist = this.findIsExist("name", obj.getFunctionName());
		if (!exist) {
			Integer id = this.functionDao.save(obj);
			obj.setFunctionId(id);
		} else {
			exist = true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exist", exist);
		map.put("obj", obj);
		return map;
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public Boolean findIsExist(String n, String n1) {
		return this.functionDao.findIsExist(n, n1);
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public String findRoleFunctions(int roleId) {
		List<Map<String, Object>> l1 = this.functionDao.findChildren(0, roleId);
		for (Map<String, Object> m1 : l1) {
			int id1 = (Integer) m1.get("id");

			List<Map<String, Object>> l2 = this.functionDao.findChildren(id1,
					roleId);
			for (Map<String, Object> m2 : l2) {
				int id2 = (Integer) m2.get("id");
				List<Map<String, Object>> l3 = this.functionDao.findChildren(
						id2, roleId);
				for (Map<String, Object> m3 : l3) {
					m3.put("children", new ArrayList<Map<String, Object>>());
				}
				m2.put("children", l3);
			}
			m1.put("children", l2);
		}

		return JSONArray.fromObject(l1).toString();
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public boolean saveRoleFunction(int roleId, List<Integer> ids) {
		if (functionDao.deleteRoleFunction(roleId) >= 0) {
			for (Integer menuId : ids) {
				functionDao.saveRoleFunction(roleId, menuId);
			}
			return true;
		}
		return false;

	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public int deleteRoleFunction(String roleIds) {
		return functionDao.deleteRoleFunction(roleIds);
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<String> findAllUrl() {
		return functionDao.findAllUrl();
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public List<String> findRolesByUrl(String url) {
		return functionDao.findRolesByUrl(url);
	}

	@Override
	@Cacheable(modelId = "functionCaching")
	public Function findFunctionByUrl(String url) {
		return functionDao.findFunctionByUrl(url);
	}

	@Override
	@CacheFlush(modelId = "functionFlushing")
	public void update(Function obj) {
		functionDao.update(obj);
	}

	@Override
	public List<Map<String, Object>> findMenus(int orgId, int userId, int pid) {
		return functionDao.findMenus(orgId, userId, pid);
	}

}
