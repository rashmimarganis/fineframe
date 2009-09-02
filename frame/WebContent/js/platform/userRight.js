var westPanel;
var centerPanel;


UserGridPanel=function(){
	this.orgId=0;
	this.userRoleWindow=new UserRoleWindow();
	var _grid=this;
	var _userRoleWindow=this.userRoleWindow;
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'user/list.jhtm?orgId='+_grid.orgId
        }),
        reader: new Ext.data.JsonReader({
            root: 'objs',
            totalProperty: 'totalCount',
            id: 'userId',
            fields: [
                'userId','username', 'title','realname','enabled','locked','concurrentMax','online','orgName','sequence'
            ]
        }
        ),
        remoteSort: true
	});
    this.store.setDefaultSort('sequence', 'asc');
	this.store.on('load',function(s,r,o){
		if(s.getTotalCount()>0){
			sm.selectFirstRow();
		}
	});
	
	
	this.cm = new Ext.grid.ColumnModel([sm,{
        id: 'userId',
        header: "ID",
        dataIndex: 'userId',
        width: 20
     },{
        id: 'username', 
        header: "帐号",
        dataIndex: 'username',
        width: 100
     },{
        header: "姓名",
        dataIndex: 'realname',
        width: 100,
        sortable:false
     },{
         header: "可用",
         dataIndex: 'enabled',
         width: 100,
         renderer:rendererEnabled
      },{
          header: "锁定",
          dataIndex: 'locked',
          width: 100,
          renderer:rendererEnabled
       },{
		header:"在线",
		dataIndex:'online',
		width:100,
		 renderer:rendererEnabled
	}]);

    this.cm.defaultSortable = true;
    UserGridPanel.superclass.constructor.call(
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
							text: '分配权限',
				            iconCls: 'x-btn-text-icon menu-config',
				            scope: this,
							handler:function(){
								_grid.loadFunctions();
							}
						} 
				],
				renderTo : 'userGridPanel',
				onRowdbclick:function(){
					//this.attrWindow.loadInfo(sm.getSelected('attributeId'));
				}
				
			});
    
};
Ext.extend(UserGridPanel, Ext.grid.GridPanel, {
	loadData:function(orgId){
		this.store.proxy=new Ext.data.HttpProxy({
            url: 'user/list.jhtm?orgId='+orgId
        });
		this.store.load({params:{start:0,limit:18}});
	}
	,
	reloadData:function(orgId){
		this.orgId=orgId;
		this.store.reload();
	},
	getSelectedIds:function(){
		var ids="";
		var selections=this.getSelectionModel().getSelections();
		var size=selections.length;
		for(var i=0;i<size;i++){
				var r=selections[i];
				if(ids.length==0){
					ids='ids='+r.get("userId");
				}else{
					ids+="&ids="+r.get("userId");
				}
		}
		return ids;
		
	},
	loadFunctions:function(){
		var selection=this.getSelectionModel().getSelected();
		if(selection){
			this.userRoleWindow.show();
			this.userRoleWindow.loadData(selection.get("userId"));
		}else{
			Ext.Msg.alert("显示用户","请选择一个用户！");
		}
	}
});


UserRoleTree=function(){
	var _tree=this;
	this.userId=0;
	this.loader= new Ext.tree.TreeLoader({
        dataUrl:'user/roles.jhtm?id='+_tree.userId
       
    });
	var root = new Ext.tree.AsyncTreeNode({
        text: '全部角色',
        draggable:false,
        id:'0'
    });
	UserRoleTree.superclass.constructor.call(this, {
		layout:'fit',
        el:'userRoleTree',
        autoScroll:true,
        animate:false,
        enableDD:false,
        draggable:false,
		split:true,
		border:true,
		width:465,
		minSize: 180,
		maxSize: 250,
		rootVisible:true,
		lines:true,
        containerScroll: true,
        loader:_tree.loader,
        root:root
	});
	this.on('checkchange', function(node, checked) {   
		node.expand();   
		node.attributes.checked = checked;   
		node.eachChild(function(child) {   
			child.ui.toggleCheck(checked);   
			child.attributes.checked = checked;   
			child.fireEvent('checkchange', child, checked);   
		});   
		
	}, this);  

};
Ext.extend(UserRoleTree,Ext.tree.TreePanel,{
	onBeforeClick: function(node,e){
		node.getUI().toggleCheck(true);
	},
	reload:function(rId){
		this.userId=rId;
		var userId=this.userId;
		var _tree=this;
		
			this.loader=new Ext.tree.TreeLoader({
				dataUrl:'user/roles.jhtm?id='+_tree.userId
				
	        });
	
		
		var root=this.getRootNode();
		root.reload(function(){
			_tree.expandAll();
		});
	},
	getSelections:function(){
		var root=this.getRootNode();
		if(root.hasChildNodes()){
			return this.getChecked("id");
		}else{
			return '';
		}
	}
});

