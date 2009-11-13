var pk="categoryId";
var listUrl='cms/model/list.jhtm';
var deleteUrl='cms/model/delete.jhtm';
var saveUrl='cms/model/save.jhtm';
var loadUrl='cms/model/load.jhtm';
var modelTitle='栏目';
var focusField='obj.name';
var selectModelId=0;
CmsCategoryTreePanel=function(){
	var _tree=this;
	this.formPanel;
	CmsCategoryTreePanel.superclass.constructor.call(this, {
		el:'cmsCategoryTree',
        autoScroll:true,
        animate:true,
        enableDD:false,
		split:true,
		border:true,
		title:'栏目列表',
		width:200,
		minSize: 180,
		maxSize: 250,
		rootVisible:false,
		containerScroll: true, 
		margins: '0 0 0 0',
        loader: new Ext.tree.TreeLoader({
            dataUrl:'cms/category/all.jhtm'
        })
	});
	
	_tree.getSelectionModel().on({
        'beforeselect' : function(sm, node){
			if(node.id!='0'){
				if(!node.expanded){
					node.expand();
				};
				if(node.getDepth()>1){
					selectModelId=node.id;
					_tree.loadData(node.id);
				}
			}
        
        },
        scope:this
    });
    var _root = new Ext.tree.AsyncTreeNode({
        text: '全部栏目',
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


Ext.extend(CmsCategoryTreePanel, Ext.tree.TreePanel, {
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


CmsCategoryFormPanel=function(){
	var _formPanel=this;
	this.treePanel;
	var _form=this.getForm();
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
	    name : "obj.categoryId",
		hideLabel:true,
	    readOnly:true,
		hidden:true,
        value:0
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : modelTitle+"名称",
		allowBlank : false,
		name : "obj.name"
	};
	
	this.titleField = {
		xtype : 'textfield',
		fieldLabel : "栏目标题",
		allowBlank : false,
		name : "obj.title"
	};

	var typeStore = new Ext.data.SimpleStore({
        fields: ['_value', 'name','tip'],
        data : Ext.ux.CmsCategoryType
    });
    this.typeField = new Ext.form.ComboBox({
        store: typeStore,
        displayField:'name',
        valueField:'_value',
        fieldLabel : "栏目类型",
        typeAhead: true,
        mode: 'local',
        width : 120,
        triggerAction: 'all',
        emptyText:'请选择类型...',
        blankText : '请选择类型...',
        selectOnFocus:true,
        value:'co',
        hiddenName : 'obj.type'
    });
	
	this.allowpostField = {
		xtype : 'checkbox',
		fieldLabel : "允许投稿",
		allowBlank : false,
		inputValue:'true',
		name : "obj.allowpost"
	};
	
    this.modelStore=new Ext.data.Store({    
        proxy: new Ext.data.HttpProxy({url: 'cms/model/all.jhtm'}),    
        reader: new Ext.data.JsonReader({    
            id:"id",totalProperty:'totalCount',root:'objs' 
        },['id','text']),    
        remoteSort: true,
        autoLoad:true
    }); 

    this.modelField = new Ext.form.ComboBox({
        store: this.modelStore,
        fieldLabel: '选择模型',
        displayField:'text',
        valueField:'id',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择模型...',
        blankText:'请选择模型...',
        selectOnFocus:true,
        allowBlank:false,
        hiddenName: 'obj.model.modelId'
    });
    
   
    this.groupStore=new Ext.data.Store({    
    	proxy: new Ext.data.HttpProxy({url: 'cms/group/all.jhtm'}),    
    	reader: new Ext.data.JsonReader({    
    		id:"id",totalProperty:'totalCount',root:'objs' 
    	},['id','name']),    
    	remoteSort: true,
    	autoLoad:true
    }); 
    
    this.groupField = new Ext.form.ComboBox({
    	store: this.groupStore,
    	fieldLabel: '访问权限',
    	displayField:'name',
    	valueField:'id',
    	typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	emptyText:'请选择权限...',
    	blankText:'请选择权限...',
    	selectOnFocus:true,
    	allowBlank:false,
    	hiddenName: 'obj.group.grouplId'
    });
	
    this.siteStore=new Ext.data.Store({    
    	proxy: new Ext.data.HttpProxy({url: 'cms/site/all.jhtm'}),    
    	reader: new Ext.data.JsonReader({    
    		id:"id",totalProperty:'totalCount',root:'objs' 
    	},['id','text']),    
    	autoLoad:true
    }); 
    
    this.siteField = new Ext.form.ComboBox({
    	store: this.siteStore,
    	fieldLabel: '选择站点',
    	displayField:'text',
    	valueField:'id',
    	typeAhead: true,
    	mode: 'local',
    	triggerAction: 'all',
    	emptyText:'请选择站点...',
    	blankText:'请选择站点...',
    	selectOnFocus:true,
    	allowBlank:false,
    	hiddenName: 'obj.site.siteId'
    });
    
	this.showField = {
		xtype : 'checkbox',
		fieldLabel : "是否显示",
		allowBlank : false,
		inputValue:'true',
		name : "obj.show"
	};
	this.sequenceField = {
		xtype : 'textfield',
		fieldLabel : "显示顺序",
		allowBlank : false,
		name : "obj.sequence"
	};
	CmsCategoryFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:230,
		region:'north',
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : pk
		}, [
		    {name:'obj.categoryId', mapping:pk},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.title',mapping:'title'},
		    {name:'obj.allowpost',mapping:'allowpost'},
		    {name:'obj.type',mapping:'type'},
		    {name:'obj.group.groupId',mapping:'groupId'},
		    {name:'obj.site.siteId',mapping:'siteId'},
		    {name:'obj.model.modelId',mapping:'modelId'},
		    {name:'obj.show',mapping:'show'},
		    {name:'obj.sequence',mapping:'sequence'}
		    ]
		),
		items : [
					   this.idField,this.nameField,this.titleField,
					   this.siteField,this.typeField, this.modelField, 
					   this.groupField,this.sequenceField,this.allowpostField, this.showField
                ]
                 
	});
	 this.modelField.on('select',function(){
	    	alert(this.getValue());
	    });
};

