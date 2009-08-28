var pageSize=18;
var relationForm;
var FrameModelElseGridPanel=function(){
	this.modelId=0;
	var _grid = this;
	this.primaryKey='modelId';
	this.renderToDiv='noRelationModelGrid';
  
	var recordType = Ext.data.Record.create([ {
		name : 'modelId',
		type : 'int',
		mapping : 'modelId'
	},{
		name : 'label',
		type : 'string',
		mapping : 'label'
	},{
		name : 'name',
		type : 'string',
		mapping : 'name'
	}]);
	this.store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'frame/modelrelation/else.jhtm?mid='+this.mid
		}),
		reader : new Ext.data.JsonReader( {
			root : 'objs',
			totalProperty : 'totalCount',
			id : this.primaryKey
		}, recordType)
	});
	_grid.store.setDefaultSort(this.primaryKey, 'desc');
	var sm = new Ext.grid.CheckboxSelectionModel( {
		singleSelect : true
	});
	this.columns = [new Ext.grid.RowNumberer(), {
		id : 'modelId',
		header : "编号",
		width : 40,
		sortable : true,
		align:'center',
		resizable: false,
		dataIndex : 'modelId'
	},{
		header : "模型名称",
		width : 75,
		sortable : true,
		dataIndex : 'label'
	}, {
		header : "英文名称",
		width : 75,
		sortable : true,
		dataIndex : 'name'
	},sm];
	
	FrameModelElseGridPanel.superclass.constructor
			.call(
					this,
					{
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
							pageSize : 10,
							store : this.store,
							displayInfo : true
						}),
						renderTo : this.renderToDiv
						
					});
};
Ext.extend(FrameModelElseGridPanel, Ext.grid.GridPanel, {
	loadData:function(mid1){
		this.modelId=mid1;
		this.store.proxy= new Ext.data.HttpProxy( {
			url : 'frame/modelrelation/else.jhtm?mid='+this.modelId
		});
		this.store.load({params:{start:0,limit:10}});
	},
	getSelected : function() {
		var record = this.getSelectionModel().getSelected();
		if (record == null) {
			Ext.MessageBox.show( {
				title : 'Infomation',
				msg : "please select record",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		} else {
			return record;
		}
	}
});


var FrameModelElseWindow = function() {
	var _win = this;
	this.modelId=0;
	this.relationForm;
	
	this.gridPanel = new FrameModelElseGridPanel();
	var _grid=this.gridPanel;

	FrameModelElseWindow.superclass.constructor.call(this, {
		title : '选择模型窗口',
		width : 370,
		height : 250,
		el:'noRelationModelWindow',
		contentEl:'noRelationModelGrid',
		resizable : true,
		plain : false,
		border : false,
		modal : true,
		autoScroll : true,
		layout : 'fit',
		closeAction : 'hide',
		items : [this.gridPanel],
		buttons : [{
			text : '确定',
			handler : function() {
				var sel=_grid.getSelectionModel().getSelected();
				relationForm.createNew(sel);
				
				//f_.findField("obj.relationId").setRawValue(0);
				//f_.findField("obj.relationModel.modelId").setRawValue(sel.get("modelId"));
				//f_.findField("obj.relationModel.label").setRawValue(sel.get("label"));
				//f_.findField("obj.relation").setRawValue("manytomany");
				_win.hide();
			},
			tooltip : '关闭窗口'
		},{
			text : '关闭',
			handler : function() {
				_win.hide();
			},
			tooltip : '关闭窗口'
		} ]
	});
}
Ext.extend(FrameModelElseWindow, Ext.Window, {
	loadData:function(mid){
		this.gridPanel.loadData(mid);
	}
});




var FrameModelRelationFormPanel=function(){
	this.mid=0;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.relationId",
		value : '0'
	};
	this.gridPanel;
	var _form=this;
	this.relationModelIdField = {
			xtype : 'hidden',
			fieldLabel : "关联模型编号",
			name : "obj.relationModel.modelId"
		};
	this.modelIdField = {
			xtype : 'hidden',
			fieldLabel : "模型编号",
			name : "obj.model.modelId",
			value:seletedModelId
		};
	this.relationModelField = {
			xtype : 'textfield',
			fieldLabel : "关联模型",
			name : "obj.relationModel.label",
			readOnly:true
		};

	var modelRecordDef = Ext.data.Record.create( [ {
		name : 'modelId'
	}, {
		name : 'label'
	} ]);
	
    var relationStore = new Ext.data.SimpleStore({
        fields: ['value', 'label','tip'],
        data : Ext.ux.ModelRelationType,
        autoLoad:true
    });
    this.relationField = new Ext.form.ComboBox({
        store: relationStore,
        displayField:'label',
        valueField:'value',
        fieldLabel : "模型关系",
        typeAhead: true,
        mode: 'local',
        width : 200,
        triggerAction: 'all',
        emptyText:'请选择模型关系...',
        blankText : '请选择模型关系...',
        allowBlank : false,
        selectOnFocus:true,
        hiddenName : 'obj.relation'
    });
	
    
    this.saveBtn=new Ext.Button(
    	{
            text:'保存',
            handler:function(){
    			_form.save();
    		}
        }
    );
    FrameModelRelationFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		labelWidth: 60,
		labelAlign: 'top',
		defaults: {width: 130},
		frame : true,
		reader : new Ext.data.JsonReader( {
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'relationId'
		}, [ {
			name : 'obj.relationId',
			mapping : 'relationId'
		}, {
			name : 'obj.relationModel.modelId',
			mapping : 'relationModelId'
		},{
			name : 'obj.model.modelId',
			mapping : 'modelId'
		},{
			name : 'obj.relationModel.label',
			mapping : 'relationModelLabel'
		}, {
			name : 'obj.relationModel.name',
			mapping : 'relationModelName'
		}, {
			name : 'obj.relationModel.modelId',
			mapping : 'relationModelId'
		}, {
			name : 'obj.relation',
			mapping : 'relation'
		} ]),
		items : [ this.idField,this.relationModelIdField,this.modelIdField, this.relationModelField,this.relationField,this.saveBtn ]
	});
};
Ext.extend(FrameModelRelationFormPanel, Ext.form.FormPanel, {
		loadNoRelation:function(mid1){
			this.modelStore.proxy=new Ext.data.HttpProxy( {
					url : 'frame/modelrelation/else.jhtm?mid='+mid1
				});
			this.modelStore.reload();
		},
		createNew:function(sel){
			var form_=this.getForm();
			form_.findField('obj.model.modelId').setValue(seletedModelId);
			form_.findField('obj.relationId').setValue(0);
			form_.findField('obj.relationModel.modelId').setValue(sel.get('modelId'));
			form_.findField('obj.relationModel.label').setValue(sel.get("label"));
			form_.findField('obj.relation').setValue('manytomany');
		}
		,
		loadData:function(sel){
			var form_=this.getForm();
			form_.findField('obj.relationId').setValue(sel.get("relationId"));
			form_.findField('obj.relationModel.modelId').setValue(sel.get('relationModelId'));
			form_.findField('obj.relationModel.label').setValue(sel.get("relationModelLabel"));
			form_.findField('obj.relation').setValue(sel.get("relation"));
			
		},
		save:function(){
			var _form=this.getForm();
			var _grid=this.gridPanel;
			if (_form.isValid()) {
				_form.submit( {
					waitMsg : '正在保存数据...',
					url : 'frame/modelrelation/save.jhtm',
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
						_grid.reloadStore();

					}
				});
			}
		}
	
});
var FrameModelRelationGridPanel = function() {
	this.elseWindow=new  FrameModelElseWindow();
	
	var elseWindow=this.elseWindow;
	var _grid = this;
	this.modelId=0;
	this.primaryKey='relationId';
	this.renderToDiv='modelRelationGrid';
	this.relationForm;
  
	var recordType = Ext.data.Record.create([ {
		name : 'relationId',
		type : 'int',
		mapping : 'relationId'
	}, {
		name : 'modelId',
		type : 'int',
		mapping : 'modelId'
	}, {
		name : 'relationModelLabel',
		type : 'string',
		mapping : 'relationModelLabel'
	},{
		name : 'relationModelId',
		type : 'int',
		mapping : 'relationModelId'
	},{
		name : 'relationModelName',
		type : 'string',
		mapping : 'relationModelName'
	},{
		name : 'modelLabel',
		type : 'string',
		mapping : 'modelLabel'
	},{
		name : 'relation',
		type : 'string',
		mapping : 'relation'
	},{
		name : 'modelLabel',
		type : 'string',
		mapping : 'modelLabel'
	}]);
	this.store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'frame/modelrelation/listByModel.jhtm?mid='+this.mid
		}),
		reader : new Ext.data.JsonReader( {
			root : 'objs',
			totalProperty : 'totalCount',
			id : this.primaryKey
		}, recordType)
	});
	_grid.store.setDefaultSort(this.primaryKey, 'desc');
	//_grid.store.load({params:{start : 0,limit : 18}});
	var sm = new Ext.grid.CheckboxSelectionModel( {
		singleSelect : false
	});
	this.columns = [new Ext.grid.RowNumberer(), {
		id : 'relationId',
		header : "编号",
		width : 160,
		sortable : true,
		hidden:true,
		dataIndex : 'relationId'
	},{
		header : "模型名称",
		width : 75,
		sortable : true,
		dataIndex : 'relationModelLabel'
	}, {
		header : "英文名称",
		width : 75,
		sortable : true,
		dataIndex : 'relationModelName'
	}, {
		header : "关系",
		width : 75,
		sortable : true,
		dataIndex : 'relation',
		renderer:function(v){
			if(v=='manytoone'){
				return '多对一';
			}else if(v=='manytomany'){
				return '多对多';
				
			}else if(v=='onetomany'){
				return '一对多';
			}else if(v=='onetoone'){
				return '一对一';
			}
		}
	},sm];
	
	FrameModelRelationGridPanel.superclass.constructor
			.call(
					this,
					{
						layout : 'fit',
						region:'center',
						sm :  sm,
						trackMouseOver : true,
						frame : false,
						autoScroll : true,
						loadMask : true,
						clicksToEdit:1,
						viewConfig : {
							enableRowBody : true,
							showPreview : true,
							forceFit : true
						},
						
						tbar:[{
						  	text: '新增',
				            iconCls: 'x-btn-text-icon add',
				            scope: this,
							handler:function(){
								elseWindow.show();
								elseWindow.loadData(_grid.modelId);
							}
						 },'-',{
							  	text: '删除',
					            iconCls: 'x-btn-text-icon delete',
					            scope: this,
								handler:function(){
							 _grid.deleteInfo();
							 
						 }
							 }],
						bbar : new Ext.PagingToolbar( {
							pageSize : 10,
							store : this.store,
							displayInfo : true
						}),
						renderTo : this.renderToDiv
						
					});
	
};

