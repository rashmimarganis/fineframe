<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>货币管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addCurrency">添加货币</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>货币管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">货币编号</th>
	<th width="10%">货币名称</th>
	<th>货币代码</th>
	<th>货币符号</th>
	<th>汇率</th>
	<th width="100" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="currency" name="currencyId" value="${obj.currencyId}"></td>
		<td class="align_c">${obj.currencyId}</td>
		<td>${obj.currencyName}</td>
		<td class="align_c">${obj.currencyType}</td>
		<td class="align_c">${obj.currencySymbol!""}</td>
		<td class="align_r">${obj.exchangeRate}</td>
		<td ><a href="#" class="currencyEdit" id="${obj.currencyId}">编辑</a>&nbsp;&nbsp;<a href="#" class="currencyDelete" id="${obj.currencyId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll" >全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteCurrency">&nbsp;&nbsp;
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
	var url='${base}/currency/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('currency');
		}else{
			cancelSel('currency');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('currency');
	});
	$('#addCurrency').click(function(){
		loadPage('${base}/currency/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('currency');
	});
	
	$('.currencyEdit').click(function(){
		loadPage("${base}/currency/load.jhtm?id="+$(this).attr("id"));
	});
	$('.currencyDelete').click(function(){
		if(confirm("确定要删除选中的货币吗？")){
			$.getJSON("${base}/currency/delete.jhtm?id="+$(this).attr("id"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteCurrency").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除货币吗？")){
			return;
		}
		$.post('${base}/currency/deletes.jhtm?'+getDelIds('currency','ids'),function(o){
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