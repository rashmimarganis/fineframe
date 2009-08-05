<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.izhi.platform.webapp.filter.CookieRememberFilter"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<% 
	Cookie[] cks=request.getCookies();
	String un="";
	String check="";
	String remember="false";
	if(cks!=null){
		for(Cookie ck:cks){
			if(CookieRememberFilter.Cookie_Username.equals(ck.getName())){
				un=ck.getValue();
			}else if(CookieRememberFilter.Cookie_Check.equals(ck.getName())){
				remember=ck.getValue();
			}
		}
	}
	if(remember.equals("true")){
		check="checked";
	}else{
		check="";
	}
	
%>
</head>
<body>
<form action="j_security_check" method="post">
<table >
<tr>
<td>用户名：</td><td><input type="text" name="j_username" value="<%=un%>"></td>
</tr>
<tr>
<td>密码：</td><td><input type="password" name="j_password"></td>
</tr>
<tr>
<td>验证码:</td><td><input type="text" name="j_captcha_response"><img src="captcha.jpg"></td>
</tr>
<tr>
<td><input type="checkbox" name="rememberme" checked>&nbsp;记住我</td>
</tr>
<tr><td><input type="submit" value="提交"></td></tr>
</table>
</form>
</body>
</html>