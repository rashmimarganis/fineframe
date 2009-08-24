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

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	IFrameProjectService ps = (IFrameProjectService) wac.getBean("frameProjectService"); 
		//out.println(JSONObject.fromObject(map).toString());
	IFrameModelService ms=(IFrameModelService)wac.getBean("frameModelService");
	IFrameComponentService cs=(IFrameComponentService)wac.getBean("frameComponentService");
	
	FrameProject fp=ps.findProjectById(1);
	FrameComponent fc=cs.findComponentById(1);
	FrameModel fm=ms.findModelById(5);
	
	out.println(TagUtils.getGenerateFileName(fp,fc,fm));
	
	if (fc != null) {
		Configuration config1=new Configuration();
		try {
			config1.setDirectoryForTemplateLoading(new File(WebUtils.getFrameTemplateRoot()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		config1.setObjectWrapper(new DefaultObjectWrapper());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("p", fp);
		String ftpl =fc.getTemplate().getFileName()+".ftl";

		if (fc.getLevel().equals(FrameComponent.LEVEL_PROJECT)) {
			String fileName = TagUtils.getGeneratePackageName(fp, fc)
					+ TagUtils.getGenerateFileName(fp, fc);
			File file = new File(fileName);
			try {
				if (!file.exists()) {

					file.createNewFile();

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Writer out1 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "UTF-8"));
				Template tpl = config1.getTemplate(ftpl);
				tpl.process(data, out1);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}

		} else {
			List<FrameModel> models = ms.findModelByProject(1);
			
			for (FrameModel m : models) {
				data.put("m", m);
				String fileName = TagUtils.getGeneratePackageName(fp, fc,fm)
				+ TagUtils.getGenerateFileName(fp, fc,fm);
				File file = new File(fileName);
				try {
					if (!file.exists()) {

						file.createNewFile();

					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Writer out1 = new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(file), "UTF-8"));
					Template tpl = config1.getTemplate(ftpl);
					tpl.process(data, out);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
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