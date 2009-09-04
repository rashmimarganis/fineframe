var pk="templateId";
var listUrl='cms/template/list.jhtm';
var deleteUrl='cms/template/delete.jhtm';
var saveUrl='cms/template/save.jhtm';
var loadUrl='cms/template/load.jhtm';
var modelTitle='模板';
var gridPanelDiv='cmsTemplateGrid';
var focusField='obj.name';
var fields=['templateId','name','fileName', 'type','content','templateSuitName'];

CmsTemplateGridPanel=function(){
	this.window=new CmsTemplateWindow();
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
        header: "模板名称",
        dataIndex: 'name',
        width: 100
     },{
        header: "模板文件",
        dataIndex: 'fileName',
        width: 100,
        sortable:false
     },{
	      header: "类型",
	      dataIndex: 'type',
	      width: 100,
	      renderer:function(v){
    	 	if(v=='c'){
    	 		return "普通模板";
    	 	}else if(v=='t'){
    	 		return "标签模板";
    	 	}
     	  }
      },{
          header: "模板方案",
          dataIndex: 'templateSuitName',
          width: 100
       }]);

    this.cm.defaultSortable = true;
    CmsTemplateGridPanel.superclass.constructor.call(
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
Ext.extend(CmsTemplateGridPanel, Ext.grid.GridPanel, {
	addInfo:function(){
		this.window.show();
		var form=this.window.formPanel.getForm();
		form.reset();
		form.findField("obj.name").focus(false,true);
		/*
		if(!editorInstance){
			var oFCKeditor = new FCKeditor( 'obj.content',810,350 ) ;   
	     	oFCKeditor.BasePath = "fckeditor/" ;   
	     	oFCKeditor.ToolbarSet = 'Default';  
	     	oFCKeditor.ReplaceTextarea() ;    
		}*/
		
	},
	loadInfo:function(){
		var select=this.getSelectionModel().getSelected();
		if(select){
			this.window.show();
			this.window.loadData(select.get(pk));
			/*
			if(!editorInstance){
				var oFCKeditor = new FCKeditor( 'obj.content',810,350 ) ;   
		     	oFCKeditor.BasePath = "fckeditor/" ;   
		     	oFCKeditor.ToolbarSet = 'Default';  
		     	oFCKeditor.ReplaceTextarea() ;    
			} 
			*/   
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


CmsTemplateFormPanel = function() {
	var _formPanel=this;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.templateId",
		hidden:true,
		hideLabel:true,
		readOnly:true,
        value:0
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "模板名称",
		allowBlank : false,
		anchor:'95%',
		name : "obj.name"
	};
	this.fileNameField = {
		xtype : 'textfield',
		fieldLabel : "模板文件",
		allowBlank : false,
		anchor:'95%',
		name : "obj.fileName"
	};
	this.oldFileNameField = {
		xtype : 'hidden',
		fieldLabel : "模板文件",
		allowBlank : false,
		hidden:true,
		hideLabel:true,
		name : "obj.oldFileName"
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
        anchor:'95%',
        typeAhead: true,
        editable:false,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择方案...',
        blankText:'请选择方案...',
        selectOnFocus:true,
        allowBlank:false,
        hiddenName: 'obj.suit.suitId'
    });
    
    this.contentField={
		xtype : 'textarea',
		fieldLabel : "模板内容",
		hideLabel:true,
		allowBlank : false,
		width:630,
		height:320,
		name : "obj.content"
	};

	var typeStore = new Ext.data.SimpleStore({
        fields: ['_value', '_name','_tip'],
        data : Ext.ux.TemplateType
    });
    this.typeField = new Ext.form.ComboBox({
        store: typeStore,
        displayField:'_name',
        valueField:'_value',
        fieldLabel : "模板类型",
        typeAhead: true,
        anchor:'95%',
        mode: 'local',
        editable:false,
        width : 120,
        triggerAction: 'all',
        emptyText:'请选择类型...',
        blankText : '请选择类型...',
        selectOnFocus:true,
        value:'c',
        hiddenName : 'obj.type'
    });
	
   
	CmsTemplateFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:230,
		labelWidth:80,
		layout:'column',
        height:200,
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : pk
		}, [
		    {name:'obj.templateId', mapping:pk},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.fileName',mapping:'fileName'}, 
		    {name:'obj.oldFileName',mapping:'oldFileName'}, 
		    {name:'obj.suit.suitId',mapping:'templateSuitId'}, 
		    {name:'obj.content',mapping:'content'}, 
		    {name:'obj.type',mapping:'type'}
		    ]
		),
		items :[{layout:'form',columnWidth:.5,
            items:[this.oldFileNameField,this.idField,this.nameField,this.typeField ]},{columnWidth:.5,
layout:'form',items:[this.fileNameField,this.suitField]},{columnWidth:1,layout:'form',items:[this.contentField]}] 
	});
};
Ext.extend(CmsTemplateFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = loadUrl+'?id=' + id;
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
				var msg="";
				if(o.id==-1){
					msg="相同文件名的模板文件已经存在，请重新填写！";
					_form.findField("obj.fileName").focus(false,true);
				}
				Ext.MessageBox.show( {
					title : '出现错误',
					msg : msg,
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	}
});


CmsTemplateWindow=function(){
	this.gridPanel;
	this.formPanel=new CmsTemplateFormPanel();
	this.form=this.formPanel.getForm();
	var _form=this.form;
	var _formPanel=this.formPanel;
	var _win=this;
	CmsTemplateWindow.superclass.constructor.call(this, {
		title : modelTitle+'信息',
		width : 660,
		height : 480,
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
			 	//Ext.get('content').dom.value=editorInstance.GetXHTML( true );
			     //获取fckeditor内容赋给textarea  
				if (_form.isValid()) {
					_form.submit( {
						waitMsg : '正在保存数据...',
						url : saveUrl,
						failure : function(form, action) {
							var json = action.response.responseText;
							var o = eval("(" + json + ")");
							var msg="";
							if(o.id==-1){
								msg="相同文件名的模板文件已经存在，请重新填写！";
								_form.findField("obj.fileName").focus(false,true);
							}
							Ext.MessageBox.show( {
								title : '出现错误',
								msg : msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
						},
						success : function(form1, action) {
							Ext.Msg.alert("保存信息","保存"+modelTitle+"信息成功！");
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

Ext.extend(CmsTemplateWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});




var CmsTemplateApp= function(){
	return {
		init:function(){
			var grid=new CmsTemplateGridPanel();
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'cmsTemplateCenter',
				contentEl:'cmsTemplateGrid',
				items:[grid]
		    });
			FineCmsMain.addFunctionPanel(center);
	     	
	     	
		}
	};
}();
CmsTemplateApp.init();