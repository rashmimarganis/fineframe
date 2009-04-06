<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>系统日志查询</caption>
  <tr>
    <td class="align_r">登录组织：</td><td><input type="text" name="obj.org.id"></td><td  class="align_r">登录用户：</td><td><input type="text" name="obj.org.id">
  	 </td><td  class="align_r">开始时间：</td><td><input type="text" id="startTime" name="startTime"></td><td  class="align_r">结束时间：</td><td><input type="text" id="endTime" name="endTime"></td>
 	<td><input type="submit"  class="button_style" value="查询"></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>系统访问日志</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="5%">ID</th>
	<th>操作名称</th>
	<th width="10%">操作用户</th>
	<th width="25%">操作时间</th>
	<th width="100">IP地址</th>
	<th width="100">访问组织</th>
</tr>
<#list objs as log>
	<tr>
		<td class="align_c"><input type="checkbox" class="log" name="logId" value="${log.id}"></td>
		<td class="align_c">${log.id}</td>
		<td>${log.operation}</td>
		<td class="align_c">${log.user.username}</td>
		<td class="align_c">${log.time}</td>
		<td class="align_c">${log.ip!""}</td>
		<td>${(log.org.title)!""}</td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteLog">&nbsp;&nbsp;
</div>

<div id="pages"><SPAN>总数：<b>${(page.totalCount)!}</b></SPAN>
<SPAN ><a href="javascript:firstPage();">首页</a></SPAN><SPAN ><a href="javascript:prevPage();">上一页</a
></SPAN><SPAN ><a href="javascript:nextPage();">下一页</a></SPAN><SPAN class="disable">
<a href="javascript:lastPage();">尾页</a></SPAN><SPAN>页次：<b><font color="red">${(page.currentPage)!}</font>/${(page.totalPage)!}</b></SPAN>
</div>
<script language="javascript">
	var pageCount='${page.totalPage}';
	var limit=${page.limit};
	var cp='${page.currentPage}';
	var url='${base}/log/page.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('log');
		}else{
			cancelSel('log');
		}
	});
	$('#selectAll').click(function(){
		selectAll('log');
	});
	$('#cancelSel').click(function(){
		cancelSel('log');
	});
	$('#deleteLog').click(function(){
		deleteObj();
	});
	$('#startTime').datepicker({
			changeMonth: true,
			changeYear: true
		});
	$('#endTime').datepicker({
			changeMonth: true,
			changeYear: true
		});
	function getIds(idName){
		var ids='';
		$.each( $('.'+idName), function(i, n){
			if($(n).attr('checked')){
				if(ids==''){
					ids= $(n).val();
				}else{
					ids=ids+","+ $(n).val();
				}
		  	}
		}); 
		return ids;
	}
	function selectAll(idName){
		$.each( $('.'+idName), function(i, n){
			if($(n).attr('checked')==false){
				$(n).attr('checked',true);
		  	}
		}); 
	}
	
	function cancelSel(idName){
		$.each( $('.'+idName), function(i, n){
			if($(n).attr('checked')==true){
				$(n).attr('checked',false);
		  	}
		}); 
	}
	
	function firstPage(){
		if(cp==0){
			alert("已经是第一页。");
		}else{
			cp=0;
			loadPage(url+'p='+cp);
		}
	}
	
	function prevPage(){
		if(cp==0){
			alert("已经是第一页。");
		}else{
			cp=cp*1-1;
			loadPage(url+'p='+cp);
		}
	}
	
	function nextPage(){
		if(cp==pageCount){
			alert("已经是最后一页。");
		}else{
			cp=cp*1+1;
			loadPage(url+'p='+cp);
		}
	}
	
	function lastPage(){
		if(cp==pageCount){
			alert("已经是最后一页。");
		}else{
			cp=pageCount;
			loadPage(url+'&p='+cp);
		}
	}
	function refresh(){
		loadPage(url+'p='+cp);
	}
	function deleteObj(){
		if(!confirm("确定要删除日志吗？")){
			return;
		}
		$.post('${base}/log/delete.jhtm?ids='+getIds('log'),function(o){
			eval("var o1="+o);
			if(o1.success){
				alert("删除成功！");
				refresh();
			}else{
				alert("删除失败！");
			}
		})
	}
	
</script>
</#compress>