<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.secoo.platform.security.support.SecurityUser"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>FineCMS网站管理系统 - Powered by FineCMS 2008</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8>">
<link rel="stylesheet" type="text/css" href="themes/base/ui.all.css" />
<link rel="stylesheet" type="text/css" href="skin/default/main.css" />
<link rel="stylesheet" type="text/css" href="skin/default/form.css" />
<link rel="stylesheet" type="text/css" href="skin/default/system.css"/>
<link rel="stylesheet" type="text/css" href="skin/default/treeview.css"/>

<style>

</style>
</head>
<body scroll="no">
<!--head-->
<div id="header">
  <div class="logo"><a href="<%=request.getContextPath()%>" target="_blank"><img src="skin/default/images/logo.jpg" width="220" height="58" border="0"/></a></div>
  <p id="info_bar"> 用户名：<strong class="font_arial white">
    <%=SecurityUser.getUsername() %>   </strong>，角色：
    超级管理员    | <a href="<%=request.getContextPath()%>/logout.jsp" class="white">退出登录</a> | <a href="<%=request.getContextPath()%>/" class="white" target="_blank">网站首页</a></p>
  <div id="menu">
    <ul>
	        <li><a href="javascript:loadMenu(2,'我的面板')" id="menu_2" class="menu selected" alt="我的面板"><span>我的面板</span></a></li>
	        <li><a href="javascript:loadMenu(1,'系统设置')" id="menu_1" class="menu" alt="系统设置"><span>系统设置</span></a></li>
	        <li><a href="javascript:loadMenu(3,'内容管理')" id="menu_3" class="menu" alt="内容管理"><span>内容管理</span></a></li>
	        <li><a href="javascript:loadMenu(5,'模板风格')" id="menu_5" class="menu" alt="模板风格"><span>模板风格</span></a></li>
	  
	</ul>
  </div>
</div><!--header over-->
<DIV id=woadd>
	<h4 class="inner"><span id="menu_name">我的面板</span></h4>
	<div id="inner" class="inner">
    <!--导航-->
    <span class="btn_menu">
		        <a href="javascript:show_map();show_div(this)" id="show_map" title="后台管理地图"><img src="<%=request.getContextPath()%>/skin/default/images/icon_9.gif" title="后台管理地图" height="22" width="22" /></a>
        <a href="javascript:add_menu()" title="添加常用操作"><img src="skin/default/images/icon_1.gif" title="添加常用操作" height="22" width="22" /></a>
		<a href="javascript:search_menu()"><img src="skin/default/images/icon_2.gif" title="菜单搜索" height="22" width="22"  /></a>
		<a href="javascript:get_memo()" onclick="show_div('memo')"><img src="<%=request.getContextPath()%>/skin/default/images/icon_7.gif" title="备忘录" height="22" width="22" /></a>
	</span>
	<input type="hidden" name="ismsgopen" id="ismsgopen" value="0" /> 
    <div id="new_msg"><img src="<%=request.getContextPath()%>/skin/default/images/s.gif" alt="查看新消息" style="width:49px;height:20px;margin-right:-2px;"  /><img src="<%=request.getContextPath()%>/skin/default/images/close_1.gif" alt="关闭" style="margin:5px 15px;" /></div>
 
	<div id="msg_div" class="div">
	<div class="content_i">
		<ul id="adminlist">
		</ul>
	<div class="footer">
		<div class="footer_left"></div>
		<div class="footer_right"></div>
		<div>
			<div class="btn"><a href="outbox.php?userid=1" target="right"><img src="<%=request.getContextPath()%>/skin/default/images/btn_sjx.gif" width="54" height="24" /></a></div>
			<div class="btn"><a href="inbox.php?userid=1"  target="right"><img src="<%=request.getContextPath()%>/skin/default/images/btn_fjx.gif" width="54" height="24" /></a></div>
		</div>
	</div>
	</div>
	</div>
    <div id="search_menu" class="div">
      <input type="text" name="menu_key" id="menu_key" value="请输入菜单名称" onblur="if($(this).val()=='')$(this).val('请输入菜单名称')" onfocus="if($(this).val()=='请输入菜单名称')$(this).val('')" size="30" />
      <div id="floor"></div>
    </div>
    <div id="memo" class="div">
	<div id="memo_mtime" style="text-align:right;padding-right:10px;"></div>
    <textarea id="memo_data" name="memo_data" rows="10" cols="50" style="padding:5px" onblur="set_memo(this.value)">
    </textarea>
    </div>
    <div id="position"><strong>当前位置：</strong><span id="firstMenu"><a href="javascript:get_menu(2,'tree',0);">我的面板</a></span><span id="secondMenu"></span><span id="thirdMenu"></span></div>
  </div>
	
</DIV><!--woadd over-->
<div id="admin_left" style="height:450px;">

	<div id="nextMenus" style="overflow: auto;">
   
	</div>
</div><!--admin_left over-->
<div id="admin_right" style="z-index:0;">
      <div name="right" id="right" style="overflow: auto;width:100%;height:450px;">  </div>
	 
</div>

</div><!--admin_right over-->
<DIV class="loading" style="display:none;">
	<DIV class="loadOut">
		<div class="loadInner">  
		<img src="images/share/blue-loading.gif"  width="20" height="20" /><span>正在加载...</span> 
		</div>  
	</DIV>
</DIV>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src=js/treeview.js"></script>
<script type="text/javascript" src="js/jquery.ui.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javaScript" src="js/common.js"></script>
<script type="text/javascript" src="js/menu.js"></script>
<script type="text/javascript" src="fckeditor/fckeditor.js"></script>

</body>
</html>
