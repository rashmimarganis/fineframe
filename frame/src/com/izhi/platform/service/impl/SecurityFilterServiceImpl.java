package com.izhi.platform.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.acegisecurity.ConfigAttributeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Function;
import com.izhi.platform.model.Log;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;
import com.izhi.platform.service.ILogService;
import com.izhi.platform.service.IRoleService;
import com.izhi.platform.service.SecurityFilterService;
@Service("securityFilterService")
public class SecurityFilterServiceImpl  implements SecurityFilterService {
	public static final Logger log=LoggerFactory.getLogger(SecurityFilterServiceImpl.class);
	private boolean convertUrlToLowercaseBeforeComparison = false;
	private boolean useAntPath = true;
	@Resource(name="roleService")
	private IRoleService roleService;
	@Resource(name="functionService")
	private IFunctionService functionService;

	private ILogService logService;
	public boolean isConvertUrlToLowercaseBeforeComparison() {
		return convertUrlToLowercaseBeforeComparison;
	}

	public void setConvertUrlToLowercaseBeforeComparison(
			boolean convertUrlToLowercaseBeforeComparison) {
		this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
	}

	public boolean isUseAntPath() {
		return useAntPath;
	}

	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}


	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public ILogService getLogService() {
		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	@Override
	public void saveLogOperation(String url,Org org) {
		if (SecurityUser.isOnline()) {
			Log log = new Log();
			log.setOperation(functionService.findFunctionByUrl(url).getFunctionName());
			log.setTime(new Date());
			User user = SecurityUser.getUser();
			log.setUser(user);
			log.setUrl(url);
			log.setOrg(org);
			log.setIp(SecurityUser.getLoginIp());
			logService.save(log);
		}
	}

	@Override
	public ConfigAttributeDefinition lookupAttributes(String url) {
		//String url="";
		if (isUseAntPath()) {
			int firstQuestionMarkIndex = url.lastIndexOf("?");
			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}
		if (convertUrlToLowercaseBeforeComparison) {
			url = url.toLowerCase();
		}
		ConfigAttributeDefinition o=null;
		
		Org org=null;
		if(SecurityUser.isOnline()){
			org=SecurityUser.getOrg();
		}
		o=roleService.findRolesByUrl(org,url);
		if(o!=null){
			Function f=functionService.findFunctionByUrl(url);
			if(f.getLog()){
				this.saveLogOperation(url,org);
			}
		}
		return o;
	}

	public IFunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}

}
