var pk="modelId";
var listUrl='cms/model/list.jhtm';
var deleteUrl='cms/model/delete.jhtm';
var saveUrl='cms/model/save.jhtm';
var loadUrl='cms/model/load.jhtm';
var modelTitle='模型';
var gridPanelDiv='cmsModelGrid';
var focusField='obj.name';
var fields=['modelId','name', 'tableName','entityClass'];
CmsModelTreePanel=function(){
	var _tree=this;
	CmsModelTreePanel.superclass.constructor.call(this, {
		el:'cmsModelTree',
        autoScroll:true,
        animate:true,
        enableDD:false,
		split:true,
		border:true,
		title:'模型列表',
		width:200,
		minSize: 180,
		maxSize: 250,
		rootVisible:false,
		containerScroll: true, 
		margins: '0 0 0 0',
		
        loader: new Ext.tree.TreeLoader({
            dataUrl:'cms/model/tree.jhtm'
        })
	});
	
	_tree.getSelectionModel().on({
        'beforeselect' : function(sm, node){
			if(node.id!='0'){
				if(!node.expanded){
					node.expand();
				};
				//gridPanel.loadData(node.id);
			}
        
        },
        scope:this
    });
    var _root = new Ext.tree.AsyncTreeNode({
        text: '全部模型',
        draggable:false,
        id:'0'
    });
    _tree.setRootNode(_root);
	
	_tree.render();
	//_root.select();
	_root.expand();
};


Ext.extend(CmsModelTreePanel, Ext.tree.TreePanel, {
	loadData : function(id) {
		
	},
	
	loadAttributes:function(id){
		
	},
	loadFunctions:function(id){
		
	}
});


CmsModelFormPanel=function(){
	var _formPanel=this;
	var _form=this.getForm();
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
	    name : "obj.modelId",
		hideLabel:true,
	    readOnly:true,
		hidden:true,
        value:0
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : modelTitle+"名称",
		allowBlank : false,
		anchor:'95%',
		name : "obj.name"
	};
	
	this.entityClassField = {
		xtype : 'textfield',
		fieldLabel : "实体类",
		allowBlank : false,
		anchor:'95%',
		name : "obj.entityClass"
	};
	this.tableNameField = {
		xtype : 'textfield',
		fieldLabel : "表名",
		allowBlank : false,
		anchor:'95%',
		name : "obj.tableName"
	};
	this.hasChildField = {
		xtype : 'checkbox',
		fieldLabel : "允许子栏目",
		allowBlank : false,
		anchor:'95%',
		name : "obj.hasChild"
	};
	this.showField = {
		xtype : 'checkbox',
		fieldLabel : "是否显示",
		anchor:'95%',
		allowBlank : false,
		name : "obj.show"
	};
	this.sequenceField = {
		xtype : 'textfield',
		fieldLabel : "显示顺序",
		anchor:'95%',
		allowBlank : false,
		name : "obj.sequence"
	};
	CmsModelFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:230,
		region:'north',
		labelWidth:80,
        height:100,
        layout:'column',
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : pk
		}, [
		    {name:'obj.modelId', mapping:pk},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.entityClass',mapping:'entityClass'},
		    {name:'obj.tableName',mapping:'tableName'},
		    {name:'obj.hasChild',mapping:'hasChild'},
		    {name:'obj.show',mapping:'show'},
		    {name:'obj.sequence',mapping:'sequence'}
		    ]
		),
		
		items : [
					   {layout:'form',columnWidth:.5,items:[this.idField,this.nameField, this.tableNameField,this.hasChildField]},
		               {layout:'form',columnWidth:.5,items:[this.entityClassField,this.sequenceField,this.showField]}
				   
				
                ]
                 
	});
};

Ext.extend(CmsModelFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = loadUrl+'?id=' + id;
		var _form=this.getForm();
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			failure : function(form, action) {
				var json = action.response.responseText;
				var o = eval("(" + json + ")");
				Ext.MessageBox.show( {
					title : '出现错误',
					msg : o.message,
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	}
});

CmsAttributeGridPanel=function(){
	var _grid=this;
	var _win=this.window;
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: listUrl
        }),
        reader: new Ext.data.JsonReader({
            root: 'objs',
            totalProperty: 'totalCount',
            id: pk,
            fields:fields 
        }
        ),
        remoteSort: true
	});
    this.store.setDefaultSort(pk, 'asc');
	this.store.on('load',function(s,r,o){
		if(s.getTotalCount()>0){
			sm.selectFirstRow();
		}
	});
	 this.store.load({params:{start:0,limit:18}});
	
	this.cm = new Ext.grid.ColumnModel([sm,{
        id: pk,
        header: "ID",
        dataIndex: pk,
        width: 20
     },{
        id: 'name', 
        header: modelTitle+"名称",
        dataIndex: 'name',
        width: 100
     },{
        header: modelTitle+"目录",
        dataIndex: 'packageName',
        width: 100,
        sortable:false
     }]);

    this.cm.defaultSortable = true;
	CmsAttributeGridPanel.superclass.constructor.call(this, {
		layout : 'fit',
		sm : sm,
		trackMouseOver : true,
		frame : false,
		autoScroll : true,
		loadMask : true,
		viewConfig : {
			enableRowBody : true,
			showPreview : true,
			forceFit : true
		},
		bbar : new Ext.PagingToolbar( {
			pageSize : 18,
			store : _grid.store,
			displayInfo : true
		}),
		tbar : [
			
			{
					text : '新增属性',
					iconCls : 'x-btn-text-icon add',
					scope : this,
					handler : _grid.addInfo
				}, '-', {
					text : '修改属性',
					iconCls : 'x-btn-text-icon edit',
					scope : this,
					handler : _grid.loadInfo
				}, '-', {
					text : '删除属性',
					iconCls : 'x-btn-text-icon delete',
					scope : this,
					handler : _grid.deleteInfo
				}
		],
		renderTo : 'cmsAttributeGrid'	
	});
};


