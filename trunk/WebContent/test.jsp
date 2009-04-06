<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springside.examples.miniservice.ws.user.UserWebService"%>
<%@page import="org.springside.examples.miniservice.ws.user.dto.UserDTO"%>
<%@page import="java.util.HashSet"%>
<%@page import="org.springside.examples.miniservice.ws.user.dto.RoleDTO"%>
<%@page import="com.izhi.platform.service.IFunctionService"%>
<%@page import="com.izhi.platform.model.User"%>
<%@page import="com.izhi.platform.security.support.SecurityUser"%>
<%@page import="java.util.List"%>
<%@page import="com.izhi.platform.model.Function"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
IFunctionService fs=(IFunctionService)wac.getBean("functionService");
User u=SecurityUser.getCurrentUser();
List<Function> s=fs.findTopFunctions(u.getPerson().getOrg().getId(),u.getId());
for(Function f:s){
	out.println(f.getFunctionName()+" "+f.getFunctionTitle());
}
/*
	WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	UserWebService client = (UserWebService) wac.getBean("userClient"); 
	for(UserDTO u:client.getAllUser().getUserList()){
		out.println(u.getLoginName()+" "+u.getName()+" "+u.getEmail()+"<br>");
	}
	UserDTO nu= new UserDTO();
	//nu.setId(new Long(client.getAllUser().getUserList().size()+2));
	nu.setEmail("zzz@ddd.com");
	nu.setLoginName("bbbbbbb"+(System.currentTimeMillis()));
	nu.setName("uu"+(System.currentTimeMillis()));
	nu.setPassword("bbbbbbbbbbb");
	nu.setRoles(new HashSet<RoleDTO>());
	//client.createUser(nu);
	User u=client.getById(2);
	out.println("LoginName:"+u.getLoginName()+" Email:"+u.getEmail());
	*/
	
%>
</body>
</html>