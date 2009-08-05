<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品分类管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addProductType">添加分类</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>商品分类管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">商品分类编号</th>
	<th width="10%">商品分类名称</th>
	<th>英文名称</th>
	<th>排序</th>
	<th width="200" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="productType" name="productTypeId" value="${obj.productTypeId}"></td>
		<td class="align_c">${obj.productTypeId}</td>
		<td>${obj.productTypeName}</td>
		<td>${obj.productTypeEnName}</td>
		<td>${obj.sequence}</td>
		<td class="align_c">&nbsp;<a href="#" class="productTypeEdit" objId="${obj.productTypeId}">编辑</a>&nbsp;|&nbsp;<a href="#" class="productTypeDelete" objId="${obj.productTypeId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteProductType">&nbsp;&nbsp;
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
	var url='${base}/producttype/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('productType');
		}else{
			cancelSel('productType');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('productType');
	});
	$('#addProductType').click(function(){
		loadPage('${base}/producttype/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('productType');
	});
	
	$('.productTypeEdit').click(function(){
		loadPage("${base}/producttype/load.jhtm?id="+$(this).attr("objId"));
	});
	$('.productTypeDelete').click(function(){
		if(confirm("确定要删除选中的商品分类分类吗？")){
			$.getJSON("${base}/producttype/delete.jhtm?id="+$(this).attr("objId"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteProductType").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除商品分类吗？")){
			return;
		}
		$.post('${base}/productType/deletes.jhtm?'+getDelIds('productType','ids'),function(o){
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