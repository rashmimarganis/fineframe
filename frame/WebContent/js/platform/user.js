var westPanel;
var centerPanel;

var UserFormPanel = function() {
	var _formPanel=this;
	this.level=1;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.userId",
		readOnly:true
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "帐户",
		allowBlank : false,
		name : "obj.userName"
	};
	
	this.titleField = {
			xtype : 'textfield',
			fieldLabel : "中文名称",
			allowBlank : false,
			name : "obj.title"
		};
	this.orgField = {
            xtype:'combotree',
            fieldLabel:'所属组织',
            name:'obj.org.orgId',
            allowUnLeafClick:true,
            treeHeight:200,
            url:'org/tree.jhtm',
            onSelect:function(id){
            }
	};
	this.sortField = {
		xtype : 'numberfield',
		fieldLabel : "排序",
		allowBlank : false,
		name : "obj.sequence"
	};
    
	UserFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:230,
		labelWidth:60,
        height:200,
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'userId'
		}, [
		    {name:'obj.userId', mapping:'userId'},
		    {name:'obj.userName',mapping:'userName'}, 
		    {name:'obj.title',mapping:'title'},
		    {name:'obj.org.orgId',mapping:'orgId'},
		    {name:'obj.sequence',mapping:'sequence'}
		    ]
		),
		items : [this.idField,this.nameField,this.titleField,this.orgField,this.sortField]
	});
};
Ext.extend(UserFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'user/load.jhtm?id=' + id;
		var _form=this.getForm();
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			success:function(form, action){
				var json = action.response.responseText;
				var o = eval("(" + json + ")");
				if(o.success){
					_form.findField("obj.org.orgId").setFieldValue(o.data[0].orgId,o.data[0].orgName);
				}
			},
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
	},
	save:function(){
		var _form=this.getForm();
    	if (_form.isValid()) {
			_form.submit( {
				waitMsg : '正在保存数据...',
				url : 'user/save.jhtm',
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
					var json = action.response.responseText;
					var o = eval("(" + json + ")");
					if(o.success){
						form1.findField("obj.userId").setValue(o.id);
						Ext.Msg.alert("保存用户","用户保存成功");
					}else{
						Ext.Msg.alert("保存用户","用户保存失败！");
					}

				}
			});
		}else{
			Ext.Masg.alert('验证信息','请把用户信息填写正确！');
		}
	}
});

UserGridPanel=function(){
	this.orgId=0;
	this.userFunctionWindow=new UserFunctionWindow();
	this.window=new UserWindow();
	this.window.gridPanel=this;
	var _win=this.window;
	var _grid=this;
	var _userFunctionWindow=this.userFunctionWindow;
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
                'userId','userName', 'title','orgName','sequence'
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
        id: 'userName', 
        header: "编号",
        dataIndex: 'userName',
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
					pageSize : 10,
					store : _grid.store,
					displayInfo : true
				}),
				tbar : [
					
					{
							text : '添加用户',
							iconCls : 'x-btn-text-icon add',
							scope : this,
							handler : _grid.addInfo
						}, '-', {
							text : '修改用户',
							iconCls : 'x-btn-text-icon edit',
							scope : this,
							handler : _grid.loadInfo
						}, '-', {
							text : '删除用户',
							iconCls : 'x-btn-text-icon delete',
							scope : this,
							handler : _grid.deleteInfo
						},'-',{
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
	addInfo:function(){
		this.window.show();
		var form=this.window.formPanel.getForm();
		var node=westPanel.getSelectionModel().getSelectedNode();
		
		form.findField("obj.userId").setValue(0);
		if(node){
			form.findField("obj.org.orgId").setFieldValue(node.id,node.text);
		}
		form.findField("obj.title").setValue("");
		form.findField("obj.userName").setValue("");
		form.findField("obj.sequence").setValue(0);
		form.findField("obj.userName").focus(false,true);
		
	},
	loadInfo:function(){
		var select=this.getSelectionModel().getSelected();
		if(select){
			this.window.show();
			this.window.loadData(select.get("userId"));
		}else{
			Ext.Msg.alert("显示用户","请选择一个用户！");
		}
	},
	deleteInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("删除用户",'请选择用户！');
			return;
		}
		
		if(!confirm("确定要删除该用户吗？")){
			return;
		}
		var url='user/delete.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("删除用户","删除成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("删除用户",rep.repsonseText);
		}
	},
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
			this.userFunctionWindow.show();
			this.userFunctionWindow.loadData(selection.get("userId"));
		}else{
			Ext.Msg.alert("显示用户","请选择一个用户！");
		}
	}
});

UserWindow=function(){
	this.gridPanel;
	this.formPanel=new UserFormPanel();
	this.form=this.formPanel.getForm();
	var _form=this.form;
	var _formPanel=this.formPanel;
	var _win=this;
	var _gridPanel=this.gridPanel;
	UserWindow.superclass.constructor.call(this, {
		title : '用户信息',
		width : 420,
		height : 280,
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
						url : 'user/save.jhtm',
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
							var orgId=_form.findField("obj.org.orgId").getValue();
							_win.gridPanel.reloadData(orgId);

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

Ext.extend(UserWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});

UserFunctionTree=function(){
	var _tree=this;
	this.userId=0;
	this.loader= new Ext.tree.TreeLoader({
        dataUrl:'user/functions.jhtm?id='+_tree.userId
       
    });
	var root = new Ext.tree.AsyncTreeNode({
        text: '全部功能',
        draggable:false,
        id:'0'
    });
	UserFunctionTree.superclass.constructor.call(this, {
		layout:'fit',
        el:'userFunctionTree',
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
Ext.extend(UserFunctionTree,Ext.tree.TreePanel,{
	onBeforeClick: function(node,e){
		node.getUI().toggleCheck(true);
	},
	reload:function(rId){
		this.userId=rId;
		var userId=this.userId;
		var _tree=this;
		
			this.loader=new Ext.tree.TreeLoader({
				dataUrl:'user/functions.jhtm?id='+_tree.userId
				
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

UserFunctionWindow=function(){
	this.gridPanel;
	this.treePanel=new UserFunctionTree();
	var _tree=this.treePanel;
	var _win=this;
	var _gridPanel=this.gridPanel;
	UserFunctionWindow.superclass.constructor.call(this, {
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
				var userId=_tree.userId;
				var fids=_tree.getSelections();
				if(fids==''){
					Ext.Msg.alert("保存用户","请至少选择一个功能！");
					return;
				}
				var url='user/saveFunctions.jhtm?id='+userId+'&fids='+fids;
				Ext.Ajax.request({
					url:url,
					success:success,
					failure:failure
				});
				function success(rep){
					eval("var result="+rep.responseText);
					if(result.success){
						Ext.Msg.alert("保存","用户关联功能配置成功！");
					}else{
						Ext.Msg.alert("保存","用户关联功能配置失败！");
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

Ext.extend(UserFunctionWindow, Ext.Window, {
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