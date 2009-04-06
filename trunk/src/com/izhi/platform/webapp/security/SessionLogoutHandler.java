package com.izhi.platform.webapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.concurrent.SessionRegistry;
import org.acegisecurity.ui.logout.LogoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izhi.platform.service.ILogService;

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

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			// 因为使用了concurrentSessionController 在限制用户登陆,所以登出时移除相应的session信息
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

}
