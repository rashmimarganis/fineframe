<html>
<head>
  <title>FineCode代码生成系统</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8>">
	<link rel="stylesheet" type="text/css" href="js/resources/css/ext-all.css" />
 	<script type="text/javascript" src="js/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
	<style type="text/css">
	html, body {
        font:normal 12px verdana;
        margin:0;
        padding:0;
        border:0 none;
        overflow:hidden;
        height:100%;
    }
	p {
	    margin:5px;
	}
    .settings {
        background-image:url(js/shared/icons/fam/folder_wrench.png);
    }
    .nav {
        background-image:url(js/shared/icons/fam/folder_go.png);
    }
    </style>
	<script type="text/javascript">
       

		
    Ext.onReady(function(){
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        var comboxWithPanel = new Ext.form.ComboBox({
    		store:new Ext.data.SimpleStore({fields:[],data:[[]]}),
    		editable:false,
    		mode: 'local',
    		triggerAction:'all',
    		maxHeight: 200,
    		tpl: '<div style="height:200px"><div id="panel"></div></div>',
    		selectedClass:'',
    		onSelect:Ext.emptyFn
    	});
    	comboxWithPanel.render('comboxWithPanel');
    	var root = new Ext.tree.AsyncTreeNode({
            id:'m11',
            text:'TTTTTT'
        });
    	 var tree2 = new Ext.tree.TreePanel({
             loader:new Ext.tree.TreeLoader({dataUrl:'leftMenu.jhtm?pid=1'}),
             root:root,
             border:false,
             margins:'3 3 3 3',
             rootVisible:false
         });
    	var border = new Ext.Panel({
    		title:'面板title',
    		layout:'fit',
    		border:false,
    		height :200,
    	    tbar:[{text:'确定一'},'-',new Ext.form.TextField({id: 'paramCnName',width:60}),{text:'查找一'}],
    		bbar:[{text:'确定二'},'-',new Ext.form.TextField({id: 'aa',width:60}),{text:'查找二'}],
    		items: tree2
      	});
    	comboxWithPanel.on('expand',function(){
    		border.render('panel');
      	});
      
       viewport.doLayout();
    });
	</script>
</head>
<body>
	<div id="comboxWithPanel"></div>
	
  <div id="panel">
  </div>
  

 </body>
</html>
