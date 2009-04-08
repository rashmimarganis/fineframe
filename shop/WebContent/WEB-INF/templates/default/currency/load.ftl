<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>货币管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addBrand">添加货币</a>&nbsp;|&nbsp;<a href="#" id="listBrand">货币列表</a></td>
  </tr>
</table>
<form id="currencyForm" method="post" enctype="multipart/form-data" action="${base}/currency/save.jhtm">
<input type="hidden" name="obj.currencyId" value="${obj.currencyId}" >
<table class="table_form">
	<caption>编辑货币</caption>
	<tr>
                <td class="align_r">
                  <font color="red">*</font>  货币类型：</td>
                <td class="align_l">
                    <select name="obj.currencyType" id="currencyType">
		<option value="CNY">人民币</option>
		<option value="USD">美元</option>

		<option value="EUR">欧元</option>
		<option value="GBP">英镑</option>
		<option value="CAD">加拿大元</option>
		<option value="AUD">澳大利亚元</option>
		<option value="RUR">卢布</option>
		<option value="HKD">港币</option>

		<option value="MOP">澳门元</option>
		<option value="TWD">新台币</option>
		<option value="KRW">韩元</option>
		<option value="SGD">新加坡元</option>
		<option value="NZD">新西兰元</option>
		<option value="JPY">日元</option>

	</select>
                </td>
            </tr>
            <tr>
                <td class="align_r">
                 <font color="red">*</font>货币名称：</td>
                <td class="align_l"><input name="obj.currencyName" type="text" value="人民币" id="currencyName" value="${obj.currencyName}"  class="required" title="请输入货币名称"></td>
            </tr>
            <tr>
                <td class="align_r">
                   <font color="red">*</font>货币符号：</td>
                <td class="align_l">
                    &nbsp;<input name="obj.currencySymbol" type="text" value="${obj.currencySymbol!""}" id="currencySymbol"  class="required"  title=" 请输入货币符号！"></td>
            </tr>

            <tr>
                <td class="align_r">
                    <font color="red">*</font>汇率：</td>
                <td class="align_l"><input name="obj.exchangeRate"  value="${obj.exchangeRate}" type="text" id="exchangeRate" class="required" number="true" min="0"  title=" 请输入数字！"/></td>
            </tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" class="required" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="button" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#currencyType").val("${obj.currencyType}");
	$("#listBrand").click(function(){
		loadPage("${base}/currency/list.jhtm");
	});
	$('#addBrand').click(function(){
		loadPage('${base}/currency/add.jhtm');
	});
	$("#currencyForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit(function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("货币保存成功！");
            		} else{
            			alert("货币保存失败！");
            		}
        		});
			}
	});
</script>