<#compress>
<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>系统缓存管理</caption>
  <tr>
    <td><a href='javascript:loadPage("${base}/cache/clearAll.jhtm");' >清空缓存</a> 
  </tr>
</table>
<table cellpadding="0" cellspacing="1" class="table_list" >
<caption>系统缓存列表</caption>
<tr align="center">
	<th width="80">缓存名称</th>
	<th width="90">缓存命中数</th>
	<th width="90">硬盘中缓存数</th>
	<th width="90">内存缓存数</th>
	<th width="90">缓存未中数</th>
	<th width="130">平均获取时间数</th>
	<th width="80">精确性</th>
	<th width="80">精确性描述</th>
</tr>
<#list objs as obj>
	<tr width="100%">
		<td>${obj.name}</td>
		<td class="align_c">${obj.cacheHints}</td>
		<td class="align_c">${obj.diskStoreSize}</td>
		<td class="align_c">${obj.memoryStoreSize!""}</td>
		<td class="align_c">${obj.cacheMisses!""}</td>
		<td class="align_c">${obj.averageGetTime!""}</td>
		<td class="align_c">${obj.accuracy!""}</td>
		<td>${(obj.accuracyDescription)!""}</td>
	</tr>
</#list>
</table>

<script language="javascript">
	
	
</script>
</#compress>