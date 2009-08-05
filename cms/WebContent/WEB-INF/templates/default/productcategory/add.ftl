<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品分类管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listCategory">商品分类列表</a></td>
  </tr>
</table>
<form id="categoryForm" method="post" enctype="multipart/form-data" action="${base}/category/save.jhtm">
<table class="table_form">
	<caption>商品分类信息</caption>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>上级分类：
		</td>
		<td>
			<select id="parentId" name="obj.parent.categoryId" >
				<option value="0">无上级
				<#if (topCategories.size()>0)>
				<#list topCategories as c>
					<option value="${c.categoryId}" >${c.categoryName}
					<#if (c.children.size()>0)>
						<#list c.children as cc>
							<option value="${cc.categoryId}">&nbsp;&nbsp;<#if cc_has_next>├<#else>└</#if>${cc.categoryName}
							<#list cc.children as ccc>
								<option value="${ccc.categoryId}">&nbsp;&nbsp;<#if cc_has_next>│<#else>&nbsp;&nbsp;</#if><#if ccc_has_next>&nbsp;├<#else>&nbsp;└</#if>${ccc.categoryName}
							</#list>
						</#list>
					</#if>
				</#list>
				</#if>
			</select>
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>商品分类名称：
		</td>
		<td>
			<input type="text" id="categoryName" name="obj.categoryName" class="required" title="请输入商品分类名称！">
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>分类英文名称：
		</td>
		<td>
			<input type="text" id="categoryEnName" name="obj.categoryEnName" class="required"  title="请输入商品分类英文名称！">
		</td>
	</tr>
	<tr>
		<td class="align_r">商品分类图片：</td>
		<td><input type="file" id="image" name="image" class="file_style"  class="required" ></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>显示顺序：</td>
		<td><input type="text" id="sequence" class="required" name="obj.sequence" number="true" value="0" title="请输入数字！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>搜索关键字：</td>
		<td><input type="text" id="pageKeyWords" name="obj.pageKeyWords" value=""></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>搜索描述：</td>
		<td><input type="text" id="pageDescription"  name="obj.pageDescription" value=""></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>商品分类介绍：</td>
		<td><textarea id="description" name="obj.description" class="required"></textarea>
			<script language="javascript">
				var oFCKeditor = new FCKeditor( 'description' ) ;
				oFCKeditor.BasePath	= "${base}/fckeditor/" ;
				oFCKeditor.ReplaceTextarea() ;
			</script>
		</td>
	</tr>
	
	<tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="reset" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#categoryName").focus();
	$("#listCategory").click(function(){
		loadPage("${base}/category/list.jhtm");
	});
	$("#categoryForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("商品分类保存成功！");
            		} else{
            			alert("商品分类保存失败！");
            		}
        		}});
			}
	});
	
</script>