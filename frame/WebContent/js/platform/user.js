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
		name : "obj.username"
	};
	this.oldNameField = {
			xtype : 'hidden',
			fieldLabel : "旧帐户",
			allowBlank : false,
			name : "obj.oldName"
		};
	this.passwordField = {
		xtype : 'textfield',
		fieldLabel : "密码",
		inputType:'password',
		name : "obj.password"
	};
	this.repasswordField = {
		xtype : 'textfield',
		fieldLabel : "重复密码",
		inputType:'password',
		name : "obj.repassword"
	};
	this.enabledField = {
		xtype : 'checkbox',
		fieldLabel : "可用",
		name : "obj.enabled",
		inputValue:'true'
	};
	this.lockedField = {
		xtype : 'checkbox',
		fieldLabel : "锁定",
		name : "obj.locked",
		inputValue:'true'
	};
	this.concurrentMaxField = {
		xtype : 'numberfield',
		fieldLabel : "并发数",
		allowBlank : false,
		name : "obj.concurrentMax",
		value:'-1'
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
	this.personField = {
        xtype:'persontree',
        fieldLabel:'关联人员',
        name:'obj.person.personId',
        allowUnLeafClick:false,
        treeHeight:200,
        url:'person/tree.jhtm',
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
		    {name:'obj.username',mapping:'username'}, 
		    {name:'obj.oldName',mapping:'username'}, 
		    {name:'obj.enabled',mapping:'enabled'},
		    {name:'obj.locked',mapping:'locked'},
		    {name:'obj.concurrentMax',mapping:'concurrentMax'},
		    {name:'obj.org.orgId',mapping:'orgId'},
		    {name:'obj.person.personId',mapping:'personId'},
		    {name:'obj.sequence',mapping:'sequence'}
		    ]
		),
		items : [this.idField,this.nameField,this.oldNameField,this.passwordField,this.repasswordField,this.personField ,this.orgField,this.enabledField,this.lockedField,this.concurrentMaxField,this.sortField]
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
					_form.findField("obj.person.personId").setFieldValue(o.data[0].personId,o.data[0].realname);
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
	}
});

UserGridPanel=function(){
	this.orgId=0;
	this.window=new UserWindow();
	this.window.gridPanel=this;
	var _win=this.window;
	var _grid=this;
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
							text : '停用帐户',
							iconCls : 'x-btn-text-icon disabled',
							scope : this,
							handler : _grid.disableInfo
						},'-',{
							text : '启用帐户',
							iconCls : 'x-btn-text-icon enabled',
							scope : this,
							handler : _grid.enableInfo
						},'-',{
							text : '锁定帐户',
							iconCls : 'x-btn-text-icon lock',
							scope : this,
							handler : _grid.lockInfo
						},'-',{
							text : '解锁帐户',
							iconCls : 'x-btn-text-icon unlock',
							scope : this,
							handler : _grid.unlockInfo
						}
				],
				renderTo : 'userGridPanel'				
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
		form.findField("obj.person.personId").setFieldValue(0,'');
		form.findField("obj.username").setValue("");
		form.findField("obj.locked").setValue(false);
		form.findField("obj.concurrentMax").setValue(0);
		form.findField("obj.enabled").setValue(false);
		form.findField("obj.sequence").setValue(0);
		form.findField("obj.username").focus(false,true);
		
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
lockInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("锁定用户",'请选择要锁定的帐户！');
			return;
		}
		
		if(!confirm("确定要锁定选中的帐户吗？")){
			return;
		}
		var url='user/lock.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("锁定用户","锁定用户成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("锁定用户",rep.repsonseText);
		}
	},
unlockInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("解锁用户",'请选择要解锁的帐户！');
			return;
		}
		
		if(!confirm("确定要解锁选中的帐户吗？")){
			return;
		}
		var url='user/unlock.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("解锁帐户","解锁帐户成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("解锁帐户",rep.repsonseText);
		}
	},
enableInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("启用用户",'请选择帐户！');
			return;
		}
		
		if(!confirm("确定要启用选定的帐户吗？")){
			return;
		}
		var url='user/enable.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("启用用户","启用帐户成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("启用用户",rep.repsonseText);
		}
	},
disableInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("停用用户",'请选择至少一个帐户！');
			return;
		}
		
		if(!confirm("确定要停用选定的帐户吗？")){
			return;
		}
		var url='user/disable.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("停用用户","停用用户成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("停用用户",rep.repsonseText);
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
			this.userRoleWindow.show();
			this.userRoleWindow.loadData(selection.get("userId"));
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
		height : 380,
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