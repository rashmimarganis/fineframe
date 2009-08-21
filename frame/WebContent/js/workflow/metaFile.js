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
	return {
		init:function(){
			//Ext.QuickTips.init();
			//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			App.initLayout();
		    setTimeout(function(){
		        Ext.get('loading').remove();
		        Ext.get('loading-mask').fadeOut({remove:true});
		        Ext.get('businessType').show();
		    }, 250);
		}
		,
		initType:function(){
			Ext.Ajax.request({
				url:'../flow/type/all.do',
				params:'r='+Math.random,
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var res="+rep.responseText);
				var type=Ext.getDom("businessType");
				for(var i=1,len=res.length;i<=len;i++){
					var obj=res[i-1];
					type.options[i-1]=new Option(obj.name,obj.id);
				}
				type.options[0].selected=true;
			}
			function failure(rep){
				alert("加载失败！");
				alert(rep.responseText);
			}
		}
		,
		initLayout:function(){
			App.initType();
			App.initGridPanel();
			App.initTree();
			var west=new Ext.Panel(
			{
				el:'west',
				contentEl:'tree',
				region:'west',
				title:'流程类别',
				layout:'fit',
				split:true,
				border:true,
				width:220,
				margins:'0 0 0 0',
				items:westPanel
		    });
		    var centerPanel=new Ext.Panel(
			{
				el:'center',
				contentEl:'grid',
			  	region:'center',
			  	border:true,
			  	split:true,
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
		            url: '../flow/meta/page.do'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'flowFileID', 'flowMetaName','createdTime','typeName'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('flowFileID', 'asc');
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
		        enableDD:false,
				border:false,
				animate:true,
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
		        containerScroll: true, 
		        loader: new Ext.tree.TreeLoader({
		            dataUrl:'../flow/type/tree.do'
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
					if (node.id != '0') {
						store.proxy = new Ext.data.HttpProxy({
							url: '../flow/meta/page.do?typeId=' + node.id
						});
						typeId=node.id;
						store.load({params:{start:0, limit:pageSize}});
					}
					
		        },
		        scope:this
		    });
		    var root = new Ext.tree.AsyncTreeNode({
		        text: '所有类别',
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
	           dataIndex:  'flowFileID',
	           width: 20
	        },{
	           header: "名称",
	           dataIndex: 'flowMetaName',
	           width: 80
	        },{
	           header: "所属类型",
	           dataIndex:  'flowMetaName',
	           width: 150,
			   sort:false
	        },{
	           header: "创建时间",
	           dataIndex: 'createdTime',
	           width: 150
	        }]);
	
		    cm.defaultSortable = true;
			
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        border:false,
		        loadMask: true,
				tbar:[{
				  	text: '新增文件',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.add
				  },'-',{
				  	text: '更换图片',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.edit
				  },'-',{
				  	text: '更换类型',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.edit
				  },'-',{
				  	text: '删除工作流文件',
		            iconCls: 'x-btn-text-icon delete',
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
		    grid.render();
		},
		add:function(){
			App.showDlg();
		}
		,
		showDlg:function(){
			if(!dlg){
				dlg = new Ext.Window({
	                el:'dlg',
	                contentEl:'dlgContent',
					title: '工作流文件信息',
	                width:350,
	                height:200,
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
		}
		,
		edit:function(){
			var select=sm.getSelected();
			if(select==null){
				 alert("请先选择一个工作流文件！");
				 return;
			}
			Ext.Ajax.request({
				url:'../flow/meta/load.do',
				params:'obj.id='+select.get('id'),
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var res="+rep.responseText);
				id.setValue(res.id);
				name.setValue(res.name);
				oldName.setValue(res.name);
				url.setValue(res.url);
				functionId.setValue(res.func.id);
				App.showDlg();
			}
			function failure(rep){
				alert("加载失败！");
				alert(rep.responseText);
			}
		},
		saveInfo:function(){
			
			Ext.Ajax.request({
				form:'uploadForm',
				url:'../flow/meta/upload.do',
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var ss="+rep.responseText);
				if(ss.success){
					if(operation==ope_edit){
						var select=sm.getSelected();
						select.set('name',name.getValue());
						select.set('url',url.getValue());
						dlg.hide();
						select.commit();
					}else{
						var lastO=store.lastOptions.params;
						var start=lastO.start;
						dlg.hide();
						store.load({params:{start:start, limit:pageSize}});
					}
					Ext.Msg.alert("保存工作流文件","保存成功！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				}else{
					var exist=ss.exist;
					if(exist){
						Ext.Msg.alert("保存工作流文件","编号已经存在，请重新填写！");
						name.focus();
						return;
					}
				}
				
				
				
			}
			function failure(rep){
				
			}
		},
		deleteInfo:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除工作流文件","请先选择一个工作流文件！");
				return;
			}
			if(!confirm("确定要删除选中的工作流文件吗？")){
				return;
			}
			var url='../flow/meta/delete.do?ids='+App.getSelectedIds();
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
					Ext.Msg.alert("删除工作流文件","删除成功！");
				}else{
					Ext.Msg.alert("删除工作流文件","删除失败！");
				}
				
			}
			function failure(rep){
				Ext.Msg.alert("删除工作流文件","删除失败！");
				Ext.Msg.alert("删除工作流文件",rep.responseText);
			}
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
Ext.onReady(App.init);