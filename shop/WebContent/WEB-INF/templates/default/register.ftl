<#assign  cms =JspTaglibs["/WEB-INF/tag/cms.tld"] >
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册用户</title>
<link rel="stylesheet" type="text/css" href="${base}/skin/default/form.css" />
<script type="text/javascript" src="${base}/js/jquery.js"></script>
<script type="text/javascript" src="${base}/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/js/jquery.validate.js"></script>
</head>
<body style="">
	<form id="form1" action="${base}/member/register.jhtm" method="post">
	<table width="100%" cellpadding="0" cellspacing="1" class="table_form">

		<tr><td><font color="red">*</font>用户名：</td><td><input type="input" id="username" name="obj.username" class="required" title="用户名不合法或已存在！" remote="${base}/member/available.jhtm"></td></tr>
		<tr><td><font color="red">*</font>密码：</td><td><input type="password" id="password" name="obj.password" class="required" title="密码长度为6-16个字符" minlength=6 maxlength=16></td></tr>
		<tr><td><font color="red">*</font>确认密码：</td><td><input type="password" id="password1" class="required name="password1" title="两次输入密码不一致"  minlength=6 maxlength=16 equalTo="#password"></td></tr>
		<tr><td><font color="red">*</font>电子邮件：</td><td><input type="text" id="email" class="required" name="obj.email" class="required" email="true" title="email格式不正确！"></td></tr>
		<tr><td><font color="red">*</font>密码问题：</td><td><input type="text" id="hintQuestion" class="required" name="obj.hintQuestion" title="密码问题不能为空！"></td></tr>
		<tr><td><font color="red">*</font>密码答案：</td><td><input type="text" id="hintAnswer" class="required" name="obj.hintAnswer" title="密码答案不能为空！"></td></tr>
		<tr valign="bottom"><td><font color="red">*</font>验证码：</td><td valign="bottom"><span><input type="text" id="code" name="code"  class="required" minlength=4 maxlength=4 title="输入4位验证码！"></span><span><img src="captcha.jpg"></span></td></tr>
		<tr><td colspan="2"><input type="submit" class="button_style" value="提交">&nbsp;&nbsp;<input type="reset"  class="button_style" value="重置"></td></tr>
	</table>
	</form>
	<script language="javascript">
		$().ready(
			function(){
				$("#form1").validate({
					success: function(label) {
						label.html("").addClass("success");
					},
					submitHandler: function(form) {
						$(form).ajaxSubmit({
							clearForm: true,
							success:function(o) { 
			            		eval("var r="+o);
			            		if(r.success){
			            			alert("注册成功！");
			            			window.location.href="index.jhtm";
			            		} else{
			            			alert("对不起，注册过程中出现了问题，注册失败了！");
			            		}
			        		}});
					},
					onkeyup: false
				});
			}
		);
	</script>
</body>
</html>