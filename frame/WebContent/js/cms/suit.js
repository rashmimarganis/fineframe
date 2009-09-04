var pk="suitId";
var listUrl='cms/suit/list.jhtm';
var deleteUrl='cms/suit/delete.jhtm';
var saveUrl='cms/suit/save.jhtm';
var loadUrl='cms/suit/load.jhtm';
var modelTitle='方案';
var gridPanelDiv='cmsSuitGrid';
var focusField='obj.name';
var fields=['suitId','name', 'title','packageName'];

CmsSuitGridPanel=function(){
	this.window=new CmsSuitWindow();
	this.window.gridPanel=this;
	var _win=this.window;
	var _grid=this;
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
    CmsSuitGridPanel.superclass.constructor.call(
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
					pageSize : 18,
					store : _grid.store,
					displayInfo : true
				}),
				tbar : [
					
					{
							text : '新增'+modelTitle,
							iconCls : 'x-btn-text-icon add',
							scope : this,
							handler : _grid.addInfo
						}, '-', {
							text : '修改'+modelTitle,
							iconCls : 'x-btn-text-icon edit',
							scope : this,
							handler : _grid.loadInfo
						}, '-', {
							text : '删除'+modelTitle,
							iconCls : 'x-btn-text-icon delete',
							scope : this,
							handler : _grid.deleteInfo
						}
				],
				renderTo : gridPanelDiv				
			});
    
};
Ext.extend(CmsSuitGridPanel, Ext.grid.GridPanel, {
	addInfo:function(){
		this.window.show();
		var form=this.window.formPanel.getForm();
		form.reset();
		form.findField("obj.name").focus(false,true);
		
	},
	loadInfo:function(){
		var select=this.getSelectionModel().getSelected();
		if(select){
			this.window.show();
			this.window.loadData(select.get(pk));
		}else{
			Ext.Msg.alert("显示"+modelTitle,"请选择一个"+modelTitle+"！");
		}
	},
	deleteInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("删除"+modelTitle,'请选择"+modelTitle+"！');
			return;
		}
		
		if(!confirm("确定要删除选中的"+modelTitle+"吗？")){
			return;
		}
		var url=deleteUrl+'?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("删除"+modelTitle+"","删除"+modelTitle+"成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("删除"+modelTitle,rep.repsonseText);
		}
	},
	reloadData:function(){
		this.store.reload();
	},
	getSelectedIds:function(){
		var ids="";
		var selections=this.getSelectionModel().getSelections();
		var size=selections.length;
		for(var i=0;i<size;i++){
				var r=selections[i];
				if(ids.length==0){
					ids='ids='+r.get(pk);
				}else{
					ids+="&ids="+r.get(pk);
				}
		}
		return ids;
		
	}
});


CmsSuitFormPanel = function() {
	var _formPanel=this;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.suitId",
		readOnly:true,
        value:0
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : modelTitle+"名称",
		allowBlank : false,
		name : "obj.name"
	};
	this.packageNameField = {
		xtype : 'textfield',
		fieldLabel : modelTitle+"目录",
		allowBlank : false,
		name : "obj.packageName"
	};
	CmsSuitFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:230,
		labelWidth:80,
        height:200,
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : pk
		}, [
		    {name:'obj.suitId', mapping:pk},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.packageName',mapping:'packageName'}
		    ]
		),
		items : [this.idField,this.nameField,this.packageNameField]
	});
};
Ext.extend(CmsSuitFormPanel, Ext.form.FormPanel, {
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


CmsSuitWindow=function(){
	this.gridPanel;
	this.formPanel=new CmsSuitFormPanel();
	this.form=this.formPanel.getForm();
	var _form=this.form;
	var _formPanel=this.formPanel;
	var _win=this;
	CmsSuitWindow.superclass.constructor.call(this, {
		title : modelTitle+'信息',
		width : 260,
		height : 160,
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
						url : saveUrl,
						failure : function(form, action) {
							var json = action.response.responseText;
							var o = eval("(" + json + ")");
							if(o.id==-1){
								var msg="方案目录已经存在,请重新选择一个！";
								Ext.MessageBox.show( {
									title : '出现错误',
									msg : msg,
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
								_form.findField("obj.packageName").focus(false,true);
							}
						},
						success : function(form1, action) {
							var json = action.response.responseText;
							var o = eval("(" + json + ")");
							if(o.success){
								Ext.Msg.alert("保存"+modelTitle,"保存"+modelTitle+"成功！");
								_win.gridPanel.reloadData();
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

Ext.extend(CmsSuitWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});




var CmsSuitApp= function(){
	return {
		init:function(){
			var grid=new CmsSuitGridPanel();
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'cmsSuitCenter',
				contentEl:'cmsSuitGrid',
				items:[grid]
		    });
	     	FineCmsMain.addFunctionPanel(center);
		}
	};
}();
CmsSuitApp.init();