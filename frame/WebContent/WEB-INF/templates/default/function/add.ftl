<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>组织管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listOrg">组织列表</a></td>
  </tr>
</table>
<form id="shopForm" method="post" action="${base}/org/save.jhtm">
<table class="table_form">
	<caption>组织信息</caption>
	
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>组织名称：
		</td>
		<td>
			<input type="text" id="title" name="obj.title" class="required" title="请输入组织名称！">
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>分类英文名称：
		</td>
		<td>
			<input type="text" id="orgName" name="obj.orgName" class="required"  title="请输入组织英文名称！">
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
	$("#orgName").focus();
	$("#listOrg").click(function(){
		loadPage("${base}/org/list.jhtm");
	});
	$("#orgForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("组织保存成功！");
            		} else{
            			alert("组织保存失败！");
            		}
        		}});
			}
	});
	
</script>