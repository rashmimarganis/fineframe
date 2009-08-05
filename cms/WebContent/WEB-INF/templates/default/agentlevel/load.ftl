<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>代理商等级管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addAgentLevel">添加代理商等级</a>&nbsp;|&nbsp;<a href="#" id="listAgentLevel">代理商等级列表</a></td>
  </tr>
</table>
<form id="agentLevelForm" method="post"  action="${base}/agentlevel/save.jhtm">
<input type="hidden" name="obj.agentLevelId" value="${obj.agentLevelId}" >
<table class="table_form">
	<caption>代理商等级信息</caption>
	<tr><td width="150" class="align_r"><font color="red">*</font>代理商等级名称：</td><td><input type="text" id="agentLevelName" name="obj.agentLevelName" value="${obj.agentLevelName}" class="required" minlength="2" title="请输入代理等级名称，至少两个字符！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>可享受折扣率：</td><td><input type="text" id="discount" name="obj.discount" number="true" value="${obj.discount}" class="required" minlength="3" title="请输入小于10的代理折扣！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>可创建会员等级数量：</td><td><input type="text" id="maxMemeberNumber" name="obj.maxMemeberNumber" value="${obj.maxMemeberNumber}" class="required"  number="true" minlength="1"  title="请输入数字！"></td></tr>
	<tr><td class="align_r"><font color="red">*</font>可创建价格模板数量：</td><td><input type="text"id="priceTemplateNumber" name="obj.priceTemplateNumber" value="${obj.priceTemplateNumber}" number="true" minlength="1"  class="required" minlength="3" title="请输入数字！"></td></tr>
	<tr><td class="align_r">描述：</td><td><textarea id="description" name="obj.description" >${obj.description}</textarea></td></tr>
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#listAgentLevel").click(function(){
		loadPage("${base}/agentlevel/list.jhtm");
	});
	$('#addAgentLevel').click(function(){
		loadPage('${base}/agentlevel/add.jhtm');
	});
	$("#agentLevelForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit(function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("代理等级保存成功！");
            		} else{
            			alert("代理等级保存失败！");
            		}
        		});
			}
	});
</script>