var App= function(){
	var viewport;
	var dlg;
	var store;
    var xg = Ext.grid;
	var grid;
	var sm;
	var id;
	var name;
	var title;
	var url;
	var oldName;
	var typeId=0;
	var pageSize=18;
	var westPanel;
	var flowMetaId=0;
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
			App.initGridPanel();
			App.initTree();
			var west=new Ext.Panel({
				el:'west',
				title:'可部署的流程',
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
				layout:'fit',
				margins:'0 0 0 0',
				items:grid
		  });
		viewport = new Ext.Viewport({
			layout:'border',
			items:[
				west,centerPanel
			]
			});
    	},
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../flow/deploy/page.do?flowMetaId=0'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'flowDeployID','flowDeployName','createTime','currentState','memo'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('flowDeployID', 'asc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					sm.selectFirstRow();
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
		            dataUrl:'../flow/deploy/tree.do?_n='+Math.random()
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
					if (node.getDepth() == 2) {
						store.proxy = new Ext.data.HttpProxy({
							url: '../flow/deploy/page.do?flowMetaId='+node.id
						});
						flowMetaId=node.id;
						Ext.getDom("flowMetaId").value=node.id;
						store.load({params:{start:0, limit:pageSize}});
					}
		        },
		        scope:this
		    });
		    var root = new Ext.tree.AsyncTreeNode({
		        text: '所有可部署流程',
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
	           dataIndex:  'flowDeployID',
	           width: 20
	        },{
	           header: "名称",
	           dataIndex: 'flowDeployName',
	           width: 80
	        },{
	           header: "创建时间",
	           dataIndex: 'createTime',
	           width: 150,
			   renderer:renderDate
	        },{
	           header: "备注",
	           dataIndex:  'memo',
	           width: 150,
			   sort:false
	        },{
	           header: "当前状态",
	           dataIndex: 'currentState',
	           width: 150,
			   renderer:function(v){
				   	if(v=='preparing'){
						return '未启用';
					}else if(v=='ready'){
						return '已启用';
					}else{
						return v;
					}
			   }
	        }]);
	
		    cm.defaultSortable = true;
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        loadMask: true,
				tbar:[{
				  	text: '新增',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.add
				  },'-',{
				  	text: '编辑',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.edit
				  },'-',{
				  	text: '配置',
		            iconCls: 'x-btn-text-icon resource-config',
		            scope: this,
					handler:App.deleteInfo
				 },'-',{
				  	text: '删除',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:App.deleteInfo
				 },'-',{
				  	text: '设置期限',
		            iconCls: 'x-btn-text-icon calendar',
		            scope: this,
					handler:App.deleteInfo
				 },'-',{
				  	text: '启用',
		            iconCls: 'x-btn-text-icon enabled',
		            scope: this,
					handler:App.enable
				 },'-',{
				  	text: '停用',
		            iconCls: 'x-btn-text-icon stoped',
		            scope: this,
					handler:App.disable
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
		    grid.render();
		},
		enable:function(){
			Ext.Ajax.request({
				url:'../flow/deploy/enable.do',
				params:'flowDeployId='+select.get('flowDeployID'),
				success:success,
				failure:failure
			});
			function success(rep){
				var lastO= store.lastOptions.params;
				var start=lastO.start;
				store.load({params:{start:start, limit:pageSize}});
			}
			function failure(rep){
				alert("加载失败！");
				alert(rep.responseText);
			}
		},
		enable:function(){
			var select=sm.getSelected();
			Ext.Ajax.request({
				url:'../flow/deploy/enable.do',
				params:'flowMetaId='+flowMetaId+'&'+App.getSelectedIds(),
				success:success,
				failure:failure
			});
			function success(rep){
				var lastO= store.lastOptions.params;
				var start=lastO.start;
				store.load({params:{start:start, limit:pageSize}});
			}
			function failure(rep){
				alert("加载失败！");
				alert(rep.responseText);
			}
		},
		disable:function(){
			var select=sm.getSelected();
			Ext.Ajax.request({
				url:'../flow/deploy/disable.do',
				params:'flowMetaId='+flowMetaId+'&'+App.getSelectedIds(),
				success:success,
				failure:failure
			});
			function success(rep){
				var lastO= store.lastOptions.params;
				var start=lastO.start;
				store.load({params:{start:start, limit:pageSize}});
			}
			function failure(rep){
				alert("加载失败！");
				alert(rep.responseText);
			}
		},
		add:function(){
			if(Ext.getDom("flowMetaId").value!='0'){
				App.showDlg();
			}else{
				Ext.Msg.alert("添加部署","请从左边选择一个可部署的工作流！");
			}
			
		},
		showDlg:function(){
			if(!dlg){
				dlg = new Ext.Window({
	                el:'dlg',
	                contentEl:'dlgContent',
					title: '新增工作流部署',
	                width:300,
	                height:190,
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
	                        dlg.hide();
	                    }
	                }]
            	});
	        }
	        dlg.show(this);
			Ext.get("flowDeployName").focus(true);
		},
		edit:function(){
			var select=sm.getSelected();
			if(select==null){
				 alert("请先选择一个工作流部署！");
				 return;
			}
			Ext.Ajax.request({
				url:'../flow/deploy/load.do',
				params:'flowDeployId='+select.get('flowDeployID'),
				success:success,
				failure:failure
			});
			function success(rep){				
				eval("var res="+rep.responseText);
				Ext.getDom("flowDeployID").value=res.flowDeployID;
				Ext.getDom("flowDeployName").value=res.flowDeployName;
				Ext.getDom("memo").value=res.memo;
				Ext.getDom("currentState").value=res.currentState;
				App.showDlg();
			}
			function failure(rep){
				System.infoDlg("系统出现异常",rep.responseText);
			}
		},
		saveInfo:function(){
			var flowMetaId=Ext.getDom("flowMetaId").value;
			var deployId=Ext.getDom("flowDeployID").value;
			var memo=Ext.getDom("memo").value;
			var deployName=Ext.getDom("flowDeployName").value;
			if(flowMetaId=='0'){
				Ext.Msg.alert("错误","请选择一个工作流部署文件！");
				return;
			}
			if(deployName==''){
				Ext.Msg.alert("错误","请输入一个部署名称！");
				return;
			}
			System.waitDlg("保存部署","正在保存工作流部署...");
			Ext.Ajax.request({
				form:'deployForm',
				url:'../flow/deploy/save.do',
				success:success,
				failure:failure
			});
			function success(rep){
				Ext.MessageBox.hide();
				eval("var ro="+rep.responseText);
				if(ro.success){
					Ext.Msg.alert("部署工作流","保存工作流部署成功！");
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					store.load({params:{start:start, limit:pageSize}});
					dlg.hide();
					Ext.getDom("flowDeployID").value='-1';
					Ext.getDom("flowDeployName").value='';
					Ext.getDom("memo").value='';
					Ext.getDom("currentState").value='preparing';
				}else{
					Ext.Msg.alert("部署工作流","保存工作流部署失败！");
				}
			}
			function failure(rep){
				Ext.Msg.alert("错误","保存工作流部署失败！");
				System.infoDlg("系统异常",rep.responseText);
			}
		},
		deleteInfo:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除工作流部署","请先选择一个工作流文件！");
				return;
			}
			if(!confirm("确定要删除选中的工作流部署吗？")){
				return;
			}
			System.waitDlg("删除工作流部署","正在删除工作流部署...");
			var url='../flow/deploy/delete.do?flowMetaId='+flowMetaId+'&'+App.getSelectedIds();
			Ext.Ajax.request({
				url:url,
				success:success,
				failure:failure
			});
			function success(rep){
				Ext.MessageBox.hide();
				eval("var o="+rep.responseText);
				if(o.result.length>0){
					var s="";
					for(var i=0;i<o.result.length;i++){
						if(s==""){
							s+=o.result[i].msg;
						}else{
							s+="<br>"+o.result[i].msg;
						}
					}
					System.infoDlg("删除部署",s);
					var totalCount= new Number(o.totalCount);;
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					if(start>0&&start>=totRecords){
						store.load({params:{start:start-pageSize, limit:pageSize}});
					}else{
						store.load({params:{start:start, limit:pageSize}});
					}
				}else{
					Ext.Msg.alert("删除部署","对不起，删除部署失败！");
				}
			}
			function failure(rep){
				Ext.Msg.alert("删除工作流部署","删除失败！");
				Ext.Msg.alert("删除工作流部署",rep.responseText);
			}
		},
		getSelectedIds:function(){
			var ids="";
			var selections=sm.getSelections();
			var size=selections.length;
			for(var i=0;i<size;i++){
				var r=selections[i];
				if(ids.length==0){
					ids="ids["+i+"]="+r.get("flowDeployID");
				}else{
					ids+="&ids["+i+"]="+r.get("flowDeployID");
				}
			}
			return ids;
			
		}
	};
}();
Ext.onReady(App.init);