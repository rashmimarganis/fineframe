var ProjectApp= function(){
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
			ProjectApp.initStore();
			ProjectApp.initGridPanel();
			ProjectApp.initLayout();
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'frame/project/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'projectId',
		            fields: [
		                'projectId','packageName' ,'name','encode','basePath','sourcePath','webPath'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('projectId', 'desc');
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
	           id: 'projectId', 
	           header: "ID",
	           dataIndex: 'projectId',
	           width: 40
	        },{
	           id: 'name', 
	           header: "名称",
	           dataIndex: 'name',
	           width: 100
	        },{
	           header: "编码",
	           dataIndex: 'encode',
	           width: 150
	        },{
	           header: "项目目录",
	           dataIndex: 'basePath',
	           width: 150
	        },{
	           header: "基础包名",
	           dataIndex: 'packageName',
	           width: 150
	        },{
	           header: "Java目录",
	           dataIndex: 'sourcePath',
	           width: 150
	        },{
		           header: "页面目录",
		           dataIndex: 'webPath',
		           width: 150
		        }]);
	
		    cm.defaultSortable = true;
		    
			var mainHeight=FineCmsMain.getMainPanelHeight()-1;	 
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        height:mainHeight,
		        autoScroll:true,
		        loadMask: true,
				tbar:[{
				  	text: '添加项目',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:ProjectApp.showInfoDlg
				 },'-',{
				  	text: '修改项目',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:ProjectApp.loadInfo
				 },'-',{
				  	text: '删除项目',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:ProjectApp.deleteInfo
				 }, '->',{
				  	text: '生成代码',
		            iconCls: 'x-btn-text-icon generate',
		            scope: this,
					handler:ProjectApp.deleteInfo
				 },'-', {
				  	text: '运行测试',
		            iconCls: 'x-btn-text-icon server',
		            scope: this,
					handler:ProjectApp.deleteInfo
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
				renderTo:'projectGrid'
		    });
		    grid.render();
		},
		initLayout:function(){
			
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'projectCenter',
				region:'center',
				contentEl:'projectGrid',
				items:[grid]
		    });
	     	mainPanel.add(grid);
	     	
	     	//
			store.load({params:{start:0, limit:pageSize}});
			center.syncSize();
			mainPanel.doLayout();
    	},
    	loadInfo:function(){
    		ProjectApp.showInfoDlg();
    		form.getForm().load({url:'frame/project/load.jhtm?id=1', waitMsg:'Loading'});
    	}
    	,
		deleteInfo : function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除项目","请先选择一个项目！");
				return;
			}else{
				var s=Ext.Msg.confirm("删除项目","确定要删除选中的项目吗？",function(o){
					if(o=='yes'){
						var url='frame/project/deletes.jhtm?'+ProjectApp.getSelectedIds();
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
					Ext.Msg.alert("删除项目","删除项目成功！");
				}else{
					Ext.Msg.alert("删除项目","删除项目失败！");
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
						ids='ids='+r.get("projectId");
					}else{
						ids+="&ids="+r.get("projectId");
					}
			}
			return ids;
			
		},
		showInfoDlg:function(){
			if(!infoDlg){
				var store = new Ext.data.SimpleStore({
			        fields: ['name', 'label', 'tip'],
			        data : Ext.ux.encoding // from states.js
			    });
			    var combo = new Ext.form.ComboBox({
			        store: store,
			        fieldLabel: '文件编码',
			        displayField:'label',
			        valueField:'name',
			        name:'obj.encode',
			        typeAhead: true,
			        mode: 'local',
			        value:'utf-8',
			        triggerAction: 'all',
			        emptyText:'请选择编码...',
			        selectOnFocus:true
			    });
				form = new Ext.form.FormPanel({
			        baseCls: 'x-plain',
			        layout:'form',
			        clientValidation: true,
			        url:'frame/project/save.jhtm',
			        defaultType: 'textfield',
			        defaults: {width: 220},
			        labelAlign: 'left',
			        reader : new Ext.data.JsonReader({
			        	root  : 'data',
			        	successProperty: 'success'
			        }, [
			            {name: 'obj.projectId', mapping:'projectId'}, // custom mapping
			            {name: 'obj.name', mapping:'name'},
			            {name: 'obj.basePath', mapping:'basePath'},
			            {name: 'obj.packageName', mapping:'packageName'},
			            {name: 'obj.sourcePath', mapping:'sourcePath'},
			            {name: 'obj.webPath', mapping:'webPath'}
			        ]),
			        items: [{
			        	name:'obj.projectId',
			        	xtype:'hidden',
			        	value:0
			        	
			        },
			        {
	                    fieldLabel: '项目名称',
	                    name: 'obj.name',
	                    allowBlank:false
	                    
	                },combo,{
	                    fieldLabel: '项目目录',
	                    name: 'obj.basePath',
	                    allowBlank:false
	                },{
	                    fieldLabel: '基础包名',
	                    name: 'obj.packageName',
	                    allowBlank:false
	                },{
	                    fieldLabel: '源码目录',
	                    name: 'obj.sourcePath',
	                    allowBlank:false,
	                    value:'src'
	                },{
	                    fieldLabel: '页面目录',
	                    name: 'obj.webPath',
	                    allowBlank:false,
	                    value:'webcontent'
	                }]
			    });
				
				form.on({
					actioncomplete: function(form, action){
						FrameMsg.msg("保存项目",action.result.msg);
		               
						saveBtn.enable();
						form.reset();
						ProjectApp.reload();
		        	},
		        	actionfailed: function(form, action){
		                saveBtn.enable();
		                FrameMsg.msg("保存项目",action.result.msg);
		        	}
				});
				saveBtn=form.addButton({
			        text: '保存',
			        handler: function(){
						if(form.getForm().isValid()){
							form.getForm().submit({url:'frame/project/save.jhtm', waitMsg:'正在保存数据...'});
							saveBtn.disable();
						}else{
							Ext.Msg.alert("保存项目","请把项目信息填写完整！");
						}
			        }
			    });
				
			    infoDlg = new Ext.Window({
			        title: '项目信息',
			        width: 400,
			        height:275,
			        minWidth: 300,
			        minHeight: 200,
			        layout: 'fit',
			        plain:true,
			        modal:true,
			        bodyStyle:'padding:5px;',
			        buttonAlign:'center',
			        items: form,
			        tbar:[{
					  	text: '上一条',
			            iconCls: 'x-btn-text-icon prev',
			            scope: this,
						handler:ProjectApp.showInfoDlg
					 },'-',{
					  	text: '下一条',
			            iconCls: 'x-btn-text-icon next',
			            scope: this,
						handler:ProjectApp.loadInfo
					 }],
			        buttons: [saveBtn,{
			            text: '取消',
			            handler:function(){
			        		infoDlg.hide();
			        	}
			        }
			        ]
			    });
			    
			}
			infoDlg.show();
			form.getForm().findField("obj.name").focus(true);
		},
		reload:function(){
			var lastO= store.lastOptions.params;
			var start=lastO.start;
			store.load({params:{start:start, limit:pageSize}});
		}
	};
}();
ProjectApp.init();