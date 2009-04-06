<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springside.examples.miniservice.ws.user.UserWebService"%>
<%@page import="org.springside.examples.miniservice.ws.user.dto.UserDTO"%>
<%@page import="java.util.HashSet"%>
<%@page import="org.springside.examples.miniservice.ws.user.dto.RoleDTO"%>
<%@page import="org.springside.examples.miniservice.entity.user.User"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.metadata.ClassMetadata"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.tools.ant.launch.Launcher"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.tools.ant.Project"%>
<%@page import="org.apache.tools.ant.ProjectHelper"%>
<%@page import="org.apache.catalina.util.ServerInfo"%>
<%@page import="org.apache.catalina.util.StringManager"%>
<%@page import="org.apache.catalina.manager.Constants"%>
<%@page import="org.apache.catalina.manager.StatusTransformer"%>
<%@page import="java.io.PrintWriter"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
/*
	WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	Date date=new Date(wac.getStartupDate());
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	out.println("系统启动时间："+sdf.format(date));
	out.println("Bean数："+wac.getBeanDefinitionCount());
	out.println("<br><hr>");
	SessionFactory client = (SessionFactory) wac.getBean("sessionFactory"); 
	Map<String,Object> allMetas=client.getAllClassMetadata();
	Set<String> alls=allMetas.keySet();
	//ClassMetadata catMeta =client.getClassMetadata(User.class);
	for(String a:alls){
		ClassMetadata catMeta=(ClassMetadata)allMetas.get(a);
		out.println("EntityName:"+catMeta.getEntityName()+"<br>");
		String[] as=catMeta.getPropertyNames();
	//String[] ps=;
		for(String s:as){
			out.println("Property:"+s+" Type:"+catMeta.getPropertyType(s).getName()+" Return Class:"+catMeta.getPropertyType(s).getReturnedClass()+"<br>");
		}
		out.println("<hr>");
	}
	
	*/
	File buildFile = new File("D:/eclipse3.3/groovy/lejia/WebContent/build.xml");
	Project p = new Project();
	p.init();
	ProjectHelper helper = ProjectHelper.getProjectHelper();
	helper.parse(p, buildFile);
	p.executeTarget("serverInfo");
	/*
	StringBuffer props=new StringBuffer();
	props.append("OK - Server info<br>");
    props.append("\nTomcat Version: ");
    props.append(ServerInfo.getServerInfo());
    props.append("<br>\nOS Name: ");
    props.append(System.getProperty("os.name"));
    props.append("<br>\nOS Version: ");
    props.append(System.getProperty("os.version"));
    props.append("<br>\nOS Architecture: ");
    props.append(System.getProperty("os.arch"));
    props.append("<br>\nJVM Version: ");
    props.append(System.getProperty("java.runtime.version"));
    props.append("<br>\nJVM Vendor: ");
    props.append(System.getProperty("java.vm.vendor"));
    props.append("<br>\nCatalina Base: ");
    props.append(System.getProperty("catalina.base"));
    props.append("<br>\nTemp Dir: ");
    props.append(((File) getServletContext().getAttribute
    ("javax.servlet.context.tempdir")).getAbsolutePath());
    props.append("<br>\ncatalina.home: ");
    props.append(System.getProperty("catalina.home"));
 	props.append("<hr><br>Free Memory:"+StatusTransformer.formatSize(Runtime.getRuntime().freeMemory(),true));
 	props.append("<br>Total Memory:"+StatusTransformer.formatSize(Runtime.getRuntime().totalMemory(),true));
 	props.append("<br>Max memory:"+StatusTransformer.formatSize(Runtime.getRuntime().maxMemory(),true));
 	out.println(props.toString());
    //StatusTransformer.writeOSState(new PrintWriter(out),0);
    //StatusTransformer.writeVMState(new PrintWriter(out),0);
    */

	
	
%>
</body>
</html>