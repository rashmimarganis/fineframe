<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addProduct">添加商品</a>&nbsp;|&nbsp;<a href="#" id="onlineProduct">商品上架</a>&nbsp;|&nbsp;<a href="#" id="offlineProduct">商品下架</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>商品管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">商品编号</th>
	<th width="10%">商品名称</th>
	<th>分类</th>
	<th>销售价</th>
	<th>库存</th>
	<th>品牌</th>
	<th>重量</th>
	<th>排序</th>
	<th class="align_c">状态</th>
	<th width="200" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="product" name="productId" value="${obj.productId}"></td>
		<td>${obj.productNo}</td>
		<td>${obj.productName}</td>
		<td>${obj.category.categoryName}</td>
		<td>${obj.price}</td>
		<td>${obj.storeNumber}</td>
		<td>${obj.brand.brandName}</td>
		<td>${obj.weight}</td>
		<td>${obj.sequence}</td>
		<td class="align_c">${obj.online?string("上架","下架")}</td>
		<td class="align_c">&nbsp;<a href="#" class="productEdit" objId="${obj.productId}">编辑</a>&nbsp;|&nbsp;<a href="#" class="productDelete" objId="${obj.productId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteProduct">&nbsp;&nbsp;
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
	var url='${base}/product/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('product');
		}else{
			cancelSel('product');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('product');
	});
	$('#addProduct').click(function(){
		loadPage('${base}/product/add.jhtm');
	});
	$('#onlineProduct').click(function(){
		if(confirm("确定要将选中的商品上架吗？")){
			$.post('${base}/product/online.jhtm?'+getDelIds('product','ids'),function(o){
			eval("var o1="+o);
			if(o1.success){
				alert("上架成功！");
				loadPage(url+'p='+cp);
			}else{
				alert("上架失败！");
			}
		});
			
		}
	});
	$('#offlineProduct').click(function(){
		if(confirm("确定要将选中的商品下架吗？")){
			$.post('${base}/product/offline.jhtm?'+getDelIds('product','ids'),function(o){
			eval("var o1="+o);
			if(o1.success){
				alert("下架成功！");
				loadPage(url+'p='+cp);
			}else{
				alert("下架失败！");
			}
		});
			
		}
	});
	$('#cancelSel').click(function(){
		cancelSel('product');
	});
	
	$('.productChild').click(function(){
		loadPage("${base}/product/children.jhtm?id="+$(this).attr("objId"));
	});
	$('.productEdit').click(function(){
		loadPage("${base}/product/load.jhtm?id="+$(this).attr("objId"));
	});
	$('.productDelete').click(function(){
		if(confirm("确定要删除选中的商品吗？")){
			$.getJSON("${base}/product/delete.jhtm?id="+$(this).attr("objId"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteProduct").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除商品吗？")){
			return;
		}
		$.post('${base}/product/deletes.jhtm?'+getDelIds('product','ids'),function(o){
			eval("var o1="+o);
			if(o1.success){
				alert("删除成功！");
				loadPage(url+'p='+cp);
			}else{
				alert("删除失败！");
			}
		});
	}
	
</script>
</#compress>