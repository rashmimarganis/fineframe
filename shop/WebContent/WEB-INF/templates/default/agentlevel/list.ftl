<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>代理商等级管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addSupplier">添加代理等级</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>代理等级管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">编号</th>
	<th width="10%">代理等级名称</th>
	<th>可享受折扣率</th>
	<th>可创建会员等级数量</th>
	<th>可创建价格模板数量</th>
	<th width="100" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="agentLevel" name="agentLevelId" value="${obj.agentLevelId}"></td>
		<td class="align_c">${obj.agentLevelId}</td>
		<td>${obj.agentLevelName!""}</td>
		<td class="align_c">${(obj.discount)!""}</td>
		<td>${obj.maxMemeberNumber!""}</td>
		<td>${obj.priceTemplateNumber!""}</td>
		<td ><a href="#" class="agentLevelEdit" objId="${obj.agentLevelId}">编辑</a>&nbsp;&nbsp;<a href="#" class="agentLevelDelete" objId="${obj.agentLevelId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteSupplier">&nbsp;&nbsp;
</div>

<div id="pages"><SPAN>总数：<b>${(page.totalCount)!}</b></SPAN>
<SPAN ><a href="javascript:firstPage();">首页</a></SPAN><SPAN ><a href="javascript:prevPage();">上一页</a
></SPAN><SPAN ><a href="javascript:nextPage();">下一页</a></SPAN><SPAN class="disable">
<a href="javascript:lastPage();">尾页</a></SPAN><SPAN>页次：<b><font color="red">${(page.currentPage)!}</font>/${(page.totalPage)!}</b></SPAN>
</div>
<script language="javascript">
	var pageCount='${(page.totalPage)!}';
	var limit=${(page.limit)!};
	var cp='${(page.currentPage)!}';
	var url='${base}/agentlevel/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('agentLevel');
		}else{
			cancelSel('agentLevel');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('agentLevel');
	});
	$('#addSupplier').click(function(){
		loadPage('${base}/agentlevel/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('agentLevel');
	});
	
	$('.agentLevelEdit').click(function(){
		loadPage("${base}/agentlevel/load.jhtm?id="+$(this).attr("objId"));
	});
	$('.agentLevelDelete').click(function(){
		if(confirm("确定要删除选中的代理等级吗？")){
			$.getJSON("${base}/agentlevel/delete.jhtm?id="+$(this).attr("objId"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteSupplier").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除代理等级信息吗？")){
			return;
		}
		$.post('${base}/agentlevel/deletes.jhtm?'+getDelIds('agentLevel','ids'),function(o){
			eval("var o1="+o);
			if(o1.success){
				alert("删除成功！");
				loadPage(url+'p='+cp);
			}else{
				alert("删除失败！");
			}
		})
	}
	
</script>
</#compress>