<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统出现异常</title>
</head>
<body>
	<h2>An unexpected error has occurred</h2>
  <p>
    Please report this error to your system administrator
    or appropriate technical support personnel.
    Thank you for your cooperation.
  </p>
  <hr/>
  <h3>Error Message</h3>
    <s:actionerror/>
    <p>
      <s:property value="%{exception.message}"/>
    </p>
    <hr/>
    <h3>Technical Details</h3>
    <p>
      <s:property value="%{exceptionStack}"/>
    </p>
</body>
</html>