package com.izhi.platform.webapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.concurrent.SessionRegistry;
import org.acegisecurity.ui.logout.LogoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.ILogService;
import com.izhi.platform.service.IUserService;

public class SessionLogoutHandler implements LogoutHandler {
	final static Logger log=LoggerFactory.getLogger(SessionLogoutHandler.class);
	private SessionRegistry sessionRegistry;
	private ILogService logService;
	public ILogService getLogService() {
		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}
	
	private IUserService userService;

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			// 因为使用了concurrentSessionController 在限制用户登陆,所以登出时移除相应的session信息
			User u=SecurityUser.getUser();
			if(u!=null){
				userService.updateLogout(u.getUserId());
			}
			sessionRegistry.removeSessionInformation(session.getId());
			try{
				request.getSession().invalidate();
			}catch(Exception e){
				log.error("Session已不存在，"+e.getMessage());
			}
		}
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
