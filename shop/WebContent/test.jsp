<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.izhi.platform.service.IFunctionService"%>
<%@page import="com.izhi.platform.model.User"%>
<%@page import="com.izhi.platform.security.support.SecurityUser"%>
<%@page import="java.util.List"%>
<%@page import="com.izhi.platform.model.Function"%>

<%@page import="com.izhi.shop.service.IBrandService"%>
<%@page import="com.izhi.platform.util.PageParameter"%>
<%@page import="com.izhi.shop.model.Brand"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	WebApplicationContext wac = WebApplicationContextUtils
			.getRequiredWebApplicationContext(this.getServletContext());
	IBrandService fs = (IBrandService) wac.getBean("brandService");
	User u = SecurityUser.getUser();
	PageParameter pp = new PageParameter();
	pp.setLimit(10);
	pp.setSort("brandId");
	pp.setDir("desc");
	pp.setCurrentPage(1);
	List<Brand> s = fs.findPage(pp);
	out.println(s);
	out.println(s.size());
	for (Brand f : s) {
		out.println(f.getBrandName() + " " + f.getBrandUrl());
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