Ext.extend(FrameModelRelationGridPanel, Ext.grid.GridPanel, {
	loadByModel:function(mid1){
		this.modelId=mid1;
		this.store.proxy= new Ext.data.HttpProxy( {
			url : 'frame/modelrelation/listByModel.jhtm?mid='+this.modelId
		});
		this.store.load({params:{start:0,limit:18}});
	}
	,
	deleteInfo : function() {
		var _grid=this;
		if (this.getSelected == null) {
			Ext.Msg.alert("删除模型关系", "请先选择一个模型关系！");
			return;
		} else {
			var s = Ext.Msg
					.confirm(
							"删除模型关系",
							"确定要删除选中的模型关系吗？",
							function(o) {
								if (o == 'yes') {
									var url = 'frame/modelrelation/deletes.jhtm?' + _grid.getSelectedIds();
									alert(url);
									Ext.Ajax.request( {
										url : url,
										success : success,
										failure : failure
									});
								} else {
									return;
								}
							});
		}
		function success(rep) {
			eval("var result=" + rep.responseText);
			var _success = result.success;
			var totalCount = result.totalCount;
			if (_success) {
				var lastO = _grid.store.lastOptions.params;
				var start = lastO.start;
				var totRecords = new Number(totalCount);
				if (start > 0 && start >= totRecords) {
					this.store.load( {
						params : {
							start : start - pageSize,
							limit : pageSize
						}
					});
				} else {
					_grid.store.load( {
						params : {
							start : start,
							limit : pageSize
						}
					});
				}
				Ext.Msg.alert("删除模型关系", "删除模型关系成功！");
			} else {
				Ext.Msg.alert("删除模型关系", "删除模型关系失败！");
			}

		}
		function failure(rep) {
			alert("删除失败！");
			alert(rep.responseText);
		}
	},
	getSelectedIds : function() {
		var ids = "";
		var selections = this.getSelections();
		var size = selections.length;
		for ( var i = 0; i < size; i++) {
			var r = selections[i];
			if (ids.length == 0) {
				ids = 'ids=' + r.get("relationId");
			} else {
				ids += "&ids=" + r.get("relationId");
			}
		}
		return ids;

	},
	getSelected : function() {
		var record = this.getSelectionModel().getSelected();
		if (record == null) {
			Ext.MessageBox.show( {
				title : 'Infomation',
				msg : "please select record",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		} else {
			return record;
		}
	},
	getSelections : function() {
		var records = this.getSelectionModel().getSelections();
		if (records.length < 1) {
			Ext.MessageBox.show( {
				title : '提示',
				msg : "请先选择一个模型关系！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		} else {
			return records;
		}
	},
	reloadStore:function(){
		var lastO= this.store.lastOptions.params;
		var start=lastO.start;
		this.store.load({params:{start:start, limit:pageSize}});
	}

});
var FrameModelRelationWindow = function() {
	var _win = this;
	this.modelId=0;
	this.gridPanel = new FrameModelRelationGridPanel();
	
	this.formPanel=new FrameModelRelationFormPanel();
	this.formPanel.gridPanel=this.gridPanel;
	relationForm=this.formPanel;
	this.gridPanel.relationForm=this.formPanel;
	this.gridPanel.elseWindow.relationForm=this.formPanel;
	var _formPanel=this.formPanel;
	this.gridPanel.getSelectionModel().on('rowselect',function(){
		//var id=this.getSelected().get('relationId');
		_formPanel.loadData(this.getSelected());
	});
	
	this.panel=new Ext.Panel({
		layout:'border',
		items:[new Ext.Panel({region:'center',layout:'fit',contentEl:'modelRelationGrid',items:[this.gridPanel]}),new Ext.Panel({region:'east',width:150,items:[this.formPanel]})]
	});
	FrameModelRelationWindow.superclass.constructor.call(this, {
		title : '模型关系窗口',
		width : 520,
		height : 350,
		resizable : true,
		plain : false,
		border : false,
		modal : true,
		autoScroll : true,
		layout : 'fit',
		closeAction : 'hide',
		items : [this.panel],
		buttons : [{
			text : '关闭',
			handler : function() {
				_win.hide();
			},
			tooltip : '关闭窗口'
		} ]
	});
}
Ext.extend(FrameModelRelationWindow, Ext.Window, {
	loadData : function(id) {
		this.gridPanel.loadByModel(id);
	},
	loadNoRelation:function(id){
		this.formPanel.loadNoRelation(id);
	},
	loadRelation:function(id){
		this.formPanel.loadData(id);
	},
	reloadStore:function(){
		this.gridPanel.reloadStore();
	}
});