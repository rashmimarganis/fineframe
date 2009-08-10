<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>店铺管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addShop">添加店铺</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>店铺管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">店铺编号</th>
	<th width="10%">店铺名称</th>
	<th>英文名称</th>
	<th>排序</th>
	<th width="200" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="shop" name="shopId" value="${obj.shopId}"></td>
		<td class="align_c">${obj.shopId}</td>
		<td>${obj.title}</td>
		<td>${obj.shopName!""}</td>
		<td>${obj.sort}</td>
		<td class="align_c"><a href="#" class="shopChild" objId="${obj.shopId}">下级店铺</a>&nbsp;|&nbsp;<a href="#" class="shopEdit" objId="${obj.shopId}">编辑</a>&nbsp;|&nbsp;<a href="#" class="shopDelete" objId="${obj.shopId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteShop">&nbsp;&nbsp;
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
	var url='${base}/shop/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('shop');
		}else{
			cancelSel('shop');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('shop');
	});
	$('#addShop').click(function(){
		loadPage('${base}/shop/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('shop');
	});
	
	$('.shopChild').click(function(){
		loadPage("${base}/shop/children.jhtm?id="+$(this).attr("objId"));
	});
	$('.shopEdit').click(function(){
		loadPage("${base}/shop/load.jhtm?id="+$(this).attr("objId"));
	});
	$('.shopDelete').click(function(){
		if(confirm("确定要删除选中的店铺吗？")){
			$.getJSON("${base}/shop/delete.jhtm?id="+$(this).attr("objId"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteShop").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除店铺吗？")){
			return;
		}
		$.post('${base}/shop/deletes.jhtm?'+getDelIds('shop','ids'),function(o){
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