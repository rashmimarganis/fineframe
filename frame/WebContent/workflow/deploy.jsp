<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/meta.jsp"></jsp:include>
<title>工作流部署管理</title>

</head>
<body>
<div id="loading-mask" style=""></div>

<div id="loading">
<div class="loading-indicator"><img
	src="../resources/images/default/shared/large-loading.gif" width="32"
	height="32" style="margin-right: 8px;" align="middle" /><label
	style="font-size: 13px;"><fmt:message key="waiting.message"/></label></div>
</div>
<jsp:include page="../common/js.jsp"></jsp:include>
<script type="text/javascript" src="../js/workflow/deploy.js"></script>
<div id="west"><div id="tree"></div></div>
<div id="center"><div id="grid"></div></div>
<div id="dlg" style="display:none">
<div id="dlgContent" >
<form id="deployForm" name="deployForm" method="post">
<table style="font-size:12px;">
	<tr>
		<td>名称：</td><td><input type="hidden"  id="flowMetaId" name="flowMetaId" value="0"><input type="hidden"  id="flowDeployID" name="obj.flowDeployID" value="-1"><input type="text"  id="flowDeployName" name="obj.flowDeployName" style="width:220px;">&nbsp;*</td>
	</tr>
	<tr>
		<td valign="top">备注：</td><td valign="top"><textarea  id="memo" name="obj.memo" style="width:220px;height:50px;"></textarea>&nbsp;*</td>
	</tr>
	<tr>
		<td valign="top">状态：</td><td valign="top"><select id="currentState" name="obj.currentState" ><option value='preparing'>未启用<option value='ready'>启用<option value='running'>运行</select>&nbsp;*</td>
	</tr>
</table>
</form>
</div>
</div>
</body>
</html>