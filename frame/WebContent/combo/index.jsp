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
			       	 	 root : new Ext.tree.AsyncTreeNode({id:'0',text:'¸ù½áµã'})
			    	},
			    	
					//all:ËùÓÐ½áµã¶¼¿ÉÑ¡ÖÐ
					//exceptRoot£º³ý¸ù½áµã£¬ÆäËü½áµã¶¼¿ÉÑ¡(Ä¬ÈÏ)
					//folder:Ö»ÓÐÄ¿Â¼£¨·ÇÒ¶×ÓºÍ·Ç¸ù½áµã£©¿ÉÑ¡
					//leaf£ºÖ»ÓÐÒ¶×Ó½áµã¿ÉÑ¡
					selectNodeModel:'leaf'
				});
			});
			function showValue(){
				alert("ÏÔÊ¾Öµ="+comboBoxTree.getRawValue()+"  ÕæÊµÖµ="+comboBoxTree.getValue());
			}
			function search(){
				var searchName = Ext.getDom('searchName').value;
				alert("²éÑ¯×Ö·û´®£º"+searchName);
			}
		</script>
	</head>
	<body>
		<table>
			<tr>
				<td>&nbsp;</td>
				<td> <div id="comboBoxTree"></div> </td>
				<td> <input type='button' value='Öµ' onclick='showValue()'> </td>
			</tr>
		</table>
	</body>
</html>