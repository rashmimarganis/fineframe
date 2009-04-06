<form id="roleForm" method="post">
	<table>
	<input type="hidden" name="obj.id" value="${obj.id}">
	
	<tr>
	<td>名称：</td>
	<td>
	<input type="text" name="obj.name" value="${obj.name}">
	</td>
	
	</tr>
	<tr>
	<td>标题：</td>
	<td>
	<input type="text" name="obj.title" value="${obj.title}">
	</td>
	
	</tr>
	<tr>
	<td></td>
	<td>
	<input type="submit" value="保存">
	</td>
	
	</tr>
</form>