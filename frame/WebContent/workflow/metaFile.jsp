<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/meta.jsp"></jsp:include>
<title>流程文件管理</title>

</head>
<body>
<div id="loading-mask"></div>

<div id="loading">
<div class="loading-indicator"><img
	src="../resources/images/default/shared/large-loading.gif" width="32"
	height="32" style="margin-right: 8px;" align="middle" /><label
	style="font-size: 13px;"><fmt:message key="waiting.message"/></label></div>
</div>
<jsp:include page="../common/js.jsp"></jsp:include>

<div id="west"><div id="tree"></div></div>
<div id="center"><div id="grid"></div></div>
<div id="dlg" style="display:none">
	<div id="dlgContent">
		<form id="uploadForm" name="uploadForm" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>流程文件：</td><td><input type="file"  id="metaFile" name="metaFile"></td>
			</tr>
			<tr>
				<td>预览图片：</td><td><input type="file"  id="imageFile" name="imageFile"></td>
			</tr>
			<tr>
				<td>所属类型：</td><td><select id="businessType" name="typeId" style="display:none;"></select></td>
			</tr>
		</table>
		</form>
	</div>
</div>
<script type="text/javascript" src="../js/workflow/metaFile.js"></script>
</body>
</html>