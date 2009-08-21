var App= function(){
	var viewport;
	var dlg;
	var store;
	var inputStore;
	var outputStore;
    var xg = Ext.grid;
	var grid;
	var inputParamSm;
	var outputParamSm;
	var inputParamGrid;
	var outputParamGrid;
	var inputParamDlg;
	var outputParamDlg;
	var sm;
	var id;
	var name;
	var title;
	var url;
	var oldName;
	var typeId=0;
	var pageSize=18;
	var westPanel;
	var contextPath="/";
	var cfgDlg;
	return {
		init:function(){
			Ext.QuickTips.init();
			//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			App.initLayout();
			setTimeout(function(){
		        Ext.get('loading').remove();
		        Ext.get('loading-mask').fadeOut({remove:true});
		    }, 250);
		},
		initLayout:function(){
			App.initInputParamGridPanel();
			App.initOutputParamGridPanel();
			App.initGridPanel();
			App.initTree();
			var west=new Ext.Panel({
					el:'west',
					title:'表单分类',
					contentEl:'tree',
				  	region:'west',
					layout:'fit',
					width:220,
					split:true,
					margins:'0 0 0 0',
					items:westPanel
				  }
			   );
	
		  var centerPanel=new Ext.Panel({
				el:'center',
				contentEl:'grid',
				region:'center',
				layout:'border',
				margins:'0 0 0 0',
				items:[grid,new Ext.Panel({
					region:'south',
					height:200,
					layout:'border',
					split:true,
					items:[
					new Ext.Panel({
						region:'center',
						split:true,
						layout:'fit',
						items:[inputParamGrid],
						tbar:[{
				  	text: '新增输入参数',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.addInputParamDlg
				  },'-',{
				  	text: '删除输入参数',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:App.deleteInputParam
				  }]
					}),new Ext.Panel({
						width:450,
						region:'east',
						split:true,
						layout:'fit',
						items:[outputParamGrid],
						tbar:[{
				  	text: '新增输出参数',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.addOutputParamDlg
				  },'-',{
				  	text: '删除输出参数',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:App.deleteOutputParam
				  },'-',{
				  	text: '参数枚举值',
		            iconCls: 'x-btn-text-icon detail',
		            scope: this,
					handler:App.deleteOutputParam
				  }]
					})]
				})]
		  });
		 
		   viewport = new Ext.Viewport({
			layout:'border',
			items:[
				west,centerPanel
			]
			});
			
    	},
		deleteInputParam:function(){
			var r=inputParamSm.getSelected();
			if(r==null){
				System.alert("删除输入参数","请选择一个要删除的输入参数！");
				return;
			}else{
				Ext.MessageBox.confirm("删除输入参数","确定要删除选中的参数吗？", function(btn){
					if(btn=='yes'){
						Ext.Ajax.request({
							url: '../flow/inputparam/delete.do?id=' + r.get("driverInputParamID"),
							success: success,
							failure: failure
						});
					}
				});
			}
			
			function success(rep){				
				eval("var re="+rep.rsponseText);
				if (re.success) {
					System.alert("删除参数", "删除输入参数成功！");
					var lastO = inputStore.lastOptions.params;
					var start = lastO.start;
					inputStore.load({
						params: {
							start: start,
							limit: pageSize
						}
					});
				}else{
					System.alert("删除参数", "删除输入参数失败！");
				}
			}
			function failure(rep){
				System.infoDlg("系统出现异常",rep.responseText);
			}
		},
		deleteOutputParam:function(){
			var r=outputParamSm.getSelected();
			if(r==null){
				System.alert("删除输出参数","请选择一个要删除的输出参数！");
				return;
			}else{
				Ext.MessageBox.confirm("删除输出参数","确定要删除选中的参数吗？", function(btn){
					if(btn=='yes'){
						Ext.Ajax.request({
							url:'../flow/outputparam/delete.do?id='+r.get("driverOutputParamID"),
							success:success,
							failure:failure
						});
					}
				});
			}
			
			function success(rep){	
				eval("var re="+rep.reponseText);	
				if(re.success){
					System.alert("删除参数","删除输出参数成功！");
					var lastO= outputStore.lastOptions.params;
					var start=lastO.start;
					outputStore.load({params:{start:start, limit:pageSize}});
				}else{
					System.alert("删除参数", "删除输入参数失败！");
				}
				
			}
			function failure(rep){
				System.infoDlg("系统出现异常",rep.responseText);
			}
		},
		addInputParamDlg:function(){
			var driverId=Ext.getDom("driverId").value;
			if (driverId == "") {
				System.infoDlg("添加参数", "请先选择一个表单！");
				return;
			}
			else {
				App.showInputParamDlg();
			}
		},
		addOutputParamDlg:function(){
			var driverId=Ext.getDom("driverId").value;
			if (driverId == "") {
				System.infoDlg("添加参数", "请先选择一个表单！");
				return;
			}
			else {
				App.showOutputParamDlg();
			}
		},
		editInputParamDlg:function(){
			App.showInputParamDlg();
		},
		editOutputParamDlg:function(){
			App.showOutputParamDlg();
		},
		initInputGridStore:function(){
			inputStore = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../flow/inputparam/page.do?driverId=0'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'driverInputParamID',
		            fields: [
		                'driverInputParamID','paramName','paramAlias'
		            ]
		        }),
		        remoteSort: true
		    });
		    inputStore.setDefaultSort('driverInputParamID', 'asc');
		}
		,
		initOutputGridStore:function(){
			outputStore = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../flow/outputparam/page.do?driverId=0'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'driverOutputParamID',
		            fields: [
		                'driverOutputParamID','paramName','paramAlias'
		            ]
		        }),
		        remoteSort: true
		    });
		    outputStore.setDefaultSort('driverOutputParamID', 'asc');
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../flow/driver/page.do?contextPath=/'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'flowDriverID','flowDriverName','memo','readURL','writeURL','contextPath'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('flowDriverID', 'asc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					sm.selectFirstRow();
					App.loadParams();
				}
			});
		},
		initTree:function(){
		    westPanel = new Ext.tree.TreePanel({
		        el:'tree',
		        autoScroll:true,
		        animate:true,
		        enableDD:false,
				border:false,
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
		        containerScroll: true, 
		        loader: new Ext.tree.TreeLoader({
		            dataUrl:'../flow/driver/contextPath.do'
		        })
		    });
			westPanel.getSelectionModel().on({
		        'beforeselect' : function(sm, node){
					selectNode=node;
					if(node.id!='0'){
						if(!node.expanded){
							node.expand()
						};
					}
		        },
		        'selectionchange' : function(sm, node){
					if (node.getDepth() == 1) {
						contextPath=node.id;
						store.proxy = new Ext.data.HttpProxy({
							url: '../flow/driver/page.do?node='+node.id
						});
						store.load({params:{start:0, limit:pageSize}});
					}
		        },
		        scope:this
		    });
		    var root = new Ext.tree.AsyncTreeNode({
		        text: '所有表单分类',
		        draggable:false,
		        id:'0'
		    });
		    westPanel.setRootNode(root);
		    westPanel.render();
			
			var expand=function(n){
				if(n.firstChild!=null){
					n.firstChild.select();
				}
			};
			root.on('expand',expand);
		    root.expand();
			root.select();
			selectNode=root;
		},
		initGridPanel:function(){	
			App.initStore();
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'id', 
	           header: "ID",
	           dataIndex:  'flowDriverID',
	           width: 20
	        },{
	           header: "名称",
	           dataIndex: 'flowDriverName',
	           width: 200
	        },{
	           header: "只读URL",
	           dataIndex:  'readURL',
	           width: 200,
			   sort:false
	        },{
	           header: "写URL",
	           dataIndex: 'writeURL',
	           width: 200
	        },{
	           header: "表单分类",
	           dataIndex: 'contextPath',
	           width: 80
	        },{
	           header: "说明",
	           dataIndex: 'memo',
	           width: 80
	        }]);
	
		    cm.defaultSortable = true;
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
				region:'center',
		        loadMask: true,
				tbar:[{
				  	text: '新增表单',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.add
				  },'-',{
				  	text: '编辑表单',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.edit
				  },'-',{
				  	text: '删除表单',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.deleteInfo
				  }],
				 bbar: new Ext.PagingToolbar({
		            pageSize: pageSize,
		            store: store,
		            displayInfo: true
		        }), 
				viewConfig: {
					enableRowBody:true,
           			showPreview:false,
		            forceFit:true
		        },
				renderTo:'grid'
		    });
			grid.on('rowdblclick',App.edit);
			grid.on('click',App.loadParams);
		    grid.render();
		},
		initInputParamGridPanel:function(){	
			App.initInputGridStore();
			inputParamSm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([inputParamSm,{
	           id: 'id', 
	           header: "ID",
	           dataIndex:  'driverInputParamID',
	           width: 20,
			   hidden:true
	        },{
	           header: "参数ID",
	           dataIndex: 'paramName',
	           width: 200
	        },{
	           header: "参数名",
	           dataIndex:  'paramAlias',
	           width: 200,
			   sort:false
	        }]);
	
		    cm.defaultSortable = true;
		    inputParamGrid = new Ext.grid.GridPanel({
		        store: inputStore,
		        cm: cm,
				sm: inputParamSm,
		        trackMouseOver:true,
		        frame:false,
				region:'center',
		        loadMask: true,
				viewConfig: {
					enableRowBody:true,
           			showPreview:false,
		            forceFit:true
		        },
				renderTo:'inputParamGrid'
		    });
			inputParamGrid.on('rowdblclick',App.editInpuparam);
		    inputParamGrid.render();
		},
		editInpuparam:function(){
			var select=sm.getSelected();
			if(select==null){
				 alert("请先选择一个工作流表单！");
				 return;
			}
			Ext.Ajax.request({
				url:'../flow/inputparam/load.do',
				params:'id='+select.get('driverInputParamID'),
				success:success,
				failure:failure
			});
			function success(rep){				
				eval("var res="+rep.responseText);
				Ext.getDom("driverInputParamID").value=res.driverInputParamID;
				Ext.getDom("paramName").value=res.paramName;
				Ext.getDom("paramAlias").value=res.paramAlias;
				App.showInputParamDlg();
			}
			function failure(rep){
				System.infoDlg("系统出现异常",rep.responseText);
			}
		}
		,
		initOutputParamGridPanel:function(){	
			App.initOutputGridStore();
			outputParamSm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([outputParamSm,{
	           id: 'id', 
	           header: "ID",
	           dataIndex:  'driverOutputParamID',
	           width: 20,
			   hidden:true
	        },{
	           header: "参数ID",
	           dataIndex: 'paramName',
	           width: 200
	        },{
	           header: "参数名",
	           dataIndex:  'paramAlias',
	           width: 200,
			   sort:false
	        }]);
	
		    cm.defaultSortable = true;
		    outputParamGrid = new Ext.grid.GridPanel({
		        store: outputStore,
		        cm: cm,
				sm: outputParamSm,
		        trackMouseOver:true,
		        frame:false,
				region:'center',
		        loadMask: true,
				viewConfig: {
					enableRowBody:true,
           			showPreview:false,
		            forceFit:true
		        },
				renderTo:'outputParamGrid'
		    });
			outputParamGrid.on('rowdblclick',App.outputParamEdit);
		    outputParamGrid.render();
		},
		outputParamEdit:function(){
			var select=sm.getSelected();
			if(select==null){
				 alert("请先选择一个工作流表单！");
				 return;
			}
			Ext.Ajax.request({
				url:'../flow/outputparam/load.do',
				params:'id='+select.get('flowDriverID'),
				success:success,
				failure:failure
			});
			function success(rep){				
				eval("var res="+rep.responseText);
				Ext.getDom("driverOutputParamID").value=res.driverOutputParamID;
				Ext.getDom("outPutParamName").value=res.paramName;
				Ext.getDom("outputParamAlias").value=res.paramAlias;
				App.showOutputParamDlg();
			}
			function failure(rep){
				System.infoDlg("系统出现异常",rep.responseText);
			}
		}
		,
		loadParams:function(){
			var driverId=sm.getSelected().get("flowDriverID");
			Ext.getDom("driverId").value=driverId;
			inputStore.proxy = new Ext.data.HttpProxy({
				url: '../flow/inputparam/page.do?driverId='+driverId
			});
			inputStore.load({params:{start:0, limit:pageSize}});
			outputStore.proxy = new Ext.data.HttpProxy({
				url: '../flow/outputparam/page.do?driverId='+driverId
			});
			outputStore.load({params:{start:0, limit:pageSize}});
		},
		add:function(){
			App.showDlg();
		},
		showDlg:function(){
			if(!dlg){
				dlg = new Ext.Window({
	                el:'dlg',
	                contentEl:'dlgContent',
					title: '新增工作流表单',
	                width:380,
	                height:230,
	                closeAction:'hide',
	                plain: true,
					modal:true,
					bodyStyle:'padding:5px;',
    				buttonAlign:'right',
	                buttons: [{
	                    text:'保存',
						handler:App.saveInfo
	                },{
	                    text: '取消',
	                    handler: function(){
							App.initFormValue();
	                        dlg.hide();
	                    }
	                }]
            	});
	        }
	        dlg.show(this);
			Ext.get("flowDriverName").focus(true);
		},
		showInputParamDlg:function(){
			if(!inputParamDlg){
				inputParamDlg = new Ext.Window({
	                el:'inputParamDlg',
	                contentEl:'inputParamContent',
					title: '表单输入参数',
	                width:260,
	                height:150,
	                closeAction:'hide',
	                plain: true,
					modal:true,
					bodyStyle:'padding:5px;',
    				buttonAlign:'right',
	                buttons: [{
	                    text:'保存',
						handler:App.saveInputInfo
	                },{
	                    text: '取消',
	                    handler: function(){
							App.initInputFormValue();
	                        inputParamDlg.hide();
	                    }
	                }]
            	});
	        }
	        inputParamDlg.show(this);
			//Ext.get("flowDriverName").focus(true);
		},
		initInputFormValue:function(){
			Ext.getDom("driverInputParamID").value="-1";
			Ext.getDom("paramName").value="";
			Ext.getDom("paramAlias").value="";
		},
		initOutputFormValue:function(){
			Ext.getDom("driverOutputParamID").value="-1";
			Ext.getDom("outPutParamName").value="";
			Ext.getDom("outputParamAlias").value="";
		}	
		,
		showOutputParamDlg:function(){
			if(!outputParamDlg){
				outputParamDlg = new Ext.Window({
	                el:'outputParamDlg',
	                contentEl:'outputParamContent',
					title: '表单输出参数',
	                width:300,
	                height:180,
	                closeAction:'hide',
	                plain: true,
					modal:true,
					bodyStyle:'padding:5px;',
    				buttonAlign:'right',
	                buttons: [{
	                    text:'保存',
						handler:App.saveOuputInfo
	                },{
	                    text: '取消',
	                    handler: function(){
							App.initOutputFormValue();
	                        outputParamDlg.hide();
	                    }
	                }]
            	});
	        }
	        outputParamDlg.show(this);
			//Ext.get("flowDriverName").focus(true);
		},
		saveInputInfo:function(){
			var name=Ext.getDom("paramName");
			var alias=Ext.getDom("paramAlias");
			
			if(name==""){
				System.alert("添加参数","请输入参数编号！");
				return;
			}
			
			if(alias==""){
				System.alert("添加参数","请输入参数名称！");
				return;
			}
			System.waitDlg("保存参数","正在保存参数...");
			var driverId=Ext.getDom("driverId").value;
			Ext.Ajax.request({
				form:'inputParamForm',
				url:'../flow/inputparam/save.do?driverId='+driverId,
				success:success,
				failure:failure
			});
			function success(rep){
				System.msgHide();
				eval("var ro="+rep.responseText);
				if(ro.success){
					System.alert("增加输出参数","保存输入参数成功！");
					var lastO= inputStore.lastOptions.params;
					var start=lastO.start;
					inputStore.load({params:{start:start, limit:pageSize}});
					inputParamDlg.hide();
					App.initInputFormValue();
				}else{
					System.alert("保存输入","保存输入参数失败！");
				}
				App.initFormValue();
			}
			function failure(rep){
				System.alert("错误","保存输入参数失败！");
				System.infoDlg("系统异常",rep.responseText);
				
			}
			
		}
		,
		saveOuputInfo:function(){
			var name=Ext.getDom("outPutParamName");
			var alias=Ext.getDom("outputParamAlias");
			
			if(name==""){
				System.alert("添加参数","请输入参数编号！");
				return;
			}
			
			if(alias==""){
				System.alert("添加参数","请输入参数名称！");
				return;
			}
			var driverId=Ext.getDom("driverId").value;
			System.waitDlg("保存参数","正在保存参数...");
			Ext.Ajax.request({
				form:'outputParamForm',
				url:'../flow/outputparam/save.do?driverId='+driverId,
				success:success,
				failure:failure
			});
			function success(rep){
				System.msgHide();
				eval("var ro="+rep.responseText);
				if(ro.success){
					System.alert("增加输出参数","保存输出参数成功！");
					var lastO= outputStore.lastOptions.params;
					var start=lastO.start;
					outputStore.load({params:{start:start, limit:pageSize}});
					outputParamDlg.hide();
					App.initOutputFormValue();
				}else{
					System.alert("保存输入参数","保存输入参数失败！");
				}
				App.initFormValue();
			}
			function failure(rep){
				System.alert("错误","保存工作流表单失败！");
				System.infoDlg("系统异常",rep.responseText);
				
			}
		}
		,
		edit:function(){
			var select=sm.getSelected();
			if(select==null){
				 alert("请先选择一个工作流表单！");
				 return;
			}
			Ext.Ajax.request({
				url:'../flow/driver/load.do',
				params:'flowDriverId='+select.get('flowDriverID'),
				success:success,
				failure:failure
			});
			function success(rep){				
				eval("var res="+rep.responseText);
				Ext.getDom("flowDriverID").value=res.flowDriverID;
				Ext.getDom("flowDriverName").value=res.flowDriverName;
				Ext.getDom("readURL").value=res.readURL;
				Ext.getDom("writeURL").value=res.writeURL;
				Ext.getDom("memo").value=res.memo;
				Ext.getDom("contextPath").value=res.contextPath;
				App.showDlg();
			}
			function failure(rep){
				System.infoDlg("系统出现异常",rep.responseText);
			}
		},
		saveInfo:function(){
			var driverId=Ext.getDom("flowDriverID").value;
			var memo=Ext.getDom("memo").value;
			var driverName=Ext.getDom("flowDriverName").value;
			var cp=Ext.getDom("contextPath").value;
			
			if(driverName==''){
				System.alert("错误","请输入一个表单名称！");
				return;
			}
			System.waitDlg("保存表单","正在保存表单...");
			Ext.Ajax.request({
				form:'driverForm',
				url:'../flow/driver/save.do',
				success:success,
				failure:failure
			});
			function success(rep){
				System.msgHide();
				eval("var ro="+rep.responseText);
				if(ro.success){
					if(westPanel.getNodeById(cp)==undefined){
						westPanel.getRootNode().appendChild({text:cp,id:cp,parentId:0,leaf:true});
					}
					System.alert("增加工作流表单","保存工作流表单成功！");
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					store.load({params:{start:start, limit:pageSize}});
					dlg.hide();
					App.initFormValue();
				}else{
					System.alert("保存工作流表单","保存工作流表单失败！");
				}
				App.initFormValue();
			}
			function failure(rep){
				System.alert("错误","保存工作流表单失败！");
				System.infoDlg("系统异常",rep.responseText);
				
			}
		},
		deleteInfo:function(){
			if(sm.getSelected==null){
				System.alert("删除表单","请先选择一个或多个工作流表单！");
				return;
			}
			if(!confirm("确定要删除选中的工作流表单吗？")){
				return;
			}
			System.waitDlg("删除工作流表单","正在删除工作流表单...");
			var url='../flow/driver/delete.do?node='+contextPath+'&'+App.getSelectedIds();
			Ext.Ajax.request({
				url:url,
				success:success,
				failure:failure
			});
			function success(rep){
				System.msgHide();
				eval("var o="+rep.responseText);
				if(o.success){
					System.alert("删除表单","删除表单成功！");
					var totalCount= new Number(o.totalCount);
					if(totalCount==0){
						node=westPanel.getSelectionModel().selectPrevious();
						westPanel.getNodeById(contextPath).remove();
					}
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					if(start>0&&start>=totRecords){
						store.load({params:{start:start-pageSize, limit:pageSize}});
					}else{
						store.load({params:{start:start, limit:pageSize}});
					}
				}else{
					System.alert("删除表单","对不起，删除表单失败！");
				}	
			}
			function failure(rep){
				System.alert("删除工作流表单","删除失败！");
				System.infoDlg("删除工作流表单",rep.responseText);
			}
		},
		initFormValue:function(){
			Ext.getDom("flowDriverID").value="-1";
			Ext.getDom("flowDriverName").value="";
			Ext.getDom("readURL").value="";
			Ext.getDom("writeURL").value="";
			Ext.getDom("memo").value="";
			Ext.getDom("contextPath").value="";
		},
		getSelectedIds:function(){
			var ids="";
			var selections=sm.getSelections();
			var size=selections.length;
			for(var i=0;i<size;i++){
				var r=selections[i];
				if(ids.length==0){
					ids="ids["+i+"]="+r.get("flowDriverID");
				}else{
					ids+="&ids["+i+"]="+r.get("flowDriverID");
				}
			}
			return ids;
			
		},
		showCfgDlg:function(){
			if(!cfgDlg){
				
			}
			cfgDlg.show();
		}
	};
}();
Ext.onReady(App.init);