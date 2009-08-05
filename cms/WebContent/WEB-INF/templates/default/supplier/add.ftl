<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>供应商管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listSupplier">供应商列表</a></td>
  </tr>
</table>
<form id="supplierForm" method="post"  action="${base}/supplier/save.jhtm">

<table class="table_form">
	<caption>供应商信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>供应商名称：</td><td><input type="text" id="supplierName" name="obj.supplierName" class="required" minlength="2" title="请输入供应商名称，至少两个字符！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>联系人：</td><td><input type="text" id="linkman" name="obj.linkman" class="required" minlength="3" title="请输入联系人！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>联系电话：</td><td><input type="text" id="telephone" name="obj.telephone" class="required"  minlength="13"  maxlenght="13" title="请输入联系电话，格式为：0531-12345678"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>手机号码：</td><td><input type="text"id="mobilephone" name="obj.mobilephone" number="true" minlength="11"  maxlenght="11" class="required" minlength="3" title="请输入手机号码，11位数字！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>电子邮件：</td><td><input type="text" id="email" name="obj.email" class="required" email="true" title="请输入供应商电子邮件，格式为email@test.com！！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>联系地址：</td><td><input type="text" id="address" name="obj.address" class="required" minlength="3" title="请输入供应商地址，至少三个字符！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>传真：</td><td><input type="text" id="fax" name="obj.fax" class="required" minlength="13"  maxlenght="13" title="请输入传真号码，格式为：0531-12345678！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>邮政编码：</td><td><input type="text" id="postcode" name="obj.postcode" class="required" minlength="6"  maxlength="6"  title="请输入6位数字邮政编码！"></td></tr>
	<tr><td class="align_r">备注：</td><td><textarea id="note" name="obj.note" ></textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>

<script language="javascript">
	$("#supplierName").focus();
	$("#listSupplier").click(function(){
		loadPage("${base}/supplier/list.jhtm");
	});
	$("#supplierForm").validate({
		success: function(label) {
			label.html("").addClass("success");
		},
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("供应商信息保存成功！");
            		} else{
            			alert("供应商信息保存失败！");
            		}
        		}});
			}
	});
	
</script>