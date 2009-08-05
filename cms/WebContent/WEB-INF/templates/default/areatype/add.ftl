<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>地区分组管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listSupplier">地区分组列表</a></td>
  </tr>
</table>
<form id="areaTypeForm" method="post"  action="${base}/areatype/save.jhtm">

<table class="table_form">
	<caption>地区分组信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>地区分组名称：</td><td><input type="text" id="areaTypeName" name="obj.areaTypeName" class="required"  title="请输入分组名称"></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>

<script language="javascript">
	$("#areaTypeName").focus();
	$("#listSupplier").click(function(){
		loadPage("${base}/areatype/list.jhtm");
	});
	$("#areaTypeForm").validate({
		success: function(label) {
			label.html("").addClass("success");
		},
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("地区分组信息保存成功！");
            		} else{
            			alert("地区分组信息保存失败！");
            		}
        		}});
			}
	});
	
</script>