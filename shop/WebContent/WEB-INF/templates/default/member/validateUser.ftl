<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册用户</title>
<link rel="stylesheet" type="text/css" href="${base}/skin/default/form.css" />
<script type="text/javascript" src="${base}/js/jquery.js"></script>
<script type="text/javascript" src="${base}/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/js/jquery.validate.js"></script>
</head>
<body>
<script language="javascript">
 	eval("var o="+'${result}');
	if(o.validated){
		document.write("账户已经通过验证，请不要重复验证！<a href='${base}/index.jhtm'>返回首页</a>");
	}else{
		if(o.success){
			document.write("账户验证通过！");
		}else{
			document.write("账户验证不通过！<a href='${base}/index.jhtm'>返回首页</a>");
		}
	}
</script>
</body>
</html>