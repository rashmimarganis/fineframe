<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page
	import="org.springside.examples.miniservice.ws.user.UserWebService"%>
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
<%@page import="org.apache.tomcat.util.modeler.Registry"%>
<%@page import="javax.management.ObjectName"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.management.ObjectInstance"%>
<%@page import="java.util.Vector"%>
<%@page import="javax.management.MBeanServer"%>
<%@page import="javax.management.MBeanServerFactory"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	Vector protocolHandlers = new Vector();
	Vector threadPools = new Vector();
	Vector requestProcessors = new Vector();
	Vector globalRequestProcessors = new Vector();
	MBeanServer mBeanServer = Registry.getRegistry(null, null)
			.getMBeanServer();
	String onStr = "*:type=ProtocolHandler,*";
	ObjectName objectName = new ObjectName(onStr);
	Set set = mBeanServer.queryMBeans(objectName, null);
	Iterator iterator = set.iterator();
	while (iterator.hasNext()) {
		ObjectInstance oi = (ObjectInstance) iterator.next();
		protocolHandlers.addElement(oi.getObjectName());
	}

	// Query Thread Pools
	onStr = "*:type=ThreadPool,*";
	objectName = new ObjectName(onStr);
	set = mBeanServer.queryMBeans(objectName, null);
	iterator = set.iterator();
	out.println("Thread Pool:<br>");
	while (iterator.hasNext()) {
		ObjectInstance oi = (ObjectInstance) iterator.next();
		ObjectName name = oi.getObjectName();
		Object kp=null;
		try{
			kp=mBeanServer.getAttribute(name, "keepAliveCount");
		}catch(Exception e){
			
		}
		out.println(name.getKeyProperty("name")+"  "+"MaxTreads:"+mBeanServer.getAttribute(oi.getObjectName(), "maxThreads")+" Current Thread:"+mBeanServer.getAttribute(name, "currentThreadCount")+" Current thread busy: "+mBeanServer.getAttribute(name, "currentThreadsBusy")+" Keep Alive Count:"+kp+"<br>");
		
		threadPools.addElement(oi.getObjectName());
	}
	
	// Query Global Request Processors
	onStr = "*:type=GlobalRequestProcessor,*";
	objectName = new ObjectName(onStr);
	set = mBeanServer.queryMBeans(objectName, null);
	iterator = set.iterator();
	out.println("GlobalRequestProcessor:<br>");
	while (iterator.hasNext()) {
		ObjectInstance oi = (ObjectInstance) iterator.next();
		String name = oi.getObjectName().getKeyProperty("name");
		out.println(name+"<br>");
		globalRequestProcessors.addElement(oi.getObjectName());
	}

	// Query Request Processors
	onStr = "*:type=RequestProcessor,*";
	objectName = new ObjectName(onStr);
	set = mBeanServer.queryMBeans(objectName, null);
	iterator = set.iterator();
	out.println("RequestProcessor:<br>");
	while (iterator.hasNext()) {
		ObjectInstance oi = (ObjectInstance) iterator.next();
		String name = oi.getObjectName().getKeyProperty("name");
		out.println(name+"<br>");
		requestProcessors.addElement(oi.getObjectName());
	}
	
	
%>
</body>

</html>