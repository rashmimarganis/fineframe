var LogApp= function(){
	var viewport;
	var ope_edit=0;
	var ope_add=1;
	var operation=ope_add;
	var dlgOrg;
	var dlgUser;
	var store;
	var storeUser;
    var xg = Ext.grid;
	var grid;
	var gridUser;
	var sm;
	var smUser;
	var id;
	var name;
	var title;
	var url;
	var oldName;
	var form;
	var pageSize=18;
	var tree;
	var orgNode = null;
	var userNodes = null;
	return {
		init:function(){
			LogApp.initStore();
			LogApp.initGridPanel();
			LogApp.initLayout();
		}
		,
		getUserIdStr:function(){
			var userIdStr = "";
			if(userNodes&&userNodes.length>0){
				userIdStr = "(";
				for(var i=0;i<userNodes.length;i++){
					userIdStr += userNodes[i].get("id")+",";
				}
				userIdStr = userIdStr.substr(0,userIdStr.length-1)+ ")";
			}
			return userIdStr;
		}
		,
		initStore:function(){
			var orgId = 0;
			if(orgNode){
				orgId = orgNode.id;
			}
	
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'log/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'id',  'username', 
						'ip','time','operation',
						'orgTitle'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('time', 'desc');
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
	           id: 'id', 
	           header: "ID",
	           dataIndex: 'id',
	           width: 40
	        },{
	           id: 'username', 
	           header: "用户名",
	           dataIndex: 'username',
	           width: 100
	        },{
	           header: "IP地址",
	           dataIndex: 'ip',
	           width: 80
	        },{
	           header: "操作时间",
	           dataIndex: 'time',
	           width: 150,
			   renderer:renderDate
	        },{
	           header: "操作描述",
	           dataIndex: 'operation',
	           width: 150
	        },{
	           header: "登录组织",
	           dataIndex: 'orgTitle',
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
				  	text: '删除日志',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:LogApp.deleteInfo
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
				renderTo:'logGrid'
		    });
		    grid.render();
			
		},
		initLayout:function(){
			var northContent=new Ext.Panel({
				el:',logNorthTable',
				contentEl:'logNorthTable'
			});
			var north= new Ext.Panel({
		        collapsible:false,
		        el: 'logNorth',
				region:'north',
				contentEl:'logNorthTable',
				items:[northContent]
		    });
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'logCenter',
				region:'center',
				contentEl:'logGrid',
				items:[grid]
		    });
			
	     	var panel = new Ext.Panel({
	          layout:'border',
	          autoScroll:true,
	          items:[
	              north,center
	           ]
	        });
	     	 mainPanel.add(panel);
			 store.load({params:{start:0, limit:20}});
    	},
		deleteInfo : function(){
			if(sm.getSelected==null){
				alert("请先选择一条日志！");
				return;
			}
			if(!confirm("确定要删除选中的日志吗？")){
				return;
			}
			var url='log/delete.jhtm?ids='+LogApp.getSelectedIds();
		
			Ext.Ajax.request({
				url:url,
				success:success,
				failure:failure
			});
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
					alert("删除成功！");
				}else{
					alert("删除失败！");
				}
				
			}
			function failure(rep){
				alert("删除失败！");
				alert(rep.responseText);
			}
		},
		queryByOrg : function(){
		    if(!tree){
		   	 	var Tree = Ext.tree;
			    tree = new Tree.TreePanel({
			        el:'log_tree-div',
			        autoScroll:true,
			        animate:true,
			        enableDD:true,
			        containerScroll: true, 
			        loader: new Tree.TreeLoader({
			            dataUrl:'org/tree.jhtm'
			        })
			    });
			
				tree.getSelectionModel().on({
			        'selectionchange' : function(sm, node){
			         	orgNode=node;
			        },
			        scope:this
			    });
			    // set the root node
			    var root = new Tree.AsyncTreeNode({
			        text: '所有组织',
			        draggable:false,
			        id:'0'
			    });
			    tree.setRootNode(root);
			
			    // render the tree
			    tree.render();
			    root.expand();
		    }
		    
			if(!dlgOrg){
				dlgOrg = new Ext.Window({
	                el:'dlg',
	                layout:'fit',
					title: '按组织查询',
	                width:300,
	                height:300,
	                closeAction:'hide',
	                plain : true,
					items : tree,
					modal : true,
					bodyStyle : 'padding:5px;',
    				buttonAlign :'right',
	                buttons: [{
	                    text:'确定',
						handler:function(){
							userNodes = null;
							var orgId = 0;
							if(orgNode){
								orgId = orgNode.id;
							}
							store.proxy=new Ext.data.HttpProxy({
		            			url: 'log/find.jhtm?orgId='+orgId+'&userId='+LogApp.getUserIdStr()
		       				})
							var lastO= store.lastOptions.params;
							var start=lastO.start;
							store.load({params:{start:start, limit:pageSize}});
		                    dlgOrg.hide();
	                    }
	                },{
	                    text: '取消',
	                    handler: function(){
	                        dlgOrg.hide();
	                    }
	                }]
            	});
	        }
	        dlgOrg.show(this);
		}
		,
		queryByUser : function(){
		    LogApp.initGridPanelUser();
			if(!dlgUser){
				dlgUser = new Ext.Window({
	                el:'dlg2',
	                layout:'fit',
					title: '按用户查询',
	                width:300,
	                height:300,
	                closeAction:'hide',
	                plain : true,
					items : gridUser,
					bodyStyle : 'padding:5px;',
    				buttonAlign :'right',
	                buttons: [{
	                    text:'确定',
						handler:function(){
							LogApp.initUserNode();
							var orgId = 0;
							if(orgNode){
								orgId = orgNode.id;
							}
							store.proxy=new Ext.data.HttpProxy({
		            			url: 'log/find.jhtm?orgId='+orgId+'&userIdStr='+LogApp.getUserIdStr()
		       				})
							var lastO= store.lastOptions.params;
							var start=lastO.start;
							store.load({params:{start:start, limit:pageSize}});
		                    dlgUser.hide();
	                    }
	                },{
	                    text: '取消',
	                    handler: function(){
	                        dlgUser.hide();
	                    }
	                }],
					 bbar: new Ext.PagingToolbar({
			            pageSize: pageSize,
			            store: store,
			            displayInfo: true
			        })
            	});
	        }
	        dlgUser.show(this);
		}
		,
		initStoreUser:function(){
			if(!storeUser){
				storeUser = new Ext.data.Store({
			        proxy: new Ext.data.HttpProxy({
			            url: 'user/page.jhtm'
			        }),
			        reader: new Ext.data.JsonReader({
			            root: 'objs',
			            totalProperty: 'totalCount',
			            id: 'id',
			            fields: [
			                'id','username'
			            ]
			        }),
			        remoteSort: true
			    });
			    storeUser.setDefaultSort('id', 'asc');
				storeUser.on('load',function(s,r,o){
					if(s.getTotalCount()>0){
						sm.selectFirstRow();
					}
				});
			}
		},
		initGridPanelUser : function(){
			if(!gridUser){
				LogApp.initStoreUser();
				smUser = new xg.CheckboxSelectionModel();

				var cmUser = new Ext.grid.ColumnModel([smUser,{
		           id: 'id',
		           header: "ID",
		           dataIndex: 'id',
		           width: 60
		        },{
		           id: 'username', 
		           header: "用户名",
		           dataIndex: 'username',
		           width: 160
		        }]);
		
			    cmUser.defaultSortable = true;
				
			    gridUser = new Ext.grid.GridPanel({
					id:'gridUser',
			        store: storeUser,
			        cm: cmUser,
					sm: smUser,
			        trackMouseOver:true,
			        frame:false,
			        loadMask: true,
					margins:'0 0 0 0',
					viewConfig: {
			            forceFit:true
			        },
					renderTo:'gridUser'
			    });
			    gridUser.render();
				storeUser.load({params:{start:0,limit:pageSize}});
			}
		},
		initUserNode : function(){
			orgNode = null;
			userNodes = smUser.getSelections();
		},
		getSelectedIds:function(){
			var ids="";
			var selections=sm.getSelections();
			var size=selections.length;
			for(var i=0;i<size;i++){
					var r=selections[i];
					if(ids.length==0){
						ids=r.get("id");
					}else{
						ids+=","+r.get("id");
					}
			}
			return ids;
			
		}
	};
}();
LogApp.init();