var westPanel;
var centerPanel;

RoleGridPanel=function(){
	this.orgId=0;
	this.roleFunctionWindow=new RoleFunctionWindow();
	var _grid=this;
	var _roleFunctionWindow=this.roleFunctionWindow;
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'role/list.jhtm?orgId='+_grid.orgId
        }),
        reader: new Ext.data.JsonReader({
            root: 'objs',
            totalProperty: 'totalCount',
            id: 'roleId',
            fields: [
                'roleId','roleName', 'title','orgName','sequence'
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
        id: 'roleId',
        header: "ID",
        dataIndex: 'roleId',
        width: 20
     },{
        id: 'roleName', 
        header: "编号",
        dataIndex: 'roleName',
        width: 100
     },{
        header: "名称",
        dataIndex: 'title',
        width: 100
     },{
         header: "排序",
         dataIndex: 'sequence',
         width: 100
      },{
		header:"所属组织",
		dataIndex:'orgName',
		width:100
	}]);

    this.cm.defaultSortable = true;
    RoleGridPanel.superclass.constructor.call(
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
					pageSize : 10,
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
				renderTo : 'roleGridPanel'
			});
    
};
Ext.extend(RoleGridPanel, Ext.grid.GridPanel, {
	loadData:function(orgId){
		this.store.proxy=new Ext.data.HttpProxy({
            url: 'role/list.jhtm?orgId='+orgId
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
					ids='ids='+r.get("roleId");
				}else{
					ids+="&ids="+r.get("roleId");
				}
		}
		return ids;
		
	},
	loadFunctions:function(){
		var selection=this.getSelectionModel().getSelected();
		if(selection){
			this.roleFunctionWindow.show();
			this.roleFunctionWindow.loadData(selection.get("roleId"));
		}else{
			Ext.Msg.alert("显示角色","请选择一个角色！");
		}
	}
});


RoleFunctionTree=function(){
	var _tree=this;
	this.roleId=0;
	this.loader= new Ext.tree.TreeLoader({
        dataUrl:'role/functions.jhtm?id='+_tree.roleId
       
    });
	var root = new Ext.tree.AsyncTreeNode({
        text: '全部功能',
        draggable:false,
        id:'0'
    });
	RoleFunctionTree.superclass.constructor.call(this, {
		layout:'fit',
        el:'roleFunctionTree',
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
		/*var parent=node.parentNode;
		if(parent){
			if(checked){
				if(!parent.attributes.checked){
					parent.ui.toggleCheck(checked); 
					parent.attributes.checked = checked; 
					//parent.fireEvent('checkchange', parent, checked); 
				}
			}
		}*/
		node.eachChild(function(child) {   
			child.ui.toggleCheck(checked);   
			child.attributes.checked = checked;   
			child.fireEvent('checkchange', child, checked);   
		});   
		
	}, this);  

};
Ext.extend(RoleFunctionTree,Ext.tree.TreePanel,{
	onBeforeClick: function(node,e){
		node.getUI().toggleCheck(true);
	},
	reload:function(rId){
		this.roleId=rId;
		var roleId=this.roleId;
		var _tree=this;
		
			this.loader=new Ext.tree.TreeLoader({
				dataUrl:'role/functions.jhtm?id='+_tree.roleId
				
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

RoleFunctionWindow=function(){
	this.gridPanel;
	this.treePanel=new RoleFunctionTree();
	var _tree=this.treePanel;
	var _win=this;
	var _gridPanel=this.gridPanel;
	RoleFunctionWindow.superclass.constructor.call(this, {
		title : '功能结构树',
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
				var roleId=_tree.roleId;
				var fids=_tree.getSelections();
				if(fids==''){
					Ext.Msg.alert("保存角色","请至少选择一个功能！");
					return;
				}
				var url='role/saveFunctions.jhtm?id='+roleId+'&fids='+fids;
				Ext.Ajax.request({
					url:url,
					success:success,
					failure:failure
				});
				function success(rep){
					eval("var result="+rep.responseText);
					if(result.success){
						Ext.Msg.alert("保存","角色关联功能配置成功！");
					}else{
						Ext.Msg.alert("保存","角色关联功能配置失败！");
					}
				}
				function failure(rep){
					Ext.Msg.alert("保存角色",rep.repsonseText);
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

Ext.extend(RoleFunctionWindow, Ext.Window, {
	loadData : function(id) {
		this.treePanel.reload(id);
	}
});

var RoleRightApp= function(){
	
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var gridPanel=new RoleGridPanel();
	return {
		init:function(){
		    RoleRightApp.initTree();
			RoleRightApp.initLayout();
			
		},
		initLayout:function(){
		    
		     var treePanel=new Ext.Panel({
		    	 layout:'fit',
		    	 el:'roleOrgPanel',
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
                   title:'角色列表',
                   el:'rolePanel',
                   contentEl:'roleGridPanel',
                   border:true,
                   width:300,
                   split:true,
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
				el:'roleOrgTree',
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
		getSelectedNode:function(){
			var node=westPanel.getSelectionModel().getSelectedNode();
			return node;
		}
		
		
	};
}();
RoleRightApp.init();