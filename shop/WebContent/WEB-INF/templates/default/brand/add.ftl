<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>品牌类别管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listBrand">品牌类别列表</a></td>
  </tr>
</table>
<form id="brandForm" method="post" enctype="multipart/form-data" action="${base}/brand/save.jhtm">
<table class="table_form">
	<caption>品牌信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>品牌名称：</td><td><input type="text" id="brandName" name="obj.brandName" class="required" minlength="2" title="<font color='red'>&nbsp;请输入品牌名称，至少两个字符！</font>"></td></tr>
	<tr><td class="align_r">品牌Logo：</td><td><input type="file" id="brandLogo" name="logo" class="file_style"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>官方网站：</td><td><input type="text" id="brandUrl" name="obj.brandUrl"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>品牌介绍：</td><td><textarea id="description" name="obj.description" class="required" minlength="3" title="<font color='red'>&nbsp;请输入品牌介绍，至少三个字符！</font>"></textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#brandName").focus();
	$("#listBrand").click(function(){
		loadPage("${base}/brand/list.jhtm");
	});
	$("#brandForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("品牌保存成功！");
            		} else{
            			alert("品牌保存失败！");
            		}
        		}});
			}
	});
	
</script>