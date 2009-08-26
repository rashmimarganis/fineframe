var pageSize=18;

var FrameModelRelationGridPanel = function() {
	var _grid = this;
	this.modelId=0;
	this.primaryKey='relationId';
	this.renderToDiv='modelRelationGrid';
    var type = new Ext.form.ComboBox({
    	 typeAhead: true,
         triggerAction: 'all',
        transform:'relation',
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
		editor:type
	},sm];
	
	FrameModelRelationGridPanel.superclass.constructor
			.call(
					this,
					{
						layout : 'fit',
						sm : sm,
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
						  	text: '添加',
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

Ext.extend(FrameModelRelationGridPanel, Ext.grid.EditorGridPanel, {
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
	this.gridPanel = new FrameModelRelationGridPanel();
	FrameModelRelationWindow.superclass.constructor.call(this, {
		title : '模型关系窗口',
		width : 420,
		height : 280,
		resizable : true,
		plain : false,
		border : false,
		modal : true,
		autoScroll : true,
		layout : 'fit',
		closeAction : 'hide',
		items : [this.gridPanel],
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
	}
});