<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品类型管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="addProductType">添加商品类型</a>&nbsp;|&nbsp;<a href="#" id="listProductType">商品类型列表</a></td>
  </tr>
</table>
<form id="productTypeForm" method="post" enctype="multipart/form-data" action="${base}/producttype/save.jhtm">
<input type="hidden" name="obj.productTypeId" value="${obj.productTypeId}" >
<table class="table_form">
	<caption>修改商品类型信息</caption>
	
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>商品类型名称：
		</td>
		<td>
			<input type="text" id="productTypeName" name="obj.productTypeName" value="${obj.productTypeName}"  class="required" title="请输入商品类型名称！">
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>类型英文名称：
		</td>
		<td>
			<input type="text" id="productTypeEnName" name="obj.productTypeEnName" value="${obj.productTypeEnName}"  class="required"  title="请输入商品类型英文名称！">
		</td>
	</tr>
	<tr>
		<td class="align_r">商品类型图片：</td>
		<td><img src="${base+(obj.imagePath)!""}" width="80" height="80"><input type="hidden" name="obj.imagePath" value="${obj.imagePath!""}"><input type="file"  class="file_style" id="image" name="image" required="true"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>显示顺序：</td>
		<td><input type="text" id="sequence" class="required" name="obj.sequence" value="${obj.sequence}"  number="true" value="0" title="请输入数字！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>搜索关键字：</td>
		<td><input type="text" id="pageKeyWords" name="obj.pageKeyWords" value="${obj.pageKeyWords}" ></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>搜索描述：</td>
		<td><input type="text" id="pageDescription"  name="obj.pageDescription" value="${obj.pageDescription}" ></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>商品类型介绍：</td>
		<td><textarea id="description" name="obj.description" class="required">${obj.description}</textarea>
			<script language="javascript">
				var oFCKeditor = new FCKeditor( 'description' ) ;
				oFCKeditor.BasePath	= "${base}/fckeditor/" ;
				oFCKeditor.ReplaceTextarea() ;
			</script>
		</td>
	</tr><tr><td colspan="2" class="align_c"><input type="submit" id="saveBtn" class="button_style" value="&nbsp;保存&nbsp;">&nbsp;&nbsp;<input type="button" class="button_style" value="&nbsp;重置&nbsp;"></td></tr>
</table>
</form>
<script language="javascript">
	$("#listProductType").click(function(){
		loadPage("${base}/producttype/list.jhtm");
	});
	$('#addProductType').click(function(){
		loadPage('${base}/producttype/add.jhtm');
	});
	$("#productTypeForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit(function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("商品类型保存成功！");
            			loadPage('${base}/producttype/add.jhtm');
            		} else{
            			alert("商品类型保存失败！");
            		}
        		});
			}
	});
</script>