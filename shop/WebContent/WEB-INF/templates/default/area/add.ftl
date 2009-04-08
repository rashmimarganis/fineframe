<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>地区管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listArea">地区列表</a></td>
  </tr>
</table>
<form id="areaForm" method="post" action="${base}/area/save.jhtm">
<table class="table_form">
	<caption>地区信息</caption>
	<tr><td width="300" class="align_r"><font color="red">*</font>地区名称：</td><td><input type="text" id="areaName" name="obj.areaName" class="required" title="请输入地区名称！"></td></tr>
	<tr><td class="align_r">地区分组：</td><td><select id="areaType" name="obj.areaType.areaTypeId" class="required" title="请选择地区分组！">
		<#list areaTypes as t>
			<option value="${t.areaTypeId}">${t.areaTypeName}
		</#list>
	</select></td></tr>
	<tr><td class="align_r"><font color="red">*</font>排序：</td><td><input type="text" id="sequence" value="0" class="required" number="true" name="obj.sequence" title="请输入地区顺序，必须为数字！"></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#areaName").focus();
	$("#listArea").click(function(){
		loadPage("${base}/area/list.jhtm");
	});
	$("#areaForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("地区保存成功！");
            		} else{
            			alert("地区保存失败！");
            		}
        		}});
			}
	});
	
</script>