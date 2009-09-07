var pk="modelId";
var listUrl='cms/model/list.jhtm';
var deleteUrl='cms/model/delete.jhtm';
var saveUrl='cms/model/save.jhtm';
var loadUrl='cms/model/load.jhtm';
var modelTitle='模型';
var gridPanelDiv='cmsModelGrid';
var focusField='obj.name';
var fields=['modelId','name', 'tableName','entityClass'];
var selectModelId=0;
CmsModelTreePanel=function(){
	var _tree=this;
	this.formPanel;
	this.attributeGrid;
	this.functionGrid;
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
				selectModelId=node.id;
				_tree.loadData(node.id);
				_tree.attributeGrid.loadData(node.id);
				_tree.functionGrid.loadData(node.id);
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
	 _root.on('expand',function(n){
	    	if(this.firstNode){
	    		this.firstNode.select();
	    	}
	    });
	_root.expand();
};


Ext.extend(CmsModelTreePanel, Ext.tree.TreePanel, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	},
	getModelId:function(){
		var tree=this;
		var id=tree.getSelectionModel().getSelectedNode().id;
		return id;
	},
	deleteNode:function(id){
		var node=this.getSelectionModel().getSelectedNode();
		if(!node){
			Ext.Msg.alert("删除"+modelTitle,"请选择"+modelTitle+"！");
			return;
		}
		
		if(!confirm("确定要删除选中的"+modelTitle+"吗？")){
			return;
		}
		var url=deleteUrl+'?ids='+node.id;
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var tree=this;
		var attributeGrid=this.attributeGrid;
		var functionGrid=this.functionGrid;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("删除"+modelTitle+"","删除"+modelTitle+"成功！");
				var node1=node.nextSibling;
				var next=false
				if(node1){
					node1.select();
					next=true;
					
				}else{
					var node2=node.previousSibling;
					if(node2){
						node2.select();
						next=true;
					}else{
						tree.addNode();
						
					}
				}
				if(!next){
					tree.getRootNode().removeChild(node);
					tree.formPanel.addData();
					attributeGrid.store.removeAll();
					functionGrid.store.removeAll();
				}else{
					node.remove();
					tree.getRootNode().reload();
				}
			}
		}
		function failure(rep){
			Ext.Msg.alert("删除"+modelTitle,rep.repsonseText);
		}
	}
	,
	addNode:function(id,text){
		var root=this.getRootNode();
		//var node=Ext.tree.TreeNode({id:id,text:text,leaf:true});
		root.appendChild({id:id,text:text,leaf:true});
	}
});


CmsModelFormPanel=function(){
	var _formPanel=this;
	this.treePanel;
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
		inputValue:'true',
		name : "obj.hasChild"
	};
	this.showField = {
		xtype : 'checkbox',
		fieldLabel : "是否显示",
		anchor:'95%',
		allowBlank : false,
		inputValue:'true',
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
	},
	saveData:function(){
		var _form=this.getForm();
		var tree=this.treePanel;
		if (_form.isValid()) {
			_form.submit( {
				waitMsg : '正在保存数据...',
				url : saveUrl,
				failure : function(form, action) {
					var json = action.response.responseText;
					var o = eval("(" + json + ")");
					Ext.MessageBox.show( {
						title : '出现错误',
						msg : o.message,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				},
				success : function(form1, action) {
					Ext.Msg.alert('保存','保存模型信息成功！');;
					var json = action.response.responseText;
					var o = eval("(" + json + ")");
					var id=o.id;
					var text=_form.findField("obj.name").getValue();
					tree.addNode(id,text);
				}
			});
		}
	},
	addData:function(){
		var _form=this.getForm();
		_form.findField("obj.modelId").setValue(0);
		_form.findField("obj.name").setValue('');
		_form.findField("obj.entityClass").setValue('');
		_form.findField("obj.tableName").setValue('');
		_form.findField("obj.hasChild").setValue(true);
		_form.findField("obj.show").setValue(true);
		_form.findField("obj.name").focus(false,true);
	}
});

