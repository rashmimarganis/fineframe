<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>品牌类别管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addBrand">添加品牌类别</a>&nbsp;|&nbsp;<a href="#" id="listBrand">品牌列表</a></td>
  </tr>
</table>
<form id="brandForm" method="post" enctype="multipart/form-data" action="${base}/brand/save.jhtm">
<input type="hidden" name="obj.brandId" value="${obj.brandId}" >
<table class="table_form">
	<caption>添加品牌类别</caption>
	<tr><td><font color="red">*</font>品牌名称：</td><td><input type="text" id="brandName" name="obj.brandName" required="true" value="${obj.brandName}"  class="required" minlength="3" title="请输入品牌名称！"></td></tr>
	<tr><td><font color="red">*</font>品牌Logo：</td><td><img src="${base+(obj.brandLogo)!""}" width="80" height="80"><input type="hidden" name="obj.brandLogo" value="${obj.brandLogo!""}"><input type="file"  class="file_style" id="brandLogo" name="logo" required="true"></td></tr>
	<tr><td>官方网站：</td><td><input type="text" id="brandUrl" name="obj.brandUrl" required="true" value="${obj.brandUrl}"></td></tr>
	<tr><td><font color="red">*</font>品牌介绍：</td><td><textarea id="description" name="obj.description" class="required" minlength="3" title="请输入品牌介绍！">${obj.description}</textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="button" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#listBrand").click(function(){
		loadPage("${base}/brand/list.jhtm");
	});
	$('#addBrand').click(function(){
		loadPage('${base}/brand/add.jhtm');
	});
	$("#brandForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit(function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("品牌保存成功！");
            		} else{
            			alert("品牌保存失败！");
            		}
        		});
			}
	});
</script>