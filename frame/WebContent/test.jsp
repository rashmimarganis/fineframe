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
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.izhi.framework.service.IFrameProjectService"%>
<%@page import="com.izhi.framework.service.IFrameTemplateService"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.izhi.framework.service.IFrameModelService"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	IFrameTemplateService templateService = (IFrameTemplateService) wac.getBean("frameTemplateService"); 
	Map<String,Object> map=new HashMap<String, Object>();
	map.put("objs", templateService.findJsonByType("control"));
	map.put("totalCount", templateService.findTotalCountByType("control"));
	out.println(JSONObject.fromObject(map).toString());
	IFrameModelDao modelService=(IFrameModelDao)wac.getBean("frameModelDao");
	List<Integer> l=new ArrayList<Integer>();
	PageParameter pp=new PageParameter();
	pp.setDir("desc");
	pp.setSort("modelId");
	pp.setStart(0);
	pp.setLimit(10);
	List<Map<String,Object>> map1=modelService.findPage(pp);
	for(Map<String,Object> m:map1){
		out.println(m);
	}
	out.println(modelService.findPage(pp).size());
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