<html>
<head>
  <title>${site.siteName}</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8>">
  	<link rel="stylesheet" type="text/css" href="${base}/skin/default/main.css" />
	<link rel="stylesheet" type="text/css" href="${base}/skin/default/system.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/js/resources/css/ext-all.css" />
	<#if site.siteStyle!="default">
	<link rel="stylesheet" type="text/css" href="${base}/js/resources/css/xtheme-${site.siteStyle}.css" />
	</#if>
	<link rel="stylesheet" type="text/css" href="${base}/style.jhtm" />
 	
</head>
<body >
<div id="loading-mask" style=""></div>

<div id="loading">
    <div class="loading-indicator">
    <img src="js/resources/images/default/shared/large-loading.gif" width="32" height="32" style="margin-right:8px;" align="middle"/>
    <label style="font-size:13px;">正在加载...</label>
    </div>
</div>

<script type="text/javascript" src="${base}/js/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${base}/js/ext-all-debug.js"></script>
    <script type="text/javascript" src="${base}/js/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${base}/js/frame/common.js"></script>
    <script type="text/javascript" src="js/ComboBoxTree.js"></script>
    <script type="text/javascript" src="js/CheckBoxTree.js"></script>
	<script language="javascript" type="text/javascript" src="${base}/script.jhtm">
	</script>
	<script type="text/javascript" src="js/frame/attribute.js"></script>
	<script type="text/javascript" src="js/frame/modelrelation.js"></script>

  <div id="west">
  </div>
  <div id="north">
     <div class="logo"><a href="${base}" target="_blank"><img src="${base}/skin/default/images/logo.jpg" width="220" height="58" border="0"/></a></div>
  <p id="info_bar"  > 用户名：<strong class="font_arial white">
      ${user.username}</strong>    | <a href="${base}/logout.jhtm" class="white">退出登录</a> | <a href="${base}" class="white" target="_blank">网站首页</a></p>
  
  </div>
  <div id="main">
     
  </div>
 
  
  <div id="south" style="background-color:#cedff5;height:'30px';vertical-align:middle;margin:0px 0px 0px 0px;">
  <div id="loginInfo"  style="background-color:#cedff5;height:'30px';vertical-align:middle;margin:0px 0px 0px 0px;">当前用户:<#if user.person??>${user.username}[${user.person.realname}]<#else>${user.username}</#if> 登录组织:${org.name} <#if user.lastLoginIp??>上次登录IP：${user.lastLoginIp}</#if> 上次登录时间：<#if user.lastLoginTime??>${user.lastLoginTime?string('yyyy年MM月dd日 H时m分s秒 ')}</#if> 共登录过  ${user.loginTimes} 次</div>
  </div>

 </body>
</html>