UserRoleWindow=function(){
	this.gridPanel;
	this.treePanel=new UserRoleTree();
	var _tree=this.treePanel;
	var _win=this;
	var _gridPanel=this.gridPanel;
	UserRoleWindow.superclass.constructor.call(this, {
		title : '角色',
		width:350,
		height:480,
		resizable : true,
		plain : false,
		border : false,
		modal : true,
		autoScroll : true,
		layout : 'fit',
		closeAction : 'hide',
		items : this.treePanel,
		buttons : [ {
			text : '保存',
			handler : function() {
				var userId=_tree.userId;
				var fids=_tree.getSelections();
				
				var url='user/saveRoles.jhtm?id='+userId+'&rids='+fids;
				Ext.Ajax.request({
					url:url,
					success:success,
					failure:failure
				});
				function success(rep){
					eval("var result="+rep.responseText);
					if(result.success){
						Ext.Msg.alert("保存","用户分配角色成功！");
					}else{
						Ext.Msg.alert("保存","用户分配角色失败！");
					}
				}
				function failure(rep){
					Ext.Msg.alert("保存用户",rep.repsonseText);
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

Ext.extend(UserRoleWindow, Ext.Window, {
	loadData : function(id) {
		this.treePanel.reload(id);
	}
});

var UserApp= function(){
	
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var gridPanel=new UserGridPanel();
	return {
		init:function(){
		    UserApp.initTree();
			UserApp.initLayout();
			
		},
		initLayout:function(){
		    
		     var treePanel=new Ext.Panel({
		    	 layout:'fit',
		    	 el:'userOrgPanel',
		    	 region:'west',
		    	 width:200,
		    	 collapsible:true,
		    	 split:true,
		    	 items:[westPanel]
		     });
		     
		     centerPanel=new Ext.Panel(
 	     	{
                   region:'center',
 				  split:true,
 				  layout:'fit',
 				  collapsible:false,
                   title:'用户列表',
                   el:'userPanel',
                   contentEl:'userGridPanel',
                   border:true,
                   width:300,
                   split:true,
                   autoScroll:true,
                   items:[gridPanel]
              	}
 		     );
		     var panel=new Ext.Panel({
		    	 layout:'border',
		    	 items:[
			              treePanel,centerPanel
				           ]
		     });
		     FineCmsMain.addFunctionPanel(panel);
    	},
		initTree:function(){
		    westPanel = new Ext.tree.TreePanel({
				el:'userOrgTree',
		        autoScroll:true,
		        animate:true,
		        enableDD:false,
				split:true,
				border:true,
				title:'组织结构树',
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:false,
				containerScroll: true, 
				margins: '0 0 0 0',
		        loader: new Ext.tree.TreeLoader({
		            dataUrl:'org/tree.jhtm'
		        })
		    });
			westPanel.getSelectionModel().on({
		        'beforeselect' : function(sm, node){
					selectNode=node;
					if(node.id!='0'){
						if(!node.expanded){
							node.expand();
						};
						gridPanel.loadData(node.id);
					}
		        
		        },
				'selectionchange' : function(sm, node){
		        	/*
		        	if(node.id!=0){
		        		formPanel.loadData(node.id);
		        	}
		        	*/
		        },
		        scope:this
		    });
		    var root = new Ext.tree.AsyncTreeNode({
		        text: '组织结构',
		        draggable:false,
		        id:'0'
		    });
		    westPanel.setRootNode(root);
			
			westPanel.render();
			root.select();
			root.expand();
			selectNode=root;
			
		},
		deleteInfo:function(){
			
		},
		getSelectedNode:function(){
			var node=westPanel.getSelectionModel().getSelectedNode();
			return node;
		}
		
		
	};
}();
UserApp.init();