Ext.extend(CmsCategoryFormPanel, Ext.form.FormPanel, {
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
					Ext.Msg.alert('保存','保存栏目信息成功！');;
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
		_form.findField("obj.categoryId").setValue(0);
		_form.findField("obj.name").setValue('');
		_form.findField("obj.title").setValue('');
		_form.findField("obj.type").setValue('');
		_form.findField("obj.allowpost").setValue(true);
		_form.findField("obj.show").setValue(true);
		_form.findField("obj.name").focus(false,true);
	}
});



var CmsCategoryApp=function(){
	var _treePanel;
	var _modelFormPanel;
	return {
		init:function(){
			_treePanel=new CmsCategoryTreePanel();
			_modelFormPanel=new CmsCategoryFormPanel();
			
		    _treePanel.formPanel=_modelFormPanel;
		    _modelFormPanel.treePanel=_treePanel;

			var westPanel=new Ext.Panel({
				region:'west',
				el:'cmsCategoryTreePanel',
				contentEl:'cmsCategoryTree',
				layout:'fit',
				frame:false,
				collapsible:true,
				split:true,
				height:200,
				items:[_treePanel]
			});
			var modelPanel=new Ext.Panel({
				
			});
			var centerTop=new Ext.Panel({
				region:'north',
				frame:false,
				layout:'fit',
				height:130,
				tbar:[{
				  	text: '添加栏目',
		            iconCls: 'add',
					scope:this,
					handler: function(){
						_modelFormPanel.addData();
					}
				  },'-',{
					  	text: '保存栏目',
			            iconCls: 'save',
						scope:this,
						handler: function(){
					  		_modelFormPanel.saveData();
						}
					  },'-',{
				  	text: '删除栏目',
		            iconCls: 'delete',
					scope:this,
					handler: function(){
						  _treePanel.deleteNode();
					}
				  }]
				,
				items:[_modelFormPanel]
			});

		
			var centerPanel=new Ext.Panel({
				region:'center',
				layout:'fit',
				title:'栏目信息',
				tbar:[{
				  	text: '添加栏目',
		            iconCls: 'add',
					scope:this,
					handler: function(){
						_modelFormPanel.addData();
					}
				  },'-',{
					  	text: '保存栏目',
			            iconCls: 'save',
						scope:this,
						handler: function(){
					  		_modelFormPanel.saveData();
						}
					  },'-',{
				  	text: '删除栏目',
		            iconCls: 'delete',
					scope:this,
					handler: function(){
						  _treePanel.deleteNode();
					}
				  }],
				height:300,
				items:[_modelFormPanel]
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

CmsCategoryApp.init();