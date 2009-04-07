<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>品牌类别管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addBrand">添加品牌</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>品牌类别管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">品牌编号</th>
	<th width="10%">品牌名称</th>
	<th>品牌Logo</th>
	<th>官方网站</th>
	<th>品牌介绍</th>
	<th width="100" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="brand" name="brandId" value="${obj.brandId}"></td>
		<td class="align_c">${obj.brandId}</td>
		<td>${obj.brandName}</td>
		<td class="align_c"><img src='${base+(obj.brandLogo)!""}' width="80" height="80"></td>
		<td>${obj.brandUrl}</td>
		<td>${obj.description}</td>
		<td ><a href="#" class="brandEdit" id="${obj.brandId}">编辑</a>&nbsp;&nbsp;<a href="#" class="brandDelete" id="${obj.brandId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteBrand">&nbsp;&nbsp;
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
	var url='${base}/brand/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('log');
		}else{
			cancelSel('log');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('brand');
	});
	$('#addBrand').click(function(){
		loadPage('${base}/brand/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('brand');
	});
	
	$('.brandEdit').click(function(){
		loadPage("${base}/brand/load.jhtm?id="+$(this).attr("id"));
	});
	$('.brandDelete').click(function(){
		if(confirm("确定要删除选中的品牌分类吗？")){
			$.getJSON("${base}/brand/delete.jhtm?id="+$(this).attr("id"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteBrand").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除品牌类别吗？")){
			return;
		}
		$.post('${base}/brand/deletes.jhtm?'+getDelIds('brand','ids'),function(o){
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