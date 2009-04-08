<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>地区管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addArea">添加地区</a>&nbsp;|&nbsp;<a href="#" id="listArea">地区列表</a></td>
  </tr>
</table>
<form id="areaForm" method="post" action="${base}/area/save.jhtm">
<input type="hidden" name="obj.areaId" value="${obj.areaId}" >
<table class="table_form">
	<caption>地区信息</caption>
	<tr><td width="300"><font color="red">*</font>地区名称：</td><td><input type="text" id="areaName" name="obj.areaName" required="true" value="${obj.areaName}"  class="required" minlength="3" title="请输入地区名称！"></td></tr>
	<tr><td><font color="red">*</font>地区分组：</td><td><select id="areaTypeId" name="obj.areaType.areaTypeId" required="true" title="请选择地区分组！">
		<#list areaTypes as type>
			<option value="${type.areaTypeId}" <#if type.areaTypeId=obj.areaType.areaTypeId>selected</#if>>${type.areaTypeName}
		</#list>
	 </select></td></tr>
	<tr><td><font color="red">*</font>地区排序：</td><td><input type="text" id="sequence" name="obj.sequence"   class="required" number="true"  value="${obj.sequence}" title="请输入地区顺序，必须为数字！"></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="button" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#listArea").click(function(){
		loadPage("${base}/area/list.jhtm");
	});
	$('#addArea').click(function(){
		loadPage('${base}/area/add.jhtm');
	});
	$("#areaForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit(function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("地区保存成功！");
            		} else{
            			alert("地区保存失败！");
            		}
        		});
			}
	});
</script>