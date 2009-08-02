<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>模板管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listTemplate">模板列表</a></td>
  </tr>
</table>
<form id="templateForm" method="post" enctype="multipart/form-data" action="${base}/template/save.jhtm">
<table class="table_form">
	<caption>模板信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>模板名称：</td><td><input type="text" id="templateName" name="obj.templateName" class="required" minlength="2" title="<font color='red'>&nbsp;请输入模板名称，至少两个字符！</font>"></td></tr>
	<tr><td class="align_r">模板Logo：</td><td><input type="file" id="templateLogo" name="logo" class="file_style"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>官方网站：</td><td><input type="text" id="templateUrl" class="url" name="obj.templateUrl"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>模板介绍：</td><td><textarea id="description" name="obj.description" class="required" minlength="3" title="<font color='red'>&nbsp;请输入模板介绍，至少三个字符！</font>"></textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#templateName").focus();
	$("#listBrand").click(function(){
		loadPage("${base}/template/list.jhtm");
	});
	$("#templateForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("模板保存成功！");
            		} else{
            			alert("模板保存失败！");
            		}
        		}});
			}
	});
	
</script>