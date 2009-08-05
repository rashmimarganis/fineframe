package com.izhi.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.ConfigAttributeEditor;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IRoleDao;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.Role;
import com.izhi.platform.security.support.Constants;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.IFunctionService;
import com.izhi.platform.service.IRoleService;
import com.izhi.platform.util.PageParameter;

@Service("roleService")

public class RoleServiceImpl extends BaseService implements IRoleService {

	private boolean convertUrlToLowercaseBeforeComparison = false;
	private boolean useAntPath = false;
	private String defaultAuthorityRole = Constants.DEFAULT_ROLE;
	@Resource(name = "roleDao")
	private IRoleDao roleDao;
	@Resource(name = "functionService")
	private IFunctionService functionService;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private PatternMatcher matcher = new Perl5Matcher();

	public boolean isUseAntPath() {
		return useAntPath;
	}

	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	public PatternMatcher getMatcher() {
		return matcher;
	}

	public void setMatcher(PatternMatcher matcher) {
		this.matcher = matcher;
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public ConfigAttributeDefinition findRolesByUrl(Org org, String url) {
		List<String> urls = functionService.findAllUrl();
		List<String> roles = new ArrayList<String>();
		boolean matched = false;
		for (String r : urls) {
			if(r==null){
				break;
			}
			if (isUseAntPath()) {
				matched = pathMatcher.match(r, url);
			} else {
				Pattern compiledPattern;
				Perl5Compiler compiler = new Perl5Compiler();
				try {
					compiledPattern = compiler.compile(r,
							Perl5Compiler.READ_ONLY_MASK);
				} catch (MalformedPatternException mpe) {
					throw new IllegalArgumentException(
							"Malformed regular expression: " + r);
				}

				matched = matcher.matches(url, compiledPattern);
			}
			if (matched) {
				if (org == null) {
					roles.add(defaultAuthorityRole);
				} else {
					roles = functionService.findRolesByUrl(r);
				}
				log.info("Current Url:" + url + " roles :" + roles);
				String authoritiesStr = " ";
				if (roles.size() > 0) {
					for (String role : roles) {
						authoritiesStr += role + ",";
					}
					authoritiesStr = authoritiesStr.substring(0, authoritiesStr
							.length() - 1);
				}
				ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
				configAttrEditor.setAsText(authoritiesStr);
				return (ConfigAttributeDefinition) configAttrEditor.getValue();
			}
		}
		return null;
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public void delete(Integer id) {
		roleDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public void delete(Role obj) {
		this.delete(obj);
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public int delete(String ids, String id) {
		return this.delete(ids, id);
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public int delete(String ids) {
		return roleDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public void deleteAll() {
		roleDao.deleteAll();
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public List<Role> find(String sql) {
		return roleDao.find(sql);
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public List<Role> find(String sql, Object obj) {
		return roleDao.find(sql, obj);
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public List<Role> find(String sql, String[] keys, Object[] objs) {
		return roleDao.find(sql, keys, objs);
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public List<Role> findPage(int firstResult, int maxResult,
			String sortField, String sort) {
		return roleDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public Integer save(Role obj) {
		return roleDao.save(obj);
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public Integer save(Role obj, String oldName) {
		if (obj != null) {
			if (obj.getRoleId() == 0) {
				if (this.findIsExist(obj.getRoleName())) {
					return -1;
				} else {
					return roleDao.save(obj);
				}
			} else {
				if (obj.getRoleName().equals(oldName)) {
					roleDao.update(obj);
					return 1;
				} else {
					if (this.findIsExist(obj.getRoleName())) {
						return -1;
					} else {
						roleDao.update(obj);
						return 1;
					}
				}
			}
		}
		return -2;
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public boolean findIsExist(String name) {
		return roleDao.findIsExist("name", name);
	}

	public IRoleDao getDao() {
		return roleDao;
	}

	public void setDao(IRoleDao dao) {
		this.roleDao = dao;
	}

	public boolean isConvertUrlToLowercaseBeforeComparison() {
		return convertUrlToLowercaseBeforeComparison;
	}

	public void setConvertUrlToLowercaseBeforeComparison(
			boolean convertUrlToLowercaseBeforeComparison) {
		this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public Map<String, Object> findPage(PageParameter pp, Org org) {
		if (org == null || org.getOrgId() == 0) {
			org = SecurityUser.getShop();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (pp != null) {
			List<Map<String, Object>> lp = roleDao.findPage(pp, org);
			Integer tc = roleDao.findTotalCount(org);
			map.put("totalCount", tc);
			map.put("objs", lp);
		}
		return map;
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public Integer findTotalCount(Org org) {
		return roleDao.findTotalCount(org);
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public Map<String, Object> findPage(PageParameter pp, int orgId, int userId) {
		if (orgId == 0) {
			orgId = SecurityUser.getShop().getOrgId();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (pp != null) {
			List<Map<String, Object>> lp = roleDao.findPage(pp, orgId, userId);
			Org o = new Org();
			o.setOrgId(orgId);
			Integer tc = roleDao.findTotalCount(o);
			map.put("totalCount", tc);
			map.put("objs", lp);
		}
		return map;
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public void deleteUserRole(int userId, List<Integer> roleIds) {
		for (int roleId : roleIds) {
			roleDao.deleteUserRole(userId, roleId);
		}
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public void saveUserRole(int userId, List<Integer> roleIds) {
		for (int roleId : roleIds) {
			roleDao.saveUserRole(userId, roleId);
		}
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public void saveUsersRoles(String userIds, List<Integer> roleIds) {
		for (int roleId : roleIds) {
			String _userIds[] = userIds.split(",");
			Integer userId = 0;
			for (String _userId_string : _userIds) {
				userId = Integer.parseInt(_userId_string);
				roleDao.saveUserRole(userId, roleId);
			}
		}
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public Map<String, Object> findRoleById(int id) {
		return roleDao.findByPk(id);
	}

	public String getDefaultAuthorityRole() {
		return defaultAuthorityRole;
	}

	public void setDefaultAuthorityRole(String defaultAuthorityRole) {
		this.defaultAuthorityRole = defaultAuthorityRole;
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public Role findObjById(int id) {
		return roleDao.findObjById(id);
	}

	public IFunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}

	@Override
	@Cacheable(modelId = "roleCaching")
	public Role findById(Integer id) {
		return roleDao.findById(id);
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public void update(Role obj) {
		roleDao.update(obj);
	}

}
