var FrameProjectApp= function(){
	var store;
    var xg = Ext.grid;
	var grid;
	var sm;
	var componentSm;
	var pageSize=18;
	var infoDlg;
	var genDlg;
	var genResultDlg;
	var saveBtn;
	var form;
	var componentStore;
	return {
		init:function(){
			FrameProjectApp.initStore();
			FrameProjectApp.initGridPanel();
			FrameProjectApp.initLayout();
			
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
		                'projectId','packageName' ,'title','name','encode','basePath','sourcePath','webPath','databaseType','javascriptPath','driverClass','databaseName','databaseUser','databasePassword'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('projectId', 'asc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					sm.selectFirstRow();
				}
			});
			
		},
		initGridPanel:function(){	

			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'projectId', 
	           header: "ID",
	           dataIndex: 'projectId',
	           width: 40
	        },{
	           id: 'name', 
	           header: "英文名称",
	           dataIndex: 'name',
	           width: 100
	        },{
		           id: 'title', 
		           header: "中文名称",
		           dataIndex: 'title',
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
	        },{
	        	header: "脚本目录",
	           dataIndex: 'javascriptPath',
	           width: 150
	        },{
	        	header: "数据库类型",
	            dataIndex: 'databaseType',
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
		        autoDestroy:true,
		        height:mainHeight,
		        autoScroll:true,
		        loadMask: true,
				tbar:[{
				  	text: '添加项目',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:FrameProjectApp.showInfoDlg
				 },'-',{
				  	text: '修改项目',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:FrameProjectApp.loadInfo
				 },'-',{
				  	text: '删除项目',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:FrameProjectApp.deleteInfo
				 }, '->',{
				  	text: '生成代码',
		            iconCls: 'x-btn-text-icon generate',
		            scope: this,
					handler:FrameProjectApp.showGenerateDlg
				 },'-', {
				  	text: '运行测试',
		            iconCls: 'x-btn-text-icon server',
		            scope: this,
					handler:FrameProjectApp.deleteInfo
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
		    grid.on('rowdblclick',FrameProjectApp.loadInfo);
		    grid.render();
		},
		initLayout:function(){
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'projectCenter',
				contentEl:'projectGrid',
				items:[grid]
		    });
			store.load({params:{start:0, limit:pageSize}});
	     	FineCmsMain.addFunctionPanel(center);
	     	
    	},
    	loadInfo:function(){
    		if(sm.getSelected()==null){
				Ext.Msg.alert("删除项目","请先选择一个项目！");
				return;
			}else{
				FrameProjectApp.showInfoDlg();
	    		var select=sm.getSelected();
	    		var id=select.get('projectId');
	    		form.getForm().load({url:'frame/project/load.jhtm?id='+id, waitMsg:'正在加载数据...'});
			}
    		
    	}
    	,
		deleteInfo : function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除项目","请先选择一个项目！");
				return;
			}else{
				var s=Ext.Msg.confirm("删除项目","确定要删除选中的项目吗？",function(o){
					if(o=='yes'){
						var url='frame/project/deletes.jhtm?'+FrameProjectApp.getSelectedIds();
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
		},
		showGenerateDlg:function(){
			
			var select=sm.getSelected();
			var pid=0;
			if(!select){
				Ext.Msg.alert("请选择一个项目！");
				return;
			}
    		pid=select.get('projectId');
			if(!genDlg){
				componentStore = new Ext.data.Store({
			        proxy: new Ext.data.HttpProxy({
			            url: 'frame/component/list.jhtm'
			        }),
			        reader: new Ext.data.JsonReader({
			            root: 'objs',
			            totalProperty: 'totalCount',
			            id: 'componentId',
			            fields: [
			                'componentId','name' ,'fileType'
			            ]
			        }),
			        remoteSort: true
			    });
				componentStore.setDefaultSort('componentId', 'asc');
				
				componentSm = new xg.CheckboxSelectionModel();
				var cm = new Ext.grid.ColumnModel([componentSm,{
		           id: 'componentId', 
		           header: "ID",
		           dataIndex: 'componentId',
		           width: 40
		        },{
			           id: 'name', 
			           header: "组件名称",
			           dataIndex: 'name',
			           width: 100
			        },{
				           id: 'fileType', 
				           header: "组件类型",
				           dataIndex: 'fileType',
				           width: 100
				        }]);
		
				componentSm.defaultSortable = true;
			    
			    grid = new Ext.grid.GridPanel({
			        store: componentStore,
			        cm: cm,
					sm: componentSm,
					height:300,
			        trackMouseOver:true,
			        frame:false,
			        autoScroll:true,
			        loadMask: true,
					
					bbar: new Ext.PagingToolbar({
			            pageSize: pageSize,
			            store: componentStore,
			            displayInfo: true
			        }), 
					viewConfig: {
						enableRowBody:true,
	           			showPreview:true,
			            forceFit:true
			        },
					renderTo:'projectComponentGrid'
			    });
			    grid.render();
			    componentStore.load({params:{start:0, limit:pageSize}});
				genDlg = new Ext.Window({
			        title: '项目【'+sm.getSelected().get("title")+'】代码生成',
			        width: 400,
			        height:475,
			        minWidth: 300,
			        minHeight: 200,
			        layout: 'fit',
			        plain:true,
			        modal:true,
			        bodyStyle:'padding:5px;',
			        buttonAlign:'center',
			        items: grid,
			        buttons: [{
			        	text:'生成',
			        	handler:function(){
			        	FrameProjectApp.generateCode();
			        }
			        },{
			            text: '取消',
			            handler:function(){
			        		genDlg.hide();
			        	}
			        }
			        ]
			    });
			}
			componentStore.load({params:{start:0, limit:pageSize}});
			componentStore.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					componentSm.selectRange(0,pageSize);
				}
			});
			//genDlg.setTile('项目【'+sm.getSelected().get("title")+"】代码生成");
			genDlg.show();
			
		}
		,
		showGenerateResultDlg:function(){
			Ext.getDom("generateDiv").innerHTML="";
			if(!genResultDlg){
				genResultDlg = new Ext.Window({
			        title: '项目代码生成结果',
			        width: 350,
			        height:350,
			        minWidth: 300,
			        minHeight: 200,
			        layout: 'fit',
			        plain:true,
			        modal:true,
			        autoScroll:true,
			        buttonAlign:'center',
			        contentEl: 'generateDiv',
			        buttons: [{
			            text: '关闭',
			            handler:function(){
			        	genResultDlg.hide();
			        	}
			        }
			        ]
			    });
			}
			genResultDlg.setTitle('项目【'+sm.getSelected().get("title")+"】代码生成结果");
			genResultDlg.show();
			
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
		generateCode:function(){
			var pid=0;
			if(sm.getSelected()!=null){
				pid=sm.getSelected().get("projectId");
			}
			var idNum=0;
			
			var selections=componentSm.getSelections();
			FrameProjectApp.showGenerateResultDlg();
			
			FrameProjectApp.generateAction(idNum,pid);
		},
		generateAction:function(idNum,pid){
			var selections=componentSm.getSelections();
			var cId=selections[idNum].get('componentId');
			var url='frame/generate/index.jhtm?pid='+pid+"&cid="+cId;
			Ext.Ajax.request({
			   url: url,
			   success: function(o){
					var div=Ext.getDom('generateDiv');
					
					
					div.innerHTML=div.innerHTML+o.responseText;
					if(idNum==selections.length){
						return ;
					}
					idNum++;
					FrameProjectApp.generateAction(idNum,pid);
			    },
			   failure: function(){alert("失败！");}
			});
		}
		,
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
			        typeAhead: true,
			        mode: 'local',
			        value:'utf-8',
			        triggerAction: 'all',
			        emptyText:'请选择编码...',
			        selectOnFocus:true,
			        hiddenName: 'obj.encode'
			    });
			    
			    
			    var dbStore = new Ext.data.SimpleStore({
			        fields: ['name', 'label', 'tip'],
			        data : Ext.ux.DatabaseType // from states.js
			    });
			    var dbType = new Ext.form.ComboBox({
			        store: dbStore,
			        fieldLabel: '数据库类型',
			        displayField:'label',
			        valueField:'name',
			        typeAhead: true,
			        mode: 'local',
			        value:'mysql',
			        triggerAction: 'all',
			        emptyText:'请选择数据库类型...',
			        selectOnFocus:true,
			        hiddenName: 'obj.databaseType'
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
			            {name: 'obj.projectId', mapping:'projectId'}, 
			            {name: 'obj.name', mapping:'name'},
			            {name: 'obj.encode', mapping:'encode'},
			            {name: 'obj.basePath', mapping:'basePath'},
			            {name: 'obj.title', mapping:'title'},
			            {name: 'obj.packageName', mapping:'packageName'},
			            {name: 'obj.sourcePath', mapping:'sourcePath'},
			            {name: 'obj.webPath', mapping:'webPath'},
			            {name: 'obj.javascriptPath', mapping:'javascriptPath'},
			            {name: 'obj.driverClass', mapping:'driverClass'},
			            {name: 'obj.databaseType', mapping:'databaseType'},
			            {name: 'obj.databaseUser', mapping:'databaseUser'},
			            {name: 'obj.databasePassword', mapping:'databasePassword'},
			            {name: 'obj.databaseUrl', mapping:'databaseUrl'},
			            {name: 'obj.databaseName', mapping:'databaseName'}
			        ]),
			        items: [{
			        	name:'obj.projectId',
			        	xtype:'hidden',
			        	value:0
			        	
			        },
			        {
	                    fieldLabel: '英文名称',
	                    name: 'obj.name',
	                    allowBlank:false
	                    
	                },
			        {
	                    fieldLabel: '中文名称',
	                    name: 'obj.title',
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
	                },{
	                    fieldLabel: '脚本目录',
	                    name: 'obj.javascriptPath',
	                    allowBlank:false,
	                    value:'js'
	                },dbType,{
	                    fieldLabel: '驱动类',
	                    name: 'obj.driverClass',
	                    allowBlank:false
	                },{
	                    fieldLabel: '数据库名',
	                    name: 'obj.databaseName',
	                    allowBlank:false
	                },{
	                    fieldLabel: '数据库连接',
	                    name: 'obj.databaseUrl',
	                    allowBlank:false
	                },{
	                    fieldLabel: '数据库帐户',
	                    name: 'obj.databaseUser',
	                    allowBlank:false
	                },{
	                    fieldLabel: '数据库密码',
	                    name: 'obj.databasePassword',
	                    allowBlank:false
	                }]
			    });
				
				form.on({
					actioncomplete: function(form, action){
						FrameMsg.msg("保存项目",action.result.msg);
		               
						saveBtn.enable();
						form.reset();
						FrameProjectApp.reload();
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
			        height:475,
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
						handler:FrameProjectApp.showInfoDlg
					 },'-',{
					  	text: '下一条',
			            iconCls: 'x-btn-text-icon next',
			            scope: this,
						handler:FrameProjectApp.loadInfo
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
FrameProjectApp.init();