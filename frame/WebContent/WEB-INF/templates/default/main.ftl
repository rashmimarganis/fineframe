<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${site.name}- Powered by FineCMS 2008</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8>">
<link rel="stylesheet" type="text/css" href="${base}/skin/default/main.css" />
<link rel="stylesheet" type="text/css" href="${base}/skin/default/form.css" />
<link rel="stylesheet" type="text/css" href="${base}/skin/default/system.css"/>
<link rel="stylesheet" type="text/css" href="${base}/skin/default/treeview.css"/>
<link rel="stylesheet" type="text/css" href="${base}/themes/base/ui.all.css" />
</head>
<body scroll="no">
<!--head-->
<div id="header">
  <div class="logo"><a href="${base}" target="_blank"><img src="${base}/skin/default/images/logo.jpg" width="220" height="58" border="0"/></a></div>
  <p id="info_bar"> 用户名：<strong class="font_arial white">
    ${user.username}   </strong>，角色：
    超级管理员    | <a href="${base}/logout.jsp" class="white">退出登录</a> | <a href="${base}" class="white" target="_blank">网站首页</a></p>
  <div id="menu">
    <ul>
    <#if topFunctions?exists>
    		<#list topFunctions as fun>
                <li><a href="javascript:loadMenu('${fun.functionId}','${fun.functionTitle}')" id="menu_${fun.functionId}"<#if fun_index==0> class="menu selected"<#else> class="menu"</#if> alt="${fun.functionTitle}"><span>${fun.functionTitle}</span></a></li>
            </#list>
	 </#if>  
	</ul>
  </div>
</div><!--header over-->
<DIV id=woadd>
	<h4 class="inner"><span id="menu_name">我的面板</span></h4>
	<div id="inner" class="inner">
    <!--导航-->
    <span class="btn_menu">
		        <a href="javascript:show_map();show_div(this)" id="show_map" title="后台管理地图"><img src="${base}/skin/default/images/icon_9.gif" title="后台管理地图" height="22" width="22" /></a>
        <a href="javascript:add_menu()" title="添加常用操作"><img src="${base}/skin/default/images/icon_1.gif" title="添加常用操作" height="22" width="22" /></a>
		<a href="javascript:search_menu()"><img src="${base}/skin/default/images/icon_2.gif" title="菜单搜索" height="22" width="22"  /></a>
		<a href="javascript:get_memo()" onclick="show_div('memo')"><img src="${base}/skin/default/images/icon_7.gif" title="备忘录" height="22" width="22" /></a>
	</span>
	<input type="hidden" name="ismsgopen" id="ismsgopen" value="0" /> 
    <div id="new_msg"><img src="${base}/skin/default/images/s.gif" alt="查看新消息" style="width:49px;height:20px;margin-right:-2px;"  /><img src="${base}/skin/default/images/close_1.gif" alt="关闭" style="margin:5px 15px;" /></div>
 
	<div id="msg_div" class="div">
	<div class="content_i">
		<ul id="adminlist">
		</ul>
	<div class="footer">
		<div class="footer_left"></div>
		<div class="footer_right"></div>
		<div>
			<div class="btn"><a href="outbox.php?userid=1" target="right"><img src="${base}/skin/default/images/btn_sjx.gif" width="54" height="24" /></a></div>
			<div class="btn"><a href="inbox.php?userid=1"  target="right"><img src="${base}/skin/default/images/btn_fjx.gif" width="54" height="24" /></a></div>
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
		<img src="${base}/skin/default/images/blue-loading.gif"  width="20" height="20" /><span>正在加载...</span> 
		</div>  
	</DIV>
</DIV>

<script type="text/javascript" src="${base}/js/jquery.js"></script>
<script type="text/javascript" src="${base}/js/treeview.js"></script>
<script type="text/javascript" src="${base}/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/js/jquery.validate.js"></script>
<script type="text/javaScript" src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/menu.js"></script>
<script type="text/javascript" src="${base}/ui/ui.core.js"></script>
<script type="text/javascript" src="${base}/ui/ui.datepicker.js"></script>
<script type="text/javascript" src="${base}/ui/ui.dialog.js"></script>
<script type="text/javascript" src="${base}/ui/i18n/ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${base}/fckeditor/fckeditor.js"></script>
<#if (topFunctions.size()>0)>
<script language="javascript">
	loadMenu('${topFunctions[0].functionId}','${topFunctions[0].functionTitle}');
</script>
</#if>
</body>
</html>
