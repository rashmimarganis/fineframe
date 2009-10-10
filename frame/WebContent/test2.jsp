
<%@page import="com.izhi.platform.security.support.SecurityUser"%>
<%@page import="com.izhi.platform.model.User"%>
<%@page import="org.acegisecurity.userdetails.UserDetails"%>
<%@page import="org.acegisecurity.Authentication"%>
<%@page import="org.acegisecurity.context.SecurityContextHolder"%>
<%@page import="org.acegisecurity.context.SecurityContext"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
User u1=SecurityUser.getUser();
out.println(u1);
SecurityContext ctx = SecurityContextHolder.getContext();
if (ctx != null) {
	Authentication auth = ctx.getAuthentication();
	if (auth != null) {
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetails) {
			User u= (User) principal;
			out.println(u);
		} else {
			out.println("AAAAAAAAA");
		}
	}
}
	
	
%>
