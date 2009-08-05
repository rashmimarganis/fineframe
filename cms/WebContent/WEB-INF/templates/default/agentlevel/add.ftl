<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>代理商等级管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listSupplier">代理商等级列表</a></td>
  </tr>
</table>
<form id="agentLevelForm" method="post"  action="${base}/agentlevel/save.jhtm">

<table class="table_form">
	<caption>代理商等级信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>代理商等级名称：</td><td><input type="text" id="agentLevelName" name="obj.agentLevelName" class="required" minlength="2" title="请输入代理等级名称，至少两个字符！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>可享受折扣率：</td><td><input type="text" id="discount" name="obj.discount" max="10.00" number="true" class="required"  title="请输入不大于10的代理折扣！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>可创建会员等级数量：</td><td><input type="text" id="maxMemeberNumber" name="obj.maxMemeberNumber" class="required"   min="1" number="true" minlength="1"  title="请输入正整数！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>可创建价格模板数量：</td><td><input type="text"id="priceTemplateNumber" name="obj.priceTemplateNumber" number="true" min="1" minlength="1"  class="required" minlength="3" title="请输入正整数！"></td></tr>
	<tr><td class="align_r">描述：</td><td><textarea id="description" name="obj.description" ></textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>

<script language="javascript">
	$("#agentLevelName").focus();
	$("#listSupplier").click(function(){
		loadPage("${base}/agentlevel/list.jhtm");
	});
	$("#agentLevelForm").validate({
		success: function(label) {
			label.html("").addClass("success");
		},
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("代理等级信息保存成功！");
            		} else{
            			alert("代理等级信息保存失败！");
            		}
        		}});
			}
	});
	
</script>