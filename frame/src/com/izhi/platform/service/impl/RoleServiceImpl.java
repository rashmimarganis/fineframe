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
import com.izhi.platform.service.IFunctionService;
import com.izhi.platform.service.IRoleService;
import com.izhi.platform.util.PageParameter;

@Service("roleService")

public class RoleServiceImpl  implements IRoleService {

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
	public boolean deleteRole(Integer id) {
		return roleDao.deleteRole(id);
	}


	@Override
	@CacheFlush(modelId = "roleFlushing")
	public boolean deleteRoles(List<Integer> ids) {
		return roleDao.deleteRoles(ids);
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
	public Map<String, Object> findPage(PageParameter pp, int orgId) {
		if (orgId == 0) {
			orgId = SecurityUser.getOrg().getOrgId();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (pp != null) {
			List<Map<String, Object>> lp = roleDao.findPage(pp, orgId);
			Integer tc = roleDao.findTotalCount(orgId);
			map.put("totalCount", tc);
			map.put("objs", lp);
		}
		return map;
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public Integer findTotalCount(int orgId) {
		return roleDao.findTotalCount(orgId);
	}

	@Override
	
	@Cacheable(modelId = "roleCaching")
	public Map<String, Object> findPage(PageParameter pp, int orgId, int userId) {
		if (orgId == 0) {
			orgId = SecurityUser.getOrg().getOrgId();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (pp != null) {
			List<Map<String, Object>> lp = roleDao.findPage(pp, orgId, userId);
			Integer tc = roleDao.findTotalCount(orgId);
			map.put("totalCount", tc);
			map.put("objs", lp);
		}
		return map;
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
		return roleDao.findObjById(id);
	}


	@Override
	public Map<String, Object> findJsonById(int id) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("data", roleDao.findJsonById(id));
		m.put("success", true);
		return m;
	}

	@Override
	@CacheFlush(modelId = "roleFlushing")
	public Map<String, Object> saveRole(Role r) {
		int id=0;
		boolean success=false;
		String action="add";
		if(r.getRoleId()==0){
			boolean exist=this.findExist(r);
			if(!exist){
				id=roleDao.saveRole(r);
				if(id>0){
					success=true;
				}
			}
		}else{
			action="update";
			if(r.getRoleName().equals(r.getOldName())){
				roleDao.updateRole(r);
				success=true;
			}else{
				boolean exist=this.findExist(r);
				if(!exist){
					roleDao.updateRole(r);
					success=true;
					id=1;
				}
			}
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("success", success);
		map.put("action",action);
		return map;
	}

	@Override
	@Cacheable(modelId = "roleCaching")
	public boolean findExist(Role o) {
		return roleDao.findExist(o);
	}
	public boolean saveRoleFunctions(int rId,List<Integer> fIds){
		return roleDao.saveRoleFunctions(rId, fIds);
	}
}
