<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${site.siteName}</title>
</head>
<body>
<form action="j_security_check" method="post">
<table >
<tr>
<td>用户名：</td><td><input type="text" name="j_username" value="<#if username??>${username}</#if>" autocomplete="off"></td>
</tr>
<tr>
<td>密码：</td><td><input type="password" name="j_password"></td>
</tr>
<tr>
<td>验证码:</td><td><input type="text" name="j_captcha_response" autocomplete="off"><img src="captcha.jpg"></td>
</tr>
<tr>
<td><input type="checkbox" name="rememberme" ${rememberMe?default("")}>&nbsp;记住我</td>
</tr>
<tr><td><input type="submit" value="提交"></td></tr>
</table>
</form>
</body>
</html>