package com.izhi.platform.webapp.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.izhi.platform.model.Log;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.ILogService;
import com.izhi.platform.service.IUserService;

public class UserCounterListener implements ServletContextListener,
		HttpSessionAttributeListener {
	public static final String COUNT_KEY = "EntwinUser_Counter";
	public static final String USERS_KEY = "EntwinUser_Names";
	public static final String USERS_SESSION = "EntwinUser_Sessions";

	public static final String EVENT_KEY = HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY;

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserCounterListener.class);

	private static transient ServletContext servletContext;

	private int counter;

	private Set<User> users;
	
	private Map<String,HttpSession> usersSession;

	public synchronized void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		usersSession=new HashMap<String, HttpSession>();
		counter=0;
		users=new LinkedHashSet<User>();
		servletContext.setAttribute((COUNT_KEY), Integer.toString(counter));
		servletContext.setAttribute(USERS_SESSION,usersSession);
		servletContext.setAttribute(USERS_KEY, users);

	}

	public synchronized void contextDestroyed(ServletContextEvent event) {
		servletContext = null;
		users = null;
		counter = 0;
		usersSession=null;
	}

	synchronized void incrementUserCounter() {
		counter = Integer.parseInt((String) servletContext
				.getAttribute(COUNT_KEY));
		counter++;
		servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));
		
	}

	synchronized void decrementUserCounter() {
		int counter = Integer.parseInt((String) servletContext
				.getAttribute(COUNT_KEY));
		counter--;
		if (counter < 0) {
			counter = 0;
		}
		servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));

	}

	@SuppressWarnings("unchecked")
	synchronized void addUser(User user,HttpSession session,String ip) {
		if(user!=null){
			users = (Set<User>) servletContext.getAttribute(USERS_KEY);
			
			if (!users.contains(user)) {
				users.add(user);
				servletContext.setAttribute(USERS_KEY, users);
				usersSession = (Map<String, HttpSession>) servletContext
				.getAttribute(USERS_SESSION);
				usersSession.put(user.getUsername(), session);
				servletContext.setAttribute(USERS_SESSION, usersSession);
				incrementUserCounter();
			}
		}
	}

	@SuppressWarnings("unchecked")
	synchronized void removeUser(User user) {
		users = (Set<User>) servletContext.getAttribute(USERS_KEY);

		if (users != null) {
			users.remove(user);
			usersSession = (Map<String, HttpSession>) servletContext.getAttribute(USERS_SESSION);
			usersSession.remove(user.getUsername());
			servletContext.setAttribute(USERS_SESSION, usersSession);
			WebApplicationContext wac = WebApplicationContextUtils
			.getWebApplicationContext(servletContext);
			ILogService logService = (ILogService) wac.getBean("logService");
			Org org=SecurityUser.getShop();
			Log spLog = new Log();
			spLog.setOperation("成功退出系统");
			spLog.setTime(new Date());
			spLog.setUser(user);
			spLog.setUrl("/logout.jsp");
			spLog.setShop(org);
			spLog.setIp(SecurityUser.getLoginIp());
			logService.save(spLog);
		}
		
		servletContext.setAttribute(USERS_KEY, users);
		decrementUserCounter();
	}

	@SuppressWarnings("unchecked")
	public void attributeAdded(HttpSessionBindingEvent event) {

		if (!isAnonymous()) {
			
			if (event.getName().equals(EVENT_KEY)) {
				User user = null;
				SecurityContext securityContext = (SecurityContext) event.getValue();
	            Authentication auth = securityContext.getAuthentication();
	            if(auth!=null){
	            	Object obj = auth.getPrincipal();
					if (obj instanceof User) {
						user = (User) obj;
					} else {
						String username = obj.toString();
						IUserService userService = (IUserService) WebApplicationContextUtils
								.getWebApplicationContext(servletContext).getBean(
										"userService");
						user = userService.findUserByName(username);
					}
					String ip="";
					if (obj instanceof WebAuthenticationDetails) {
						ip= ((WebAuthenticationDetails) obj)
								.getRemoteAddress();
					}
					this.addUser(user,event.getSession(),ip);
	            }
				
				
			}else if (event.getName().equals(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY)) {
	            String username = (String) event.getValue();
	            IUserService userService = (IUserService) WebApplicationContextUtils
				.getWebApplicationContext(servletContext).getBean(
						"userService");
	            User user = userService.findUserByName(username);
	            String ip="";
	            this.addUser(user,event.getSession(),ip);
	        }
		}
	}

	private boolean isAnonymous() {
		AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			Authentication auth = ctx.getAuthentication();
			return resolver.isAnonymous(auth);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
			SecurityContext securityContext = (SecurityContext) event.getValue();
            Authentication auth = securityContext.getAuthentication();
            if (auth != null && (auth.getPrincipal() instanceof User)) {
                User user = (User) auth.getPrincipal();
                removeUser(user);
            }
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			Authentication auth=securityContext.getAuthentication();
			if (auth != null) {
				if (auth.getPrincipal() instanceof User) {
					User user = (User) securityContext.getAuthentication()
							.getPrincipal();
					addUser(user,event.getSession(),"");
				}
			}
		}
	}
}