Ext.extend(CmsAttributeGridPanel, Ext.grid.GridPanel, {
	loadData : function(id) {
		
	}
});

CmsFunctionGridPanel=function(){
	var _grid=this;
	var _win=this.window;
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: listUrl
        }),
        reader: new Ext.data.JsonReader({
            root: 'objs',
            totalProperty: 'totalCount',
            id: pk,
            fields:fields 
        }
        ),
        remoteSort: true
	});
    this.store.setDefaultSort(pk, 'asc');
	this.store.on('load',function(s,r,o){
		if(s.getTotalCount()>0){
			sm.selectFirstRow();
		}
	});
	 this.store.load({params:{start:0,limit:18}});
	
	this.cm = new Ext.grid.ColumnModel([sm,{
        id: pk,
        header: "ID",
        dataIndex: pk,
        width: 20
     },{
        id: 'name', 
        header: modelTitle+"名称",
        dataIndex: 'name',
        width: 100
     },{
        header: modelTitle+"目录",
        dataIndex: 'packageName',
        width: 100,
        sortable:false
     }]);

    this.cm.defaultSortable = true;
    CmsFunctionGridPanel.superclass.constructor.call(this, {
		layout : 'fit',
		sm : sm,
		trackMouseOver : true,
		frame : false,
		autoScroll : true,
		loadMask : true,
		viewConfig : {
			enableRowBody : true,
			showPreview : true,
			forceFit : true
		},
		bbar : new Ext.PagingToolbar( {
			pageSize : 18,
			store : _grid.store,
			displayInfo : true
		}),
		tbar : [
			
			{
					text : '新增功能',
					iconCls : 'x-btn-text-icon add',
					scope : this,
					handler : _grid.addInfo
				}, '-', {
					text : '修改功能',
					iconCls : 'x-btn-text-icon edit',
					scope : this,
					handler : _grid.loadInfo
				}, '-', {
					text : '删除功能',
					iconCls : 'x-btn-text-icon delete',
					scope : this,
					handler : _grid.deleteInfo
				}
		],
		renderTo : 'cmsFunctionGrid'	
	});
};
Ext.extend(CmsFunctionGridPanel, Ext.grid.GridPanel, {
	loadData : function(id) {
		
	}
});

CmsAttributeFormPanel=function(){
	var _formPanel=this;
	var _form=this.getForm();
	CmsAttributeFormPanel.superclass.constructor.call(this, {
		
	});
};
Ext.extend(CmsAttributeFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		
	}
});
CmsFunctionFromPanel=function(){
	var _formPanel=this;
	var _form=this.getForm();
	CmsFunctionFromPanel.superclass.constructor.call(this, {
		
	});
};
Ext.extend(CmsFunctionFromPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		
	}
});
CmsAttributeWindow=function(){
	
};

CmsFunctionWindow=function(){
	
};

var CmsModelApp=function(){
	var _treePanel;
	var _modelFormPanel;
	var _attributeGridPanel;
	var _functionGridPanel;
	return {
		init:function(){
			_treePanel=new CmsModelTreePanel();
			_modelFormPanel=new CmsModelFormPanel();
			_attributeGridPanel=new CmsAttributeGridPanel();
		    _functionGridPanel=new CmsFunctionGridPanel();
			var westPanel=new Ext.Panel({
				region:'west',
				el:'cmsModelTreePanel',
				contentEl:'cmsModelTree',
				layout:'fit',
				collapsible:true,
				split:true,
				height:200,
				items:[_treePanel]
			});
			
			var centerTop=new Ext.Panel({
				region:'north',
				layout:'fit',
				height:130,
				tbar:[{
				  	text: '添加',
		            iconCls: 'add',
					scope:this,
					handler: function(){
						
					}
				  },'-',{
					  	text: '保存',
			            iconCls: 'save',
						scope:this,
						handler: function(){
							
						}
					  },'-',{
				  	text: '删除',
		            iconCls: 'delete',
					scope:this,
					handler: function(){
						
					}
				  }]
				,
				items:[_modelFormPanel]
			});
			/*
			var attributeGridPanel=new Ext.Panel({
				region:'west',
				layout:'fit',
				items:[_attributeGridPanel]
			});
			
			var functionGridPanel=new Ext.Panel({
				region:'center',
				layout:'fit',
				items:[_functionGridPanel]
			});
			*/
			var gridPanel=new Ext.TabPanel({
				height:200,
				renderTo:'cmsModelTab',
				activeTab: 0,
			    items: [{title:'模型属性',layout:'fit',el:'cmsAttributeCenter',contentEl:'cmsAttributeGrid',items:[_attributeGridPanel]},
			            {title:'模型功能',layout:'fit',el:'cmsFunctionCenter', contentEl:'cmsFunctionGrid',items:[_functionGridPanel]}
			    ]
			});
			
			
			var centerPanel=new Ext.Panel({
				region:'center',
				layout:'border',
				title:'模型信息',
				height:300,
				items:[centerTop,new Ext.Panel({region:'center',layout:'fit',contentEl:'cmsModelTab',items:[gridPanel]})]
			});
		
			var modelPanel=new Ext.Panel({
				layout:'border',
				width:200,
				height:200,
				items:[westPanel,centerPanel]
			});
			FineCmsMain.addFunctionPanel(modelPanel);
		},
		getTreePanel:function(){
			return _treePanel;
		},
		getModelFormPanel:function(){
			return _modelFormPanel;
		},
		getAttributeGridPanel:function(){
			return _attributeGridPanel;
		}
	};
}();

CmsModelApp.init();