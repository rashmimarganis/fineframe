<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>模板管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addTemplate">添加模板</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>模板管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">模板编号</th>
	<th width="10%">模板名称</th>
	<th>所属方案</th>
	<th width="100" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="template" name="templateId" value="${obj.templateId}"></td>
		<td class="align_c">${obj.templateId}</td>
		<td>${obj.templateName}</td>
		<td>${obj.suit.suitName}</td>
	
		<td ><a href="#" class="templateEdit" id="${obj.templateId}">编辑</a>&nbsp;&nbsp;<a href="#" class="templateDelete" id="${obj.templateId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteTemplate">&nbsp;&nbsp;
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
	var url='${base}/template/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('template');
		}else{
			cancelSel('template');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('template');
	});
	$('#addTemplate').click(function(){
		loadPage('${base}/template/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('template');
	});
	
	$('.templateEdit').click(function(){
		loadPage("${base}/template/load.jhtm?id="+$(this).attr("id"));
	});
	$('.templateDelete').click(function(){
		if(confirm("确定要删除选中的品牌分类吗？")){
			$.getJSON("${base}/template/delete.jhtm?id="+$(this).attr("id"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteTemplate").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除模板吗？")){
			return;
		}
		$.post('${base}/template/deletes.jhtm?'+getDelIds('template','ids'),function(o){
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