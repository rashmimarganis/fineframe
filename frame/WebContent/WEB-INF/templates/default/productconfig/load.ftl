<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品配置方案管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addProductConfig">添加商品配置方案</a>&nbsp;|&nbsp;<a href="#" id="listProductConfig">商品配置方案列表</a></td>
  </tr>
</table>
<form id="productConfigForm" method="post" action="${base}/productconfig/save.jhtm">
<input type="hidden" name="obj.productConfigId" value="${obj.productConfigId}" >
<table class="table_form">
	<caption>商品配置方案信息</caption>
	<tr>
		<td width="150" class="align_r"><font color="red">*</font>方案名称：</td>
		<td><input type="text" id="productConfigName" name="obj.productConfigName" value="${obj.productConfigName}" class="required" title="请输入方案名称！">
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r"><font color="red">*</font>缩略图高度：</td>
		<td><input type="text" id="thumbHeight" name="obj.thumbHeight" value="${obj.thumbHeight}" class="required"  number="true" min="0" title="请输入大于0的数字！">
		</td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>缩略图宽度：</td>
		<td><input type="text" id="thumbWidth" name="obj.thumbWidth"  value="${obj.thumbHeight}" class="required" number="true" min="0" title="请输入大于0的数字！">
		</td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>清晰图片高度：</td>
		<td><input type="text" id="imageHeight"  name="obj.imageHeight"  value="${obj.thumbHeight}"   class="required" number="true" min="0" title="请输入大于0的数字！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>清晰图片宽度：</td>
		<td><input id="imageWidth" name="obj.imageWidth"  value="${obj.thumbHeight}" class="required" number="true" min="0" title="请输入大于0的数字！">
		</td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>元/积分：</td>
		<td><input id="moneyPerScore" name="obj.moneyPerScore"   value="${obj.moneyPerScore}" class="required"  min="0" title="请输入大于0的数字！">
		</td>
	</tr>
	<tr>
		<td colspan="2" class="align_c">
			<input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">
			&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;">
		</td>
	</tr>
</table>
</form>
<script language="javascript">
	$("#productConfigName").focus();
	$("#listProductConfig").click(function(){
		loadPage("${base}/productconfig/list.jhtm");
	});
	$("#productConfigForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("商品配置保存成功！");
            		} else{
            			alert("商品配置保存失败！");
            		}
        		}});
			}
	});
	
</script>