<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>会员等级管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listSupplier">会员等级列表</a></td>
  </tr>
</table>
<form id="memberLevelForm" method="post"  action="${base}/memberlevel/save.jhtm">

<table class="table_form">
	<caption>会员等级信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>会员等级名称：</td><td><input type="text" id="memberLevelName" name="obj.memberLevelName" class="required" minlength="2" title="请输入会员等级名称，至少两个字符！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>积分满足点：</td><td><input type="text" id="minBuyScore" name="obj.minBuyScore" min="0" number="true" class="required" title="请输入不小于0的积分数！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>交易量满足点：</td><td><input type="text" id="minBusinessScrore" name="obj.minBusinessScrore" class="required"  number="true" minlength="1"  title="请输入交易量满足点！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>交易额满足点：</td><td><input type="text" id="minMoney" name="obj.minMoney" class="required"  number="true" minlength="1"  title="请输入交易额满足点！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>可享受折扣率：</td><td><input type="text"id="discount" name="obj.discount" number="true" minlength="1" max="10.00"   class="required" minlength="3" title="请输入会员折扣率（不大于10）！"></td></tr>
	<input type="hidden" name="obj.defaultLevel" value="false">
	<tr><td class="align_r">描述：</td><td><textarea id="description" name="obj.description" ></textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>

<script language="javascript">
	$("#memberLevelName").focus();
	$("#listSupplier").click(function(){
		loadPage("${base}/memberlevel/list.jhtm");
	});
	$("#memberLevelForm").validate({
		success: function(label) {
			label.html("").addClass("success");
		},
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("会员等级信息保存成功！");
            		} else{
            			alert("会员等级信息保存失败！");
            		}
        		}});
			}
	});
	
</script>