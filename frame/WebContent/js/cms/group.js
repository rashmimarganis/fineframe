var westPanel;
var centerPanel;

var GroupFormPanel = function() {
	var _formPanel=this;
	this.level=1;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.groupId",
		readOnly:true
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "名称",
		allowBlank : false,
		name : "obj.groupName"
	};
	
    
	GroupFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:230,
		labelWidth:60,
        height:200,
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'groupId'
		}, [
		    {name:'obj.groupId', mapping:'groupId'},
		    {name:'obj.groupName',mapping:'groupName'}
		    ]
		),
		items : [this.idField,this.nameField]
	});
};
Ext.extend(GroupFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'cms/group/load.jhtm?id=' + id;
		var _form=this.getForm();
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			success:function(form, action){
				var json = action.response.responseText;
				var o = eval("(" + json + ")");
			},
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
	save:function(){
		var _form=this.getForm();
    	if (_form.isValid()) {
			_form.submit( {
				waitMsg : '正在保存数据...',
				url : 'cms/group/save.jhtm',
				success : function(form1, action) {
					var json = action.response.responseText;
					var o = eval("(" + json + ")");
					if(o.success){
						Ext.Msg.alert("保存会员组","会员组保存成功");
						_form.findField("obj.groupId").setValue(o.id);
					}else{
						Ext.Msg.alert("保存会员组","会员组保存失败！");
					}

				},
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
		}else{
			Ext.Masg.alert('验证信息','请把会员组信息填写正确！');
		}
	}
});

GroupGridPanel=function(){
	this.orgId=0;
	this.window=new GroupWindow();
	this.window.gridPanel=this;
	var _win=this.window;
	var _grid=this;
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'cms/group/list.jhtm'
        }),
        reader: new Ext.data.JsonReader({
            root: 'objs',
            totalProperty: 'totalCount',
            id: 'groupId',
            fields: [
                'groupId','groupName'
            ]
        }
        ),
        remoteSort: true
	});
    this.store.setDefaultSort('groupId', 'asc');
	this.store.on('load',function(s,r,o){
		if(s.getTotalCount()>0){
			sm.selectFirstRow();
		}
	});
	this.store.load({params:{start:0,limit:18}});
	
	this.cm = new Ext.grid.ColumnModel([sm,{
        id: 'groupId',
        header: "ID",
        dataIndex: 'groupId',
        width: 20
     },{
        id: 'groupName', 
        header: "名称",
        dataIndex: 'groupName',
        width: 100
     }]);

    this.cm.defaultSortable = true;
    GroupGridPanel.superclass.constructor.call(
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
					store : _grid.store,
					displayInfo : true
				}),
				tbar : [
					
					{
							text : '添加会员组',
							iconCls : 'x-btn-text-icon add',
							scope : this,
							handler : _grid.addInfo
						}, '-', {
							text : '修改会员组',
							iconCls : 'x-btn-text-icon edit',
							scope : this,
							handler : _grid.loadInfo
						}, '-', {
							text : '删除会员组',
							iconCls : 'x-btn-text-icon delete',
							scope : this,
							handler : _grid.deleteInfo
						}
				],
				renderTo : 'groupGridPanel'
			});
    
};
Ext.extend(GroupGridPanel, Ext.grid.GridPanel, {
	addInfo:function(){
		this.window.show();
		var form=this.window.formPanel.getForm();
		form.findField("obj.groupId").setValue(0);
		form.findField("obj.groupName").setValue("");
		form.findField("obj.groupName").focus(false,true);
		
	},
	loadInfo:function(){
		var select=this.getSelectionModel().getSelected();
		if(select){
			this.window.show();
			this.window.loadData(select.get("groupId"));
		}else{
			Ext.Msg.alert("显示会员组","请选择一个会员组！");
		}
	},
	deleteInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("删除会员组",'请选择会员组！');
			return;
		}
		
		if(!confirm("确定要删除该会员组吗？")){
			return;
		}
		var url='cms/group/delete.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("删除会员组","删除成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("删除会员组",rep.repsonseText);
		}
	},
	loadData:function(orgId){
		this.store.proxy=new Ext.data.HttpProxy({
            url: 'cms/group/list.jhtm'
        });
		this.store.load({params:{start:0,limit:18}});
	}
	,
	reloadData:function(orgId){
		this.store.reload();
	},
	getSelectedIds:function(){
		var ids="";
		var selections=this.getSelectionModel().getSelections();
		var size=selections.length;
		for(var i=0;i<size;i++){
				var r=selections[i];
				if(ids.length==0){
					ids='ids='+r.get("groupId");
				}else{
					ids+="&ids="+r.get("groupId");
				}
		}
		return ids;
		
	}
});

GroupWindow=function(){
	this.gridPanel;
	this.formPanel=new GroupFormPanel();
	this.form=this.formPanel.getForm();
	var _form=this.form;
	var _formPanel=this.formPanel;
	var _win=this;
	var _gridPanel=this.gridPanel;
	GroupWindow.superclass.constructor.call(this, {
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
						url : 'cms/group/save.jhtm',
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
							var json = action.response.responseText;
							var o = eval("(" + json + ")");
							if(o.success){
								Ext.Msg.alert("保存会员组","会员组保存成功");
								form1.findField("obj.groupId").setValue(o.id);
								_win.gridPanel.reloadData();
							}else{
								Ext.Msg.alert("保存会员组","会员组保存失败！");
							}

							
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
};

Ext.extend(GroupWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});


var GroupApp= function(){
	
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var gridPanel=new GroupGridPanel();
	return {
		init:function(){
			GroupApp.initLayout();
			
		},
		initLayout:function(){
			centerPanel=new Ext.Panel(
 	     	{
 				  split:true,
 				  layout:'fit',
 				  collapsible:false,
                   el:'groupPanel',
                   contentEl:'groupGridPanel',
                   border:true,
                   width:300,
                   split:true,
                   items:[gridPanel]
              	}
 		     );
		     var panel=new Ext.Panel({
		    	 layout:'fit',
		    	 items:[
			              centerPanel
				           ]
		     });
		     FineCmsMain.addFunctionPanel(panel);
    	}
		
		
	};
}();
GroupApp.init();