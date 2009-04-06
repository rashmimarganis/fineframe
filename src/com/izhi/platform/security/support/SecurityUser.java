package com.izhi.platform.security.support;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.userdetails.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;

public class SecurityUser {
	private static Logger logger = LoggerFactory.getLogger(SecurityUser.class);

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
		User user = SecurityUser.getCurrentUser();
		if (user != null) {
			return user.getId();
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

	public static User getCurrentUser() {
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
		User user = getCurrentUser();
		if (user != null) {
			return user.getPerson().getRealname();
		}
		return "";
	}

	public static String getIp() {

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

	@SuppressWarnings("unchecked")
	public static Org getCurrentOrg() {
		Org org = null;
		User u = SecurityUser.getCurrentUser();
		if (u != null) {
			org = u.getPerson().getOrg();
		}
		return org;
	}

}
