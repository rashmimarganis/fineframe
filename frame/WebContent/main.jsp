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
	var tree2 = new Ext.tree.TreePanel({
		loader: new Ext.tree.DWRTreeLoader({dwrCall:Tmplt.getTmpltTree}),
		border:false,
		autoScroll:true,
		root:new Ext.tree.AsyncTreeNode({text: '模板根目录',id:'0'})
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
		
    Ext.onReady(function(){
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		var menu = new Ext.Panel({
                id:"project",
                title:"项目管理",
                iconCls:'nav',
				border:'0px'
        });

       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                new Ext.BoxComponent({ // raw
                    region:'north',
                    el: 'north',
                    height:32
                }),new Ext.BoxComponent({
                    region:'south',
                    el: 'south',
                    height: 30
                }),{
                    region:'west',
                    id:'west-panel',
                    title:'导航菜单',
                    split:true,
                    width: 200,
                    minSize: 175,
                    maxSize: 400,
                    collapsible: true,
                    margins:'0 0 0 5',
                    layout:'accordion',
                    collapseMode:'mini',
                    
                    layoutConfig:{
						activeOnTop: false,
                        animate:true
                    },
                    items: [menu,{
                        title:'系统管理',
                        html:'<p></p>',
                        border:false,
                        iconCls:'settings'
                    }]
                },
                new Ext.TabPanel({
                    region:'center',
                    deferredRender:false,
                    activeTab:0,
                    items:[{
                        contentEl:'center1',
                        title: 'Close Me',
                        closable:true,
                        autoScroll:true
                    },{
                        contentEl:'center2',
                        title: 'Center Panel',
                        autoScroll:true
                    }]
                })
             ]
        });
      
       viewport.doLayout();
    });
	</script>
</head>
<body>

  <div id="west">
    <p></p>
  </div>
  <div id="north">
    <p></p>
  </div>
  <div id="center2">
        
  </div>
  <div id="center1">
        
  </div>
  
  <div id="south">
    <p></p>
  </div>

 </body>
</html>
