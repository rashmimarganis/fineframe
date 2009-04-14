<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>店铺管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listShop">店铺列表</a></td>
  </tr>
</table>
<form id="shopForm" method="post" action="${base}/shop/save.jhtm">
<table class="table_form">
	<caption>店铺信息</caption>
	
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>店铺名称：
		</td>
		<td>
			<input type="text" id="title" name="obj.title" class="required" title="请输入店铺名称！">
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>分类英文名称：
		</td>
		<td>
			<input type="text" id="shopName" name="obj.shopName" class="required"  title="请输入店铺英文名称！">
		</td>
	</tr>
	
	<tr>
		<td class="align_r"><font color="red">*</font>显示顺序：</td>
		<td><input type="text" id="sort" class="required" name="obj.sort" number="true" value="0" title="请输入数字！"></td>
	</tr>
	
	
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#shopName").focus();
	$("#listShop").click(function(){
		loadPage("${base}/shop/list.jhtm");
	});
	$("#shopForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("店铺保存成功！");
            		} else{
            			alert("店铺保存失败！");
            		}
        		}});
			}
	});
	
</script>