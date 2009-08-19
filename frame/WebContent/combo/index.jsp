<%@ page language="java" contentType="text/html; charset=GB2312"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>Customizing ComboBoxTree</title>
		<link rel="stylesheet" type="text/css" href="../js/resources/css/ext-all.css" />
	 	<script src="../js/adapter/ext/ext-base.js"></script>
	 	<script src="../js/ext-all.js"></script>
	    <script src="ComboBoxTree.js"></script>
		
		<script type="text/javascript">
			var comboBoxTree;
			Ext.BLANK_IMAGE_URL = '../js/resources/images/default/s.gif';
			Ext.onReady(function(){
				comboBoxTree = new Ext.ux.ComboBoxTree({
					renderTo : 'comboBoxTree',
					width : 250,
					tree : {
						xtype:'treepanel',
						loader: new Ext.tree.TreeLoader({dataUrl:'getNodes.jsp'}),
			       	 	 root : new Ext.tree.AsyncTreeNode({id:'0',text:'根结点'})
			    	},
			    	
					//all:所有结点都可选中
					//exceptRoot：除根结点，其它结点都可选(默认)
					//folder:只有目录（非叶子和非根结点）可选
					//leaf：只有叶子结点可选
					selectNodeModel:'leaf'
				});
			});
			function showValue(){
				alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
			}
			function search(){
				var searchName = Ext.getDom('searchName').value;
				alert("查询字符串："+searchName);
			}
		</script>
	</head>
	<body>
		<table>
			<tr>
				<td>&nbsp;</td>
				<td> <div id="comboBoxTree"></div> </td>
				<td> <input type='button' value='值' onclick='showValue()'> </td>
			</tr>
		</table>
	</body>
</html>