<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品类型管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listProductType">商品类型列表</a></td>
  </tr>
</table>
<form id="productTypeForm" method="post" enctype="multipart/form-data" action="${base}/producttype/save.jhtm">
<table class="table_form">
	<caption>商品类型信息</caption>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>商品类型名称：
		</td>
		<td>
			<input type="text" id="productTypeName" name="obj.productTypeName" class="required" title="请输入商品类型名称！">
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>分类英文名称：
		</td>
		<td>
			<input type="text" id="productTypeEnName" name="obj.productTypeEnName" class="required"  title="请输入商品类型英文名称！">
		</td>
	</tr>
	<tr>
		<td class="align_r">商品类型图片：</td>
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
		<td class="align_r"><font color="red">*</font>商品类型介绍：</td>
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
	$("#productTypeName").focus();
	$("#listProductType").click(function(){
		loadPage("${base}/producttype/list.jhtm");
	});
	$("#productTypeForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("商品类型保存成功！");
            		} else{
            			alert("商品类型保存失败！");
            		}
        		}});
			}
	});
	
</script>