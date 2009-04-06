package com.izhi.platform.security.support;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.userdetails.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izhi.platform.model.Shop;
import com.izhi.platform.model.User;

public class SecurityUser {
	static Logger logger = LoggerFactory.getLogger(SecurityUser.class);

	public static String getUsername() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			Authentication auth = ctx.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (principal instanceof UserDetails) {
					return ((UserDetails) principal).getUsername();
				} else {
					return principal.toString();
				}
			}
		}
		return null;
	}

	public static boolean isAnonymous() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			Authentication auth = ctx.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (!(principal instanceof UserDetails)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isOnline() {
		return !SecurityUser.isAnonymous();
	}

	public static int getUserId() {
		User user = SecurityUser.getUser();
		if (user != null) {
			return user.getUserId();
		} else {
			return 0;
		}
	}

	public static String getPassword() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			Authentication auth = ctx.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (principal instanceof UserDetails) {
					return ((UserDetails) principal).getPassword();
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public static User getUser() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			Authentication auth = ctx.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (principal instanceof UserDetails) {
					return (User) principal;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public static String getRealname() {
		User user = getUser();
		if (user != null) {
			return user.getRealname();
		}
		return "";
	}

	public static String getLoginIp() {

		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			if (ctx instanceof SecurityContext) {
				SecurityContext sc = (SecurityContext) ctx;
				Authentication auth = sc.getAuthentication();
				if (auth != null) {
					Object details = auth.getDetails();
					if (details instanceof WebAuthenticationDetails) {
						return ((WebAuthenticationDetails) details)
								.getRemoteAddress();
					} else {
						return "";
					}
				}
			}
		}
		return null;
	}

	public static String getSessionId() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			if (ctx instanceof SecurityContext) {
				SecurityContext sc = (SecurityContext) ctx;
				Authentication auth = sc.getAuthentication();
				if (auth != null) {
					Object details = auth.getDetails();
					if (details instanceof WebAuthenticationDetails) {
						return ((WebAuthenticationDetails) details)
								.getSessionId();
					} else {
						return "";
					}
				}
			}
		}
		return null;
	}

	public static Shop getShop() {
		Shop org = null;
		User u = SecurityUser.getUser();
		if (u != null) {
			org = u.getShop();
		}
		return org;
	}

}
