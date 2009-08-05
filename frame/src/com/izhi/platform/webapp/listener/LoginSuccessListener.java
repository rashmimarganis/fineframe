package com.izhi.platform.webapp.listener;

import javax.annotation.Resource;

import org.acegisecurity.Authentication;
import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.acegisecurity.event.authentication.AuthenticationSwitchUserEvent;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Log;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.ILogService;
import com.izhi.platform.service.IUserService;
@Service("loginSuccessListener")
public class LoginSuccessListener implements ApplicationListener {
	private static Logger log=LoggerFactory.getLogger(LoginSuccessListener.class);
	@Resource(name="logService")
	private ILogService logService;
	@Resource(name="userService")
	private IUserService userService;
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// ①判断是否是认证成功的事件
		if (event instanceof AuthenticationSuccessEvent) {
			
			AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
			Authentication auth = authEvent.getAuthentication();
			// ②处理登录操作
			Object obj=auth.getDetails();
			String ip=((WebAuthenticationDetails)obj).getRemoteAddress();
			User user=(User)auth.getPrincipal();
			//SecurityUser.setCurrentOrg(user.getPerson().getOrg());
			user.setLastLoginIp(ip);
			Log spLog = new Log();
			spLog.setIp(ip);
			spLog.setUser(user);
			spLog.setOrg(user.getOrg());
			spLog.setOperation("成功登录系统");
			log.debug("User:"+user.getUsername()+" 成功登录系统");
			spLog.setUrl("/login.jsp");
			int i=logService.save(spLog);
			log.debug("登陆成功："+i);
			userService.updateLoginInfo(user);
		}else if(event instanceof AuthenticationSwitchUserEvent){
			AuthenticationSwitchUserEvent authEvent = (AuthenticationSwitchUserEvent) event;
			Authentication auth = authEvent.getAuthentication();
			Object obj=auth.getDetails();
			String ip=((WebAuthenticationDetails)obj).getRemoteAddress();
			User user=(User)auth.getPrincipal();
			user.setLastLoginIp(ip);
			Org org=SecurityUser.getOrg();
			Log spLog = new Log();
			spLog.setIp(ip);
			spLog.setUser(user);
			spLog.setOrg(org);
			spLog.setOperation("切换组织");
			spLog.setUrl("");
			//logService.save(spLog);
			//userService.updateLoginInfo(user);
		}

	}
	public ILogService getLogService() {
		return logService;
	}
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
