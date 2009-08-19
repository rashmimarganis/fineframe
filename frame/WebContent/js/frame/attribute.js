var FrameAttributeFormPanel = function() {
	this.idField = {
		xtype : 'hidden',
		fieldLabel : "id",
		name : "obj.attributeId",
		value : '0'
	};
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "英文名称",
		allowBlank : false,
		name : "obj.name"
	};
	this.labelField = {
		xtype : 'textfield',
		fieldLabel : "中文名称",
		allowBlank : false,
		name : "obj.label"
	};

	this.lengthField = {
		xtype : 'numberfield',
		fieldLabel : "长度",
		allowBlank : false,
		name : "obj.length"
	};

	var javaClassStore = new Ext.data.SimpleStore({
        fields: ['javaClassId', 'javaClassName','javaClassTip'],
        data : Ext.ux.JavaClassType
    });
    this.javaClassField = new Ext.form.ComboBox({
        store: javaClassStore,
        displayField:'javaClassName',
        valueField:'javaClassId',
        fieldLabel : "类型",
        typeAhead: true,
        mode: 'local',
        width : 200,
        triggerAction: 'all',
        emptyText:'请选择类型...',
        blankText : '请选择类型...',
        allowBlank : false,
        selectOnFocus:true,
        hiddenName : 'obj.javaClass'
    });
	
	

	this.isKeyField = new Ext.form.Checkbox({
		fieldLabel : "主键",
		autoShow:true,
		hidden:false,
		checked:true, 
		name : "obj.isKey"
	});

	this.requiredField = new Ext.form.Checkbox({
		fieldLabel : "必填",
		checked:true,
		autoShow:true,
		name : "obj.required"
	});
	var modelRecordDef = Ext.data.Record.create( [ {
		name : 'modelId'
	}, {
		name : 'label'
	} ]);

	var modelStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'frame/model/list.jhtm'
		}),
		autoLoad:true,
		reader : new Ext.data.JsonReader( {
			root : 'objs',
			totalProperty : 'totalCount',
			id : 'modelId'
		}, [{
			name : 'modelId',
			mapping : 'modelId'
		}, {
			name : 'label',
			mapping : 'label'
		}, {
			name : 'name',
			mapping : 'name'
		}, {
			name : 'projectName',
			mapping : 'projectName'
		} ], modelRecordDef)
	});
	var controlRecordDef = Ext.data.Record.create( [ {
		name : 'controlId'
	}, {
		name : 'label'
	} ]);
	var controlStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'frame/control/list.jhtm'
		}),
		autoLoad:true,
		reader : new Ext.data.JsonReader( {
			root : 'objs',
			totalProperty : 'totalCount',
			id : 'controlId'
		}, [{
			name : 'controlId',
			mapping : 'controlId'
		}, {
			name : 'label',
			mapping : 'label'
		}, {
			name : 'name',
			mapping : 'name'
		} ], controlRecordDef)
	});
	// Custom rendering Template
	var modelTpl = new Ext.XTemplate('<tpl for="."><div class="search-item">',
			'<span>[{projectName}]','{label}', '</span></div></tpl>');
	var controlTpl = new Ext.XTemplate('<tpl for="."><div class="search-item">',
			'<span>{label}', '</span></div></tpl>');

	this.modelField = new Ext.form.ComboBox( {
		store : modelStore,
		fieldLabel : '所属模型',
		displayField : 'label',
		valueField:'modelId',
		typeAhead : true,
		loadingText : '正在加载数据...',
		width : 200,
		pageSize : 10,
		hideTrigger : false,
		tpl : modelTpl,
		itemSelector : 'div.search-item',
		selectOnFocus : true,
		allowBlank : false,
		editable:false,
		triggerAction : 'all',
		emptyText : '请选择模型...',
		blankText : '请选择模型...',
		hiddenName : 'obj.model.modelId'
	});

	this.controlField = new Ext.form.ComboBox( {
		store : controlStore,
		fieldLabel : '选择控件',
		displayField : 'label',
		valueField:'controlId',
		typeAhead : true,
		loadingText : '正在加载数据...',
		width : 200,
		pageSize : 10,
		hideTrigger : false,
		tpl : controlTpl,
		itemSelector : 'div.search-item',
		selectOnFocus : true,
		allowBlank : false,
		editable:false,
		triggerAction : 'all',
		emptyText : '请选择控件...',
		blankText : '请选择控件...',
		hiddenName : 'obj.control.controlId'
	});
	
	FrameAttributeFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		reader : new Ext.data.JsonReader( {
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'attributeId'
		}, [ {
			name : 'obj.attributeId',
			mapping : 'attributeId'
		}, {
			name : 'obj.name',
			mapping : 'name'
		}, {
			name : 'obj.label',
			mapping : 'label'
		}, {
			name : 'obj.javaClass',
			mapping : 'javaClass'
		}, {
			name : 'obj.length',
			mapping : 'length'
		}, {
			name : 'obj.isKey',
			mapping : 'isKey'
		}, {
			name : 'obj.required',
			mapping : 'required'
		}, {
			name : 'obj.model.modelId',
			mapping : 'modelId'
		}, {
			name : 'obj.control.controlId',
			mapping : 'controlId'
		} ]),
		items : [ this.idField, this.nameField, this.labelField,
				this.requiredField, this.isKeyField, this.javaClassField,
				this.lengthField, this.modelField,this.controlField ]
	});
};
Ext.extend(FrameAttributeFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'frame/attribute/load.jhtm?id=' + id;
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

var FrameAttributeWindow = function() {
	var _win = this;
	this.formPanel = new FrameAttributeFormPanel();
	var _form = this.formPanel.getForm();
	FrameAttributeWindow.superclass.constructor.call(this, {
		title : '属性信息',
		width : 420,
		height : 280,
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
						url : 'frame/attribute/save.jhtm',
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
							FrameAttributeApp.reload();

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
		} ]
	});
}
Ext.extend(FrameAttributeWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});

