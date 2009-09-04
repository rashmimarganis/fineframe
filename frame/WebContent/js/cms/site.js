var pk="siteId";
var listUrl='cms/site/list.jhtm';
var deleteUrl='cms/site/delete.jhtm';
var saveUrl='cms/site/save.jhtm';
var loadUrl='cms/site/load.jhtm';
var modelTitle='站点';
var gridPanelDiv='cmsSiteGrid';
var focusField='obj.name';
var fields=['siteId','name','watermark', 'title','packageName','htmlPath','siteUrl','closed','closeReason','templateSuitName'];

CmsSiteGridPanel=function(){
	this.window=new CmsSiteWindow();
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
        header: "站点名称",
        dataIndex: 'name',
        width: 100
     },{
        header: "站点标题",
        dataIndex: 'title',
        width: 100,
        sortable:false
     },{
         header: "文件夹",
         dataIndex: 'packageName',
         width: 100
	  },{
	      header: "文件生成路径",
	      dataIndex: 'htmlPath',
	      width: 100
      },{
    	  header: "站点URL",
    	  dataIndex: 'siteUrl',
    	  width: 100
      },{
        header: "图片水印",
        dataIndex: 'watermark',
        width: 100
     },{
         header: "关闭状态",
         dataIndex: 'closed',
         width: 100
      },{
          header: "模板方案",
          dataIndex: 'templateSuitName',
          width: 100
       }]);

    this.cm.defaultSortable = true;
    CmsSiteGridPanel.superclass.constructor.call(
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
Ext.extend(CmsSiteGridPanel, Ext.grid.GridPanel, {
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
			Ext.Msg.alert("删除"+modelTitle,"请选择"+modelTitle+"！");
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


CmsSiteFormPanel = function() {
	var _formPanel=this;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.siteId",
		readOnly:true,
        value:0
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "站点名称",
		allowBlank : false,
		name : "obj.name"
	};
	this.titleField = {
		xtype : 'textfield',
		fieldLabel : "站点标题",
		allowBlank : false,
		name : "obj.title"
	};
	this.siteUrlField = {
		xtype : 'textfield',
		fieldLabel : "站点URL",
		allowBlank : false,
		width : 200,
		name : "obj.siteUrl"
	};
	this.packageNameField = {
		xtype : 'textfield',
		fieldLabel : "文件夹",
		allowBlank : false,
		name : "obj.packageName"
	};
	this.htmlPathField = {
		xtype : 'textfield',
		fieldLabel : "生成文件路径",
		width : 200,
		name : "obj.htmlPath"
	};
	
	
	this.suitStore=new Ext.data.Store({    
        proxy: new Ext.data.HttpProxy({url: 'cms/suit/all.jhtm'}),    
        reader: new Ext.data.JsonReader({    
            id:"suitId",totalProperty:'totalCount',root:'objs' 
        },['suitId','name']),    
        remoteSort: true,
        autoLoad:true
    }); 

    this.suitField = new Ext.form.ComboBox({
        store: this.suitStore,
        fieldLabel: '模板方案',
        displayField:'name',
        valueField:'suitId',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择方案...',
        blankText:'请选择方案...',
        selectOnFocus:true,
        allowBlank:false,
        hiddenName: 'obj.templateSuit.suitId'
    });
    this.watermarkField = {
		xtype : 'checkbox',
		fieldLabel : "启用水印",
		boxLabel:"启用",
		name : "obj.watermark"
	};
    
	this.closedField = {
		xtype : 'checkbox',
		fieldLabel : "网站状态",
		boxLabel:"关闭",
		name : "obj.closed"
	};
	this.watermarkPicField = {
		xtype : 'textfield',
		fieldLabel : "水印图片",
		name : "obj.watermarkPic"
	};
	var watermarkPositionStore = new Ext.data.SimpleStore({
        fields: ['_value', '_name','_tip'],
        data : Ext.ux.WatermarkPositionType
    });
    this.watermarkPositionField = new Ext.form.ComboBox({
        store: watermarkPositionStore,
        displayField:'_name',
        valueField:'_value',
        fieldLabel : "水印位置",
        typeAhead: true,
        mode: 'local',
        width : 120,
        triggerAction: 'all',
        emptyText:'请选择位置...',
        blankText : '请选择位置...',
        selectOnFocus:true,
        value:'lt',
        hiddenName : 'obj.watermarkPosition'
    });
	
    this.closeReasonField = {
		xtype : 'textarea',
		fieldLabel:'关闭原因',
		name : "obj.closeReason",
		width:280,
		height:100
	};
	CmsSiteFormPanel.superclass.constructor.call(this, {
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
		    {name:'obj.siteId', mapping:pk},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.title',mapping:'title'}, 
		    {name:'obj.watermark',mapping:'watermark'}, 
		    {name:'obj.htmlPath',mapping:'htmlPath'},
		    {name:'obj.packageName',mapping:'packageName'},
		    {name:'obj.siteUrl',mapping:'siteUrl'},
		    {name:'obj.closed',mapping:'closed'},
		    {name:'obj.closeReason',mapping:'closeReason'},
		    {name:'obj.watermark',mapping:'watermark'},
		    {name:'obj.watermarkPic',mapping:'watermarkPic'},
		    {name:'obj.watermarkPosition',mapping:'watermarkPosition'}
		    ]
		),
		items : [this.idField,this.nameField,this.titleField,this.htmlPathField,this.siteUrlField,this.suitField,this.watermarkPicField,this.watermarkPositionField,this.watermarkField,this.closedField,this.closeReasonField ]
	});
};
Ext.extend(CmsSiteFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = loadUrl+'?id=' + id;
		var _form=this.getForm();
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			success:function(form, action){
				var json = action.response.responseText;
				var o = eval("(" + json + ")");
				_form.findField("obj.templateSuit.suitId").setRawValue(o.data[0].templateSuitName);
				_form.findField("obj.templateSuit.suitId").setValue(o.data[0].templateSuitId);
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
	}
});


CmsSiteWindow=function(){
	this.gridPanel;
	this.formPanel=new CmsSiteFormPanel();
	this.form=this.formPanel.getForm();
	var _form=this.form;
	var _formPanel=this.formPanel;
	var _win=this;
	CmsSiteWindow.superclass.constructor.call(this, {
		title : modelTitle+'信息',
		width : 450,
		height : 460,
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
							Ext.MessageBox.show( {
								title : '出现错误',
								msg : o.message,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
						},
						success : function(form1, action) {
							_win.gridPanel.reloadData();

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

Ext.extend(CmsSiteWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});




var CmsSiteApp= function(){
	return {
		init:function(){
			var grid=new CmsSiteGridPanel();
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'cmsSiteCenter',
				contentEl:'cmsSiteGrid',
				items:[grid]
		    });
	     	FineCmsMain.addFunctionPanel(center);
		}
	};
}();
CmsSiteApp.init();