CmsAttributeGridPanel=function(){
	var _grid=this;
	
	var _win=new CmsAttributeWindow();
	this.window=_win;
	this.modelId;
	_win.grid=this;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var fields=['attributeId','name', 'label'];
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'cms/attribute/list.jhtm?modelId='+_grid.modelId
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
    this.store.setDefaultSort('attributeId', 'asc');
	this.store.on('load',function(s,r,o){
		if(s.getTotalCount()>0){
			sm.selectFirstRow();
		}
	});
	
	this.cm = new Ext.grid.ColumnModel([sm,{
        id: 'attributeId',
        header: "ID",
        dataIndex: 'attributeId',
        width: 20
     },{
        id: 'name', 
        header: "名称",
        dataIndex: 'name',
        width: 100
     },{
        header: "标签",
        dataIndex: 'label',
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
		this.modelId=id;
		this.store.proxy= new Ext.data.HttpProxy({
            url: 'cms/attribute/list.jhtm?modelId='+id
        });
		this.store.load({params:{start:0,limit:10}});
	},
	addInfo:function(){

		this.window.show();
		this.window.formPanel.getForm().reset();
	},
	loadInfo:function(){
		var id=this.getSelectionModel().getSelected().get('attributeId');
		this.window.show();
		this.window.formPanel.loadData(id);
	},
	deleteInfo:function(){
		var sm=this.getSelectionModel();
		var c=this;
		var _grid1=this;
		if(sm.getSelected==null){
			Ext.Msg.alert("删除属性","请先选择一个属性！");
			return;
		}else{
			var s=Ext.Msg.confirm("删除属性","确定要删除选中的属性吗？",function(o){
				if(o=='yes'){
					var url='cms/attribute/delete.jhtm?'+_grid1.getSelectedIds();
					Ext.Ajax.request({
						url:url,
						success:success,
						failure:failure
					});
				}else{
					return;
				}
			});
		}
		function success(rep){
			eval("var result="+rep.responseText);
			var _success=result.success;
			var totalCount=result.totalCount;
			var pageSize=10;
			if(_success){
				var store=_grid1.store;
				var lastO= store.lastOptions.params;
				var start=lastO.start;
				var totRecords= new Number(totalCount);
				if(start>0&&start>=totRecords){
					store.load({params:{start:start-pageSize, limit:pageSize}});
				}else{
					store.load({params:{start:start, limit:pageSize}});
				}
				Ext.Msg.alert("删除属性","删除属性成功！");
			}else{
				Ext.Msg.alert("删除属性","删除属性失败！");
			}
			
		}
		function failure(rep){
			alert("删除失败！");
			alert(rep.responseText);
		}
	},
	getSelectedIds:function(){
		var ids="";
		var selections=this.getSelectionModel().getSelections();
		var size=selections.length;
		for(var i=0;i<size;i++){
				var r=selections[i];
				if(ids.length==0){
					ids='ids='+r.get("attributeId");
				}else{
					ids+="&ids="+r.get("attributeId");
				}
		}
		return ids;
		
	},
	reload:function(){
		this.store.reload();
	}
});

CmsFunctionGridPanel=function(){
	var _grid=this;
	var _win= new CmsFunctionWindow();
	this.window=_win;
	_win.grid=this;
	this.modelId;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var fields=['functionId','name', 'url','show'];
	this.window;
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'cms/function/list.jhtm?modelId=0'
        }),
        reader: new Ext.data.JsonReader({
            root: 'objs',
            totalProperty: 'totalCount',
            id: 'functionId',
            fields:fields 
        }
        ),
        remoteSort: true
	});
    this.store.setDefaultSort('functionId', 'asc');
	this.store.on('load',function(s,r,o){
		if(s.getTotalCount()>0){
			sm.selectFirstRow();
		}
	});
	
	this.cm = new Ext.grid.ColumnModel([sm,{
        id: 'functionId',
        header: "ID",
        dataIndex: 'functionId',
        width: 30
     },{
        id: 'name', 
        header: "名称",
        dataIndex: 'name',
        width: 60
     },{
        header: "URL",
        dataIndex: 'url',
        width: 60,
        sortable:false
     },{
         header: "显示",
         dataIndex: 'show',
         width: 60,
         renderer:rendererEnabled,
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
		this.modelId=id;
		this.store.proxy= new Ext.data.HttpProxy({
	        url: 'cms/function/list.jhtm?modelId='+id
	    });
		this.store.load({params:{start:0,limit:10}});
	},
	addInfo:function(){
		this.window.show();
		this.window.formPanel.getForm().reset();
	},
	loadInfo:function(){
		var id=this.getSelectionModel().getSelected().get('functionId');
		this.window.show();
		this.window.formPanel.loadData(id);
	},
	deleteInfo:function(){
		var sm=this.getSelectionModel();
		var _grid1=this;
		if(sm.getSelected==null){
			Ext.Msg.alert("删除功能","请先选择一个功能！");
			return;
		}else{
			var s=Ext.Msg.confirm("删除功能","确定要删除选中的功能吗？",function(o){
				if(o=='yes'){
					var url='cms/function/delete.jhtm?'+_grid1.getSelectedIds();
					Ext.Ajax.request({
						url:url,
						success:success,
						failure:failure
					});
				}else{
					return;
				}
			});
		}
		function success(rep){
			eval("var result="+rep.responseText);
			var _success=result.success;
			var totalCount=result.totalCount;
			var pageSize=10;
			if(_success){
				var store=_grid1.store;
				var lastO= store.lastOptions.params;
				var start=lastO.start;
				var totRecords= new Number(totalCount);
				if(start>0&&start>=totRecords){
					store.load({params:{start:start-pageSize, limit:pageSize}});
				}else{
					store.load({params:{start:start, limit:pageSize}});
				}
				Ext.Msg.alert("删除功能","删除功能成功！");
			}else{
				Ext.Msg.alert("删除功能","删除功能失败！");
			}
			
		}
		function failure(rep){
			alert("删除失败！");
			alert(rep.responseText);
		}
	},
	getSelectedIds:function(){
		var ids="";
		var selections=this.getSelectionModel().getSelections();
		var size=selections.length;
		for(var i=0;i<size;i++){
				var r=selections[i];
				if(ids.length==0){
					ids='ids='+r.get("functionId");
				}else{
					ids+="&ids="+r.get("functionId");
				}
		}
		return ids;
		
	},
	reload:function(){
		this.store.reload();
	}
});



