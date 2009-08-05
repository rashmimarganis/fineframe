<table cellpadding="0" cellspacing="1" class="table_form">
  <caption>商品管理</caption>
  <tr>
    <td class="align_l"><a href="#" id="listProduct">商品列表</a></td>
  </tr>
</table>
<form id="productForm" method="post" enctype="multipart/form-data" action="${base}/product/save.jhtm">
<table class="table_form">
	<caption>商品信息</caption>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>所属分类：
		</td>
		<td>
			<select id="categoryId" name="obj.category.categoryId"  class="required" title="请选择商品类型！">
			<#if (categories.size()>0)>
				<#list categories as c>
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
			<font color="red">*</font>所属类型：
		</td>
		<td>
			<select id="productType" name="obj.type.productTypeId" class="required" title="请选择商品类型！">
				<#list types as type>
					<option value='${type.productTypeId}'>${type.productTypeName}
				
				</#list>
			</select>
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>商品名称：
		</td>
		<td>
			<input type="text" id="productName" name="obj.productName" class="required" title="请输入商品名称！">
		</td>
	</tr>
	<tr>
		<td width="150" class="align_r">
			<font color="red">*</font>商品编号：
		</td>
		<td>
			<input type="text" id="productNo" name="obj.productNo" class="required" title="请输入商品编号！">
		</td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>所属品牌：</td>
		<td>
			<select id="brandId" class="required" name="obj.brand.brandId" number="true" value="0" title="请输入数字！">
				<#list brands as brand>
					<option value="${brand.brandId}">${brand.brandName}
				</#list>
			</select>
		</td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>计量单位：</td>
		<td><input type="text" id="unit" name="obj.unit"  class="required"  title="请计量单位！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>商品重量：</td>
		<td><input type="text" id="weight" name="obj.weight"  class="required"  title="请输入商品重量！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>库存数量：</td>
		<td><input type="text" id="scoreNumber" name="obj.scoreNumber"  class="required"  title="请库存数量！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>市场价格：</td>
		<td><input type="text" id="markerPrice" name="obj.markerPrice"  class="required"  title="请市场价格！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>价格：</td>
		<td><input type="text" id="price" name="obj.price"  class="required"  title="请价格！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>成本价格：</td>
		<td><input type="text" id="costPrice" name="obj.costPrice"  class="required"  title="请成本价格！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>商品图片：</td>
		<td><input type="file" id="image" name="image" class="file_style"  class="required" ></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>显示顺序：</td>
		<td><input type="text" id="sequence" class="required" name="obj.sequence" number="true" value="0" title="请输入数字！"></td>
	</tr>
	<tr>
		<td class="align_r"><font color="red">*</font>上架：</td>
		
		<td><input type="hidden" id="online1" name="obj.online" value="false"><input type="checkbox" id="online" ></td>
	</tr>
	
	<tr>
		<td class="align_r"><font color="red">*</font>商品介绍：</td>
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
	$("#productName").focus();
	$("#listProduct").click(function(){
		loadPage("${base}/product/list.jhtm");
	});
	$("#online").click(function(){
		if($(this).attr("checked")==true){
			$("#online1").val("true");
		}else{
			$("#online1").val("false");
		}
	});
	$("#productForm").validate({
		submitHandler: function(form) {
				$(form).ajaxSubmit({
				clearForm: true,
				success:function(o) { 
            		eval("var r="+o);
            		if(r.success){
            			alert("商品保存成功！");
            		} else{
            			alert("商品保存失败！");
            		}
        		}});
			}
	});
	
</script>