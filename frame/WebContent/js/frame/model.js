var FrameModelFormPanel = function() {
	this.idField = {
		xtype : 'hidden',
		fieldLabel : "id",
		name : "obj.modelId",
		value:'0'
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
	
	var RecordDef = Ext.data.Record.create([    
            {name: 'projectId'},{name: 'name'}                   
    ]); 
    this.projectStore=new Ext.data.Store({    
        //设定读取的地址 
        proxy: new Ext.data.HttpProxy({url: 'frame/project/all.jhtm'}),    
        //设定读取的格式    
        reader: new Ext.data.JsonReader({    
            id:"projectId",totalProperty:'totalCount',root:'objs' 
        }, RecordDef),    
        remoteSort: true,
        autoLoad:true
    }); 

    this.projectField = new Ext.form.ComboBox({
        store: this.projectStore,
        fieldLabel: '所属项目',
        displayField:'name',
        valueField:'projectId',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择项目...',
        blankText:'请选择项目...',
        selectOnFocus:true,
        allowBlank:false,
        hiddenName: 'obj.project.projectId'
    });
	
	FrameModelFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		reader : new Ext.data.JsonReader( {
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'modelId'
		}, [
		    {name:'obj.modelId', mapping:'modelId'},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.label',mapping:'label'},
		    {name:'obj.project.projectId',mapping:'projectId'}
		    ]
		),
		items : [this.idField, this.nameField, this.labelField,this.projectField]
	});
};
Ext.extend(FrameModelFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'frame/model/load.jhtm?id=' + id;
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

var FrameModelWindow = function() {
	var _win = this;
	this.formPanel = new FrameModelFormPanel();
	var _form = this.formPanel.getForm();
	FrameModelWindow.superclass.constructor.call(this, {
		title : '模型信息',
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
						url : 'frame/model/save.jhtm',
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
							FrameModelApp.reload();
							
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
Ext.extend(FrameModelWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});










var FrameModelApp= function(){
	var store;
    var xg = Ext.grid;
	var grid;
	var sm;
	var pageSize=18;
	var infoDlg;
	var saveBtn;
	var form;
	return {
		init:function(){
			FrameModelApp.initStore();
			FrameModelApp.initGridPanel();
			FrameModelApp.initLayout();
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'frame/model/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'modelId',
		            fields: [
		                'modelId','name' ,'label','projectId','projectName'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('modelId', 'desc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					sm.selectFirstRow();
				}
			});
		},
		initGridPanel:function(){	
			function renderDate(v){
				if(v==null){
					return '';
				}
				var month=v.month*1+1;
				var year=v.year*1+1900;
				var date=v.date;
				var hours=v.hours;
				var minutes=v.minutes;
				var seconds=v.seconds;
				
				return year+'-'+month+'-'+date+' '+hours+':'+minutes+':'+seconds;
				
			}
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'modelId', 
	           header: "ID",
	           dataIndex: 'modelId',
	           width: 40
	        },{
	           id: 'name', 
	           header: "英文名称",
	           dataIndex: 'name',
	           width: 100
	        },{
		           id: 'label', 
		           header: "中文名称",
		           dataIndex: 'label',
		           width: 100
		        },{
	           header: "所属项目",
	           dataIndex: 'projectName',
	           width: 150,
	           sortable: false
	        },{
		           header: "项目ID",
		           dataIndex: 'projectId',
		           width: 150,
		           sortable: false,
		           hidden:true
		        }]);
	
		    cm.defaultSortable = true;
		    
			
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				sm: sm,
				region:'west',
				width:'300',
		        trackMouseOver:true,
		        frame:false,
		        autoScroll:true,
		        loadMask: true,
				tbar:[{
				  	text: '添加模型',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:FrameModelApp.addInfo
				 },'-',{
				  	text: '修改模型',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:FrameModelApp.loadInfo
				 },'-',{
				  	text: '删除模型',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:FrameModelApp.deleteInfo
				 }],
				bbar: new Ext.PagingToolbar({
		            pageSize: pageSize,
		            store: store,
		            displayInfo: true
		        }), 
				viewConfig: {
					enableRowBody:true,
           			showPreview:true,
		            forceFit:true
		        },
				renderTo:'modelGrid'
		    });
		    grid.on('rowdblclick',FrameModelApp.loadInfo);
		    grid.render();
		},
		
		addInfo:function(){
			FrameModelApp.showInfoDlg();
			form.reset();
			
		},
		initLayout:function(){
			var mainHeight=FineCmsMain.getMainPanelHeight()-1;	 
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'border',
		        el: 'modelCenter',
		        height:mainHeight,
				region:'center',
				items:[grid]
		    });
			FineCmsMain.addFunctionPanel(grid);
	     	//mainPanel.add(center);
	     	
	     	//
			store.load({params:{start:0, limit:pageSize}});
			center.syncSize();
			//mainPanel.doLayout();
    	},
    	loadInfo:function(){
    		if(sm.getSelected()==null){
				Ext.Msg.alert("编辑模型","请先选择一个模型！");
				return;
			}else{
				FrameModelApp.showInfoDlg();
				//infoDlg.show();
	    		var select=sm.getSelected();
	    		var id=select.get('modelId');
	    		//infoDlg.formPanel.getForm().loadData(id);
	    		//infoDlg.show();
	    		infoDlg.loadData(id);
			}
    		
    	}
    	,
		deleteInfo : function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除模型","请先选择一个模型！");
				return;
			}else{
				var s=Ext.Msg.confirm("删除模型","确定要删除选中的模型吗？",function(o){
					if(o=='yes'){
						var url='frame/model/deletes.jhtm?'+FrameModelApp.getSelectedIds();
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
				if(_success){
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					var totRecords= new Number(totalCount);
					if(start>0&&start>=totRecords){
						store.load({params:{start:start-pageSize, limit:pageSize}});
					}else{
						store.load({params:{start:start, limit:pageSize}});
					}
					Ext.Msg.alert("删除模型","删除模型成功！");
				}else{
					Ext.Msg.alert("删除模型","删除模型失败！");
				}
				
			}
			function failure(rep){
				alert("删除失败！");
				alert(rep.responseText);
			}
		}
		,
		getSelectedIds:function(){
			var ids="";
			var selections=sm.getSelections();
			var size=selections.length;
			for(var i=0;i<size;i++){
					var r=selections[i];
					if(ids.length==0){
						ids='ids='+r.get("modelId");
					}else{
						ids+="&ids="+r.get("modelId");
					}
			}
			return ids;
			
		},
		showInfoDlg:function(){
			if(!infoDlg){
				infoDlg=new FrameModelWindow();
			}
			infoDlg.show();
			//form.getForm().findField("obj.name").focus(true);
		},
		reload:function(){
			var lastO= store.lastOptions.params;
			var start=lastO.start;
			store.load({params:{start:start, limit:pageSize}});
		}
	};
}();
FrameModelApp.init();