CmsAttributeFormPanel = function() {
	this.idField = {
		xtype : 'hidden',
		fieldLabel : "id",
		name : "obj.attributeId",
		value:'0'
	};
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "名称",
		allowBlank : false,
		name : "obj.name"
	};
	this.labelField = {
		xtype : 'textfield',
		fieldLabel : "标签",
		allowBlank : false,
		name : "obj.label"
	};
	
    CmsAttributeFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		reader : new Ext.data.JsonReader( {
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'attributeId'
		}, [
		    {name:'obj.attributeId', mapping:'attributeId'},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.label',mapping:'label'},
		    {name:'obj.model.modelId',mapping:'modelId'}
		    ]
		),
		items : [this.idField, this.nameField, this.labelField]
	});
};
Ext.extend(CmsAttributeFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'cms/attribute/load.jhtm?id=' + id;
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





CmsFunctionFormPanel = function() {
	this.idField = {
		xtype : 'hidden',
		fieldLabel : "id",
		name : "obj.functionId",
		value:'0'
	};
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "名称",
		allowBlank : false,
		name : "obj.name"
	};
	this.urlField = {
		xtype : 'textfield',
		fieldLabel : "URL",
		allowBlank : false,
		name : "obj.url"
	};
	this.showField = {
		xtype : 'checkbox',
		fieldLabel : "显示",
		allowBlank : false,
		inputValue:'true',
		name : "obj.show"
	};
	
	
	CmsFunctionFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		reader : new Ext.data.JsonReader( {
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'functionId'
		}, [
		    {name:'obj.functionId', mapping:'functionId'},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.url',mapping:'url'},
		    {name:'obj.show',mapping:'show'}
		    ]
		),
		items : [this.idField, this.nameField, this.urlField,this.showField]
	});
};
Ext.extend(CmsFunctionFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'cms/function/load.jhtm?id=' + id;
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

CmsAttributeWindow = function() {
	var _win = this;
	this.formPanel = new CmsAttributeFormPanel();
	var _form = this.formPanel.getForm();
	this.form=_form;
	this.grid;
	CmsAttributeWindow.superclass.constructor.call(this, {
		title : '属性信息',
		width : 320,
		height : 180,
		
		resizable : true,
		plain : false,
		border : false,
		modal : true,
		autoScroll : true,
		layout : 'fit',
		closeAction : 'hide',
		items : this.formPanel,
		buttons : [ {
			text : '保存',
			handler : function() {
				if (_form.isValid()) {
					
					_form.submit( {
						waitMsg : '正在保存数据...',
						url : 'cms/attribute/save.jhtm?obj.model.modelId='+selectModelId,
						failure : function(form, action) {
							var json = action.response.responseText;
							var o = eval("(" + json + ")");
							Ext.MessageBox.show( {
								title : '出现错误',
								msg : o.message,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
						},
						success : function(form1, action) {
							_win.grid.reload();
						}
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				_win.hide();
			},
			tooltip : '关闭窗口'
		}]
	});
}
Ext.extend(CmsAttributeWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});



CmsFunctionWindow = function() {
	var _win = this;
	this.formPanel = new CmsFunctionFormPanel();
	var _form = this.formPanel.getForm();
	this.grid;
	CmsFunctionWindow.superclass.constructor.call(this, {
		title : '属性信息',
		width : 320,
		height : 180,
		
		resizable : true,
		plain : false,
		border : false,
		modal : true,
		autoScroll : true,
		layout : 'fit',
		closeAction : 'hide',
		items : this.formPanel,
		buttons : [ {
			text : '保存',
			handler : function() {
				if (_form.isValid()) {
					_form.submit( {
						waitMsg : '正在保存数据...',
						url : 'cms/function/save.jhtm?obj.model.modelId='+selectModelId,
						failure : function(form, action) {
							var json = action.response.responseText;
							var o = eval("(" + json + ")");
							Ext.MessageBox.show( {
								title : '出现错误',
								msg : o.message,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
						},
						success : function(form1, action) {
							_win.grid.reload();
						}
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				_win.hide();
			},
			tooltip : '关闭窗口'
		}]
	});
}
Ext.extend(CmsFunctionWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});



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
		    _treePanel.formPanel=_modelFormPanel;
		    _modelFormPanel.treePanel=_treePanel;
		    _treePanel.attributeGrid=_attributeGridPanel;
		    _treePanel.functionGrid=_functionGridPanel;
			var westPanel=new Ext.Panel({
				region:'west',
				el:'cmsModelTreePanel',
				contentEl:'cmsModelTree',
				layout:'fit',
				frame:false,
				collapsible:true,
				split:true,
				height:200,
				items:[_treePanel]
			});
			
			var centerTop=new Ext.Panel({
				region:'north',
				frame:false,
				layout:'fit',
				height:130,
				tbar:[{
				  	text: '添加模型',
		            iconCls: 'add',
					scope:this,
					handler: function(){
						_modelFormPanel.addData();
					}
				  },'-',{
					  	text: '保存模型',
			            iconCls: 'save',
						scope:this,
						handler: function(){
					  		_modelFormPanel.saveData();
						}
					  },'-',{
				  	text: '删除模型',
		            iconCls: 'delete',
					scope:this,
					handler: function(){
						  _treePanel.deleteNode();
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
			*/
			var functionGridPanel=new Ext.Panel({
				layout:'fit',
				width:200,
				frame:false,
				el:'cmsFunctionCenter',
				height:100,
				contentEl:'cmsFunctionGrid',
				items:[_functionGridPanel]
			});
			var attributeGridPanel=new Ext.Panel({
				layout:'fit',
				width:200,
				frame:false,
				el:'cmsAttributeCenter',
				height:100,
				contentEl:'cmsAttributeGrid',
				items:[_attributeGridPanel]
			});
			
			var tabPanel=new Ext.TabPanel({
				height:200,
				renderTo:'cmsModelTab',
				activeTab: 0,
				frame:false,
			    items: [{title:'模型属性',id:'attribute',frame:false,layout:'fit',items:attributeGridPanel},
			            {title:'模型功能',id:'function',frame:false,layout:'fit',items:functionGridPanel}
			    ]
			});
			tabPanel.setActiveTab('function');
			tabPanel.setActiveTab('attribute');
			var centerPanel=new Ext.Panel({
				region:'center',
				layout:'border',
				title:'模型信息',
				height:300,
				items:[centerTop,new Ext.Panel({region:'center',layout:'fit',items:[tabPanel]})]
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