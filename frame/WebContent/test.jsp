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

<%@page import="com.izhi.platform.util.PageParameter"%>
<%@page import="com.izhi.platform.service.ILogService"%>
<%@page import="java.util.Map"%>
<%@page import="com.izhi.platform.dao.ILogDao"%>
<%@page import="com.izhi.framework.dao.IFrameModelDao"%>
<%@page import="com.izhi.framework.model.FrameProject"%>
<%@page import="com.izhi.framework.model.FrameComponent"%>
<%@page import="com.izhi.framework.model.FrameModel"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.izhi.framework.service.IFrameProjectService"%>
<%@page import="com.izhi.framework.service.IFrameComponentService"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.izhi.framework.service.IFrameModelService"%>
<%@page import="com.izhi.platform.util.WebUtils"%>
<%@page import="com.izhi.framework.tag.TagUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="freemarker.template.Configuration"%>
<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>
<%@page import="freemarker.template.DefaultObjectWrapper"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="freemarker.template.Template"%>
<%@page import="freemarker.template.TemplateException"%>


<%@page import="com.izhi.framework.service.IFrameModelRelationService"%>
<%@page import="com.izhi.framework.model.FrameModelRelation"%>
<%@page import="com.izhi.platform.service.IOrgService"%>
<%@page import="com.izhi.platform.service.IRoleService"%>
<%@page import="com.izhi.platform.service.IPersonService"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		//out.println(JSONObject.fromObject(map).toString());
	IPersonService mrs=(IPersonService)wac.getBean("personService");
	PageParameter pp=new PageParameter();
	pp.setDir("desc");
	pp.setSort("personId");
	pp.setStart(0);
	pp.setLimit(10);
	List<Map<String,Object>> objs=mrs.findPage(pp,1);
	
	out.println(JSONArray.fromObject(objs).toString());
	/*
	PageParameter pp=new PageParameter();
	pp.setDir("desc");
	pp.setSort("relationId");
	pp.setStart(0);
	pp.setLimit(10);
	List<Map<String,Object>> list=mrs.findNoRelation(5);
	out.println(list.size());
	for(Map<String,Object> m:list){
		out.println(m.get("relationModelLabel"));
	}
	*/
	
	
/*
PageParameter pp=new PageParameter();
	pp.setDir("desc");
	pp.setSort("projectId");
	pp.setStart(0);
	pp.setLimit(10);
	List map=service.findPage(pp);
	out.println(service.findTotalCount());

	for(int i=0;i<map.size();i++){
		Map m=(Map)map.get(i);
		out.println(m.get("id")+" "+m.get("basePath")+"dddddddddddddddddddd<br>");
	}
	out.println(JSONArray.fromObject(map).toString()+"ddddddddddd");
	*/
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