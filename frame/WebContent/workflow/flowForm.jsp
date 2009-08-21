<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/meta.jsp"></jsp:include>
<title>工作流表单管理</title>

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
<script type="text/javascript" src="../js/workflow/flowForm.js"></script>
<div id="west"><div id="tree"></div></div>
<div id="center"><div id="grid"></div></div>
<div id="dlg" style="display:none">
<div id="dlgContent" >
<form id="driverForm" name="driverForm" method="post">
<table style="font-size:12px;">
	<tr>
		<td>表单名称：</td><td><input type="hidden"  id="flowDriverID" name="obj.flowDriverID" value="-1"><input type="text"  id="flowDriverName" name="obj.flowDriverName" style="width:220px;">&nbsp;*</td>
	</tr>
	<tr>
		<td valign="top">读取路径:</td><td valign="top"><input type="text" id="readURL" name="obj.readURL" style="width:220px;">&nbsp;*</td>
	</tr>
	<tr>
		<td valign="top">写入路径:</td><td valign="top"><input type="text" id="writeURL" name="obj.writeURL" style="width:220px;">&nbsp;*</td>
	</tr>
	<tr>
		<td valign="top">表单分类:</td><td valign="top"><input type="text" id="contextPath" name="obj.contextPath" style="width:220px;">&nbsp;*</td>
	</tr>
	<tr>
		<td valign="top">描述：</td><td valign="top"><textarea  id="memo" name="obj.memo" style="width:220px;height:50px;"></textarea>&nbsp;*</td>
	</tr>
</table>
</form>
</div>
</div>

<div id="cfgDlg" style="display:none;">
	<div id="cfgDlgContent">
		<div id="nodeTree"></div>
		<div id="nodeInfo"></div>
	</div>
</div>

<div id="inputParamGrid">
</div>
<div id="outputParamGrid">
</div>
<input type="hidden" id="driverId" name="driverId" value="0">
<div id="inputParamDlg" style="display:none;">
	<div id="inputParamContent" >
		<form id="inputParamForm">
		<table style="font-size:12px;">
			<tr><td>参数ID：</td><td><input type="hidden" id="driverInputParamID" name="obj.driverInputParamID" value="-1"><input type="text" id="paramName" name="obj.paramName" ></td></tr>
			<tr><td>参数名称：</td><td><input type="text" id="paramAlias" name="obj.paramAlias" ></td></tr>
		</table>
		</form>
	</div>
</div>
<div id="outputParamDlg" style="display:none;">
	<div id="outputParamContent">
	<form id="outputParamForm">
		<table style="font-size:12px;">
			<tr><td>参数ID：</td><td><input type="hidden" id="driverOutputParamID" name="obj.driverOutputParamID" value="-1"><input type="text" id="outPutParamName" name="obj.paramName" ></td></tr>
			<tr><td>参数名称：</td><td><input type="text" id="outputParamAlias" name="obj.paramAlias" ></td></tr>
		</table>
		</form>
	</div>
</div>
</body>
</html>