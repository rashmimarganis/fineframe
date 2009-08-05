<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>供应商管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addSupplier">添加供应商</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>供应商管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">供应商编号</th>
	<th width="10%">供应商名称</th>
	<th>联系人</th>
	<th>联系电话</th>
	<th>手机号码</th>
	<th>电子邮件</th>
	<th>地址</th>
	<th>邮编</th>
	<th width="100" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="supplier" name="supplierId" value="${obj.supplierId}"></td>
		<td class="align_c">${obj.supplierId}</td>
		<td>${obj.supplierName!""}</td>
		<td class="align_c">${(obj.linkMan)!""}</td>
		<td>${obj.telephone!""}</td>
		<td>${obj.mobilephone!""}</td>
		<td>${obj.email!""}</td>
		<td>${obj.address!""}</td>
		<td>${obj.postcode!""}</td>
		<td ><a href="#" class="supplierEdit" objId="${obj.supplierId}">编辑</a>&nbsp;&nbsp;<a href="#" class="supplierDelete" objId="${obj.supplierId}">删除</a></td>
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
	var url='${base}/supplier/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('supplier');
		}else{
			cancelSel('supplier');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('supplier');
	});
	$('#addSupplier').click(function(){
		loadPage('${base}/supplier/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('supplier');
	});
	
	$('.supplierEdit').click(function(){
		loadPage("${base}/supplier/load.jhtm?id="+$(this).attr("objId"));
	});
	$('.supplierDelete').click(function(){
		if(confirm("确定要删除选中的供应商分类吗？")){
			$.getJSON("${base}/supplier/delete.jhtm?id="+$(this).attr("objId"), function(o){
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
		if(!confirm("确定要删除供应商信息吗？")){
			return;
		}
		$.post('${base}/supplier/deletes.jhtm?'+getDelIds('supplier','ids'),function(o){
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