var FrameAttributeApp = function() {
	var store;
	var xg = Ext.grid;
	var grid;
	var sm;
	var pageSize = 18;
	var infoDlg;
	var saveBtn;
	var form;
	return {
		init : function() {
			FrameAttributeApp.initStore();
			FrameAttributeApp.initGridPanel();
			FrameAttributeApp.initLayout();
		},
		initStore : function() {
			store = new Ext.data.Store( {
				proxy : new Ext.data.HttpProxy( {
					url : 'frame/attribute/list.jhtm'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'objs',
					totalProperty : 'totalCount',
					id : 'attributeId',
					fields : [ 'attributeId', 'name', 'label', 'modelId',
							'modelLabel', 'isKey', 'required', 'javaClass','controlLabel','controlId' ]
				}),
				remoteSort : true
			});
			store.setDefaultSort('attributeId', 'desc');
			store.on('load', function(s, r, o) {
				if (s.getTotalCount() > 0) {
					sm.selectFirstRow();
				}
			});
		},
		initGridPanel : function() {
			
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel( [ sm, {
				id : 'attributeId',
				header : "ID",
				dataIndex : 'attributeId',
				width : 40
			}, {
				id : 'name',
				header : "英文名称",
				dataIndex : 'name',
				width : 100
			},
			{
				id : 'label',
				header : "中文名称",
				dataIndex : 'label',
				width : 100
			}, {
				id : 'javaClass',
				header : "类型",
				dataIndex : 'javaClass',
				width : 100
			}, {
				id : 'isKey',
				header : "主键",
				dataIndex : 'isKey',
				width : 100,
				renderer:function(v){
					if(v){
						return '是';
					}else{
						return '否';
					}
				}
			}, {
				id : 'required',
				header : "必填",
				dataIndex : 'required',
				width : 100,
				renderer:function(v){
					if(v){
						return '是';
					}else{
						return '否';
					}
				}
			}, {
				header : "所属模型",
				dataIndex : 'modelLabel',
				width : 150,
				sortable : false
			},{
				header : "所属控件",
				dataIndex : 'controlLabel',
				width : 150,
				sortable : false
			},  {
				header : "模型ID",
				dataIndex : 'modelId',
				width : 150,
				sortable : false,
				hidden : true
			} ]);

			cm.defaultSortable = true;

			var mainHeight = FineCmsMain.getMainPanelHeight() - 1;
			grid = new Ext.grid.GridPanel( {
				store : store,
				cm : cm,
				sm : sm,
				height : mainHeight,
				trackMouseOver : true,
				frame : false,
				autoScroll : true,
				loadMask : true,
				tbar : [ {
					text : '添加属性',
					iconCls : 'x-btn-text-icon add',
					scope : this,
					handler : FrameAttributeApp.addInfo
				}, '-', {
					text : '修改属性',
					iconCls : 'x-btn-text-icon edit',
					scope : this,
					handler : FrameAttributeApp.loadInfo
				}, '-', {
					text : '删除属性',
					iconCls : 'x-btn-text-icon delete',
					scope : this,
					handler : FrameAttributeApp.deleteInfo
				} ],
				bbar : new Ext.PagingToolbar( {
					pageSize : pageSize,
					store : store,
					displayInfo : true
				}),
				viewConfig : {
					enableRowBody : true,
					showPreview : true,
					forceFit : true
				},
				renderTo : 'attributeGrid'
			});
			grid.on('rowdblclick', FrameAttributeApp.loadInfo);
			grid.render();
			store.load( {
				params : {
					start : 0,
					limit : pageSize
				}
			});
		},

		addInfo : function() {
			FrameAttributeApp.showInfoDlg();
			//form.reset();

		},
		initLayout : function() {
			var mainHeight = FineCmsMain.getMainPanelHeight() - 1;
			var center = new Ext.Panel( {
				collapsible : false,
				layout : 'fit',
				el : 'attributeCenter',
				contentEl : 'attributeGrid',
				items : [ grid ]
			});
			FineCmsMain.addFunctionPanel(center);
		},
		loadInfo : function() {
			if (sm.getSelected() == null) {
				Ext.Msg.alert("编辑属性", "请先选择一个属性！");
				return;
			} else {
				FrameAttributeApp.showInfoDlg();
				var select = sm.getSelected();
				var id = select.get('attributeId');
				infoDlg.loadData(id);
			}

		},
		deleteInfo : function() {
			if (sm.getSelected == null) {
				Ext.Msg.alert("删除属性", "请先选择一个属性！");
				return;
			} else {
				var s = Ext.Msg
						.confirm(
								"删除属性",
								"确定要删除选中的属性吗？",
								function(o) {
									if (o == 'yes') {
										var url = 'frame/attribute/deletes.jhtm?' + FrameAttributeApp
												.getSelectedIds();
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
					var lastO = store.lastOptions.params;
					var start = lastO.start;
					var totRecords = new Number(totalCount);
					if (start > 0 && start >= totRecords) {
						store.load( {
							params : {
								start : start - pageSize,
								limit : pageSize
							}
						});
					} else {
						store.load( {
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
			var selections = sm.getSelections();
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
		showInfoDlg : function() {
			if (!infoDlg) {
				infoDlg = new FrameAttributeWindow();
			}
			infoDlg.show();
			// form.getForm().findField("obj.name").focus(true);
	},
	reload : function() {
		var lastO = store.lastOptions.params;
		var start = lastO.start;
		store.load( {
			params : {
				start : start,
				limit : pageSize
			}
		});
	}
	};
}();
FrameAttributeApp.init();