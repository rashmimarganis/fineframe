<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>会员等级管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addMemberLevel">添加会员等级</a></td>
  </tr>
</table>

<table cellpadding="0" cellspacing="1" class="table_list">
<caption>会员等级管理</caption>
<tr align="center">
	<th width="35" class="align_c"><input type="checkbox" id="checkAll"></th>
	<th width="10%">编号</th>
	<th width="10%">会员等级名称</th>
	<th >积分满足点</th>
	<th>交易量满足点</th>
	<th>交易额满足点</th>
	<th>可销售折扣率</th>
	<th>默认</th>
	<th width="100" class="align_c">操作</th>
</tr>
<#list objs as obj>
	<tr>
		<td class="align_c"><input type="checkbox" class="memberLevel" name="memberLevelId" value="${obj.memberLevelId}"></td>
		<td class="align_c">${obj.memberLevelId}</td>
		<td>${obj.memberLevelName!""}</td>
		<td class="align_c">${(obj.minBuyScore)!""}</td>
		<td>${obj.minBusinessScrore!""}</td>
		<td>${obj.minMoney!""}</td>
		<td>${obj.discount!""}</td>
		<td class="align_c"><#if obj.defaultLevel>是<#else>否</#if></td>
		<td ><a href="#" class="memberLevelEdit" objId="${obj.memberLevelId}">编辑</a>&nbsp;&nbsp;<a href="#" class="memberLevelDelete" objId="${obj.memberLevelId}">删除</a></td>
	</tr>
</#list>
</table>
<div class="button_box">
		<a href="#" id="selectAll"onClick="javascript:selectAll('log');">全选</a>/<a href="#" id="cancelSel">取消</a>
        
		<input type="submit" class="button_style" value="删除" id="deleteMemberLevel">&nbsp;&nbsp;
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
	var url='${base}/memberlevel/list.jhtm?';
	$('#checkAll').click(function(){
		if($(this).attr('checked')){
			selectAll('log');
		}else{
			cancelSel('log');
		}
	});
	
	$('#selectAll').click(function(){
		selectAll('memberLevel');
	});
	$('#addMemberLevel').click(function(){
		loadPage('${base}/memberlevel/add.jhtm');
	});
	$('#cancelSel').click(function(){
		cancelSel('memberLevel');
	});
	
	$('.memberLevelEdit').click(function(){
		loadPage("${base}/memberlevel/load.jhtm?id="+$(this).attr("objId"));
	});
	$('.memberLevelDelete').click(function(){
		if(confirm("确定要删除选中的会员等级吗？")){
			$.getJSON("${base}/memberlevel/delete.jhtm?id="+$(this).attr("objId"), function(o){
			  	if(o.success){
			  		alert("删除成功！");
			  		loadPage(url+'p='+cp);
			  	}else{
			  		alert("删除失败！");
			  	}
			}); 
			
		}
	});
	
	$("#deleteMemberLevel").click(function(){
		deleteObj();
	});
	function deleteObj(){
		if(!confirm("确定要删除会员等级信息吗？")){
			return;
		}
		$.post('${base}/memberlevel/deletes.jhtm?'+getDelIds('memberLevel','ids'),function(o){
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