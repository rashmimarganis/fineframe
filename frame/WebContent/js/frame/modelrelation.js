var pageSize=18;
var FrameModelRelationFormPanel=function(){
	this.mid=0;
	this.idField = {
		xtype : 'hidden',
		fieldLabel : "id",
		name : "obj.relationId",
		value : '0'
	};

	var modelRecordDef = Ext.data.Record.create( [ {
		name : 'modelId'
	}, {
		name : 'label'
	} ]);
	this.modelStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'frame/modelrelation/else.jhtm?mid='+this.mid
		}),
		autoLoad:true,
		reader : new Ext.data.JsonReader( {
			root : 'objs',
			totalProperty : 'totalCount',
			id : 'modelId'
		}, [ {
			name : 'label',
			mapping : 'label'
		}, {
			name : 'name',
			mapping : 'name'
		} ], modelRecordDef)
	});
    this.modelField = new Ext.form.ComboBox({
        store: this.modelStore,
        displayField:'label',
        valueField:'modelId',
        fieldLabel : "关联模型",
        typeAhead: true,
        mode: 'local',
        width : 200,
        triggerAction: 'all',
        emptyText:'请选择模型...',
        blankText : '请选择模型...',
        allowBlank : false,
        selectOnFocus:true,
        hiddenName : 'obj.relationModel.label'
    });
	
    var relationStore = new Ext.data.SimpleStore({
        fields: ['value', 'label','tip'],
        data : Ext.ux.ModelRelationType
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
			name : 'obj.relationModel.label',
			mapping : 'relationModelLabel'
		}, {
			name : 'obj.relationModel.name',
			mapping : 'relationModelName'
		}, {
			name : 'obj.relation',
			mapping : 'relation'
		} ]),
		items : [ this.idField, this.modelField,this.relationField ]
	});
};
Ext.extend(FrameModelRelationFormPanel, Ext.form.FormPanel, {
		loadNoRelation:function(mid1){
			this.modelStore.proxy=new Ext.data.HttpProxy( {
					url : 'frame/modelrelation/else.jhtm?mid='+mid1
				});
			this.modelStore.reload();
		},
		loadData:function(id){
			var url = 'frame/modelrelation/load.jhtm?id=' + id;
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
var FrameModelRelationGridPanel = function() {
	var _grid = this;
	this.modelId=0;
	this.primaryKey='relationId';
	this.renderToDiv='modelRelationGrid';
    var type = new Ext.form.ComboBox({
    	 typeAhead: true,
         triggerAction: 'all',
        lazyRender:true,
        listClass: 'x-combo-list-small'
    });
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
	this.sm = new Ext.grid.CheckboxSelectionModel( {
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
		dataIndex : 'relation'
	},this.sm];
	
	FrameModelRelationGridPanel.superclass.constructor
			.call(
					this,
					{
						layout : 'fit',
						region:'center',
						sm : this.sm,
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
							handler:function(){}
						 },'-',{
						  	text: '保存',
				            iconCls: 'x-btn-text-icon save',
				            scope: this,
							handler:function(){}
						 },'-',{
							  	text: '删除',
					            iconCls: 'x-btn-text-icon delete',
					            scope: this,
								handler:function(){}
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
			Ext.Msg.alert("删除属性", "请先选择一个属性！");
			return;
		} else {
			var s = Ext.Msg
					.confirm(
							"删除属性",
							"确定要删除选中的属性吗？",
							function(o) {
								if (o == 'yes') {
									var url = 'frame/attribute/deletes.jhtm?' + _grid.getSelectedIds();
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
				Ext.Msg.alert("删除属性", "删除属性成功！");
			} else {
				Ext.Msg.alert("删除属性", "删除属性失败！");
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
				ids = 'ids=' + r.get("attributeId");
			} else {
				ids += "&ids=" + r.get("attributeId");
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
				msg : "请先选择一个属性！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
		} else {
			return records;
		}
	}

});
var FrameModelRelationWindow = function() {
	var _win = this;
	this.modelId=0;
	this.gridPanel = new FrameModelRelationGridPanel();
	this.formPanel=new FrameModelRelationFormPanel();
	var _formPanel=this.formPanel;
	this.gridPanel.getSelectionModel().on('rowselect',function(){
		var id=this.getSelected().get('relationId');
		_formPanel.loadData(id);
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
	}
});