<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>地区管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addArea">添加地区</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>地区管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="70">地区编号</th>
	<th >地区名称</th>
	<th >排序</th>
	<th width="100" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="area" name="areaId" value="${obj.areaId}"></td>
		<td class="align_c">${obj.areaId}</td>
		<td class="align_c">${obj.areaName}</td>
		<td>${obj.sequence}</td>
		<td ><a href="#" class="areaEdit" id="${obj.areaId}">编辑</a>&nbsp;&nbsp;<a href="#" class="areaDelete" id="${obj.areaId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteArea">&nbsp;&nbsp;
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
	var url='${base}/area/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('log');
		}else{
			cancelSel('log');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('area');
	});
	$('#addArea').click(function(){
		loadPage('${base}/area/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('area');
	});
	
	$('.areaEdit').click(function(){
		loadPage("${base}/area/load.jhtm?id="+$(this).attr("id"));
	});
	$('.areaDelete').click(function(){
		if(confirm("确定要删除选中的地区分类吗？")){
			$.getJSON("${base}/area/delete.jhtm?id="+$(this).attr("id"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteArea").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除地区吗？")){
			return;
		}
		$.post('${base}/area/deletes.jhtm?'+getDelIds('area','ids'),function(o){
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