<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品配置方案管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addProductConfig">添加商品配置方案</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>商品配置方案管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">方案编号</th>
	<th width="10%">方案名称</th>
	<th width="10%">缩略图宽度</th>
	<th width="10%">缩略图高度</th>
	<th>清晰图片宽度</th>
	<th>清晰图片高度</th>
	<th>元/积分</th>
	<th>是否默认</th>
	<th width="200" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="productConfig" name="productConfigId" value="${obj.productConfigId}"></td>
		<td class="align_c">${obj.productConfigId}</td>
		<td class="align_c">${obj.productConfigName}</td>
		<td>${obj.thumbWidth}</td>
		<td>${obj.thumbHeight}</td>
		<td>${obj.imageWidth}</td>
		<td>${obj.imageHeight}</td>
		<td>${obj.moneyPerScore}</td>
		<td><#if obj.defaultConfig==true>是<#else>否</#if></td>
		<td  class="align_c"><a href="#" class="productConfigDefault" id="${obj.productConfigId}">设置默认</a>&nbsp;|&nbsp;<a href="#" class="productConfigEdit" id="${obj.productConfigId}">编辑</a>&nbsp;|&nbsp;<a href="#" class="productConfigDelete" id="${obj.productConfigId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteProductConfig">&nbsp;&nbsp;
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
	var url='${base}/productconfig/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('productConfig');
		}else{
			cancelSel('productConfig');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('productConfig');
	});
	$('#addProductConfig').click(function(){
		loadPage('${base}/productconfig/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('productConfig');
	});
	
	$('.productConfigEdit').click(function(){
		loadPage("${base}/productconfig/load.jhtm?id="+$(this).attr("id"));
	});
	$('.productConfigDelete').click(function(){
		if(confirm("确定要删除选中的商品配置吗？")){
			$.getJSON("${base}/productconfig/delete.jhtm?id="+$(this).attr("id"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	$('.productConfigDefault').click(function(){
		if(confirm("确定要默认商品配置方案吗？")){
			$.getJSON("${base}/productconfig/default.jhtm?id="+$(this).attr("id"), function(o){
			  	if(o.success){
			  		alert("设置成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("设置失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteProductConfig").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除商品配置吗？")){
			return;
		}
		$.post('${base}/productconfig/deletes.jhtm?'+getDelIds('productConfig','ids'),function(o){
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