<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>供应商管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addSupplier">添加供应商</a>&nbsp;|&nbsp;<a href="#" id="listSupplier">供应商列表</a></td>
  </tr>
</table>
<form id="supplierForm" method="post"  action="${base}/supplier/save.jhtm">
<input type="hidden" name="obj.supplierId" value="${obj.supplierId}" >
<table class="table_form">
	<caption>供应商信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>供应商名称：</td><td><input type="text" value="${obj.supplierName}" id="supplierName" name="obj.supplierName" class="required" minlength="2" title="<font color='red'>&nbsp;请输入供应商名称，至少两个字符！</font>"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>联系人：</td><td><input type="text" id="linkman" name="obj.linkman" value="${obj.linkman}"  class="required" minlength="3" title="<font color='red'>&nbsp;请输入联系人！</font>"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>联系电话：</td><td><input type="text" id="telephone" name="obj.telephone"  value="${obj.telephone}" class="required" minlength="3" title="<font color='red'>&nbsp;请输入联系电话，格式为：0531-12345678</font>"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>手机号码：</td><td><input type="text"id="mobilephone" name="obj.mobilephone" value="${obj.mobilephone}" class="required" minlength="3" title="<font color='red'>&nbsp;请输入手机号码，11位数字！</font>"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>电子邮件：</td><td><input type="text" id="email" name="obj.email" value="${obj.email}"  class="required" email="true" title="<font color='red'>&nbsp;请输入供应商电子邮件，格式为email@test.com！</font>"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>联系地址：</td><td><input type="text" id="address" name="obj.address" value="${obj.address}" class="required" minlength="3" title="<font color='red'>&nbsp;请输入供应商地址，至少三个字符！</font>"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>传真：</td><td><input type="text" id="fax" name="obj.fax" class="required"  value="${obj.fax}" minlength="3" title="<font color='red'>&nbsp;请输入传真号码，格式为：0531-12345678！</font>"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>邮政编码：</td><td><input type="text" id="postcode" name="obj.postcode"  value="${obj.postcode}" class="required" minlength="6"  maxlength="6" title="<font color='red'>&nbsp;请输入邮政编码，6位数字！</font>"></td></tr>
	<tr><td class="align_r">备注：</td><td><textarea id="note" name="obj.note" >${obj.note}</textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#listSupplier").click(function(){
		loadPage("${base}/supplier/list.jhtm");
	});
	$('#addSupplier').click(function(){
		loadPage('${base}/supplier/add.jhtm');
	});
	$("#supplierForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit(function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("供应商保存成功！");
            		} else{
            			alert("品牌保存失败！");
            		}
        		});
			}
	});
</script>