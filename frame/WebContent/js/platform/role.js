var westPanel;
var centerPanel;

var RoleFormPanel = function() {
	var _formPanel=this;
	this.level=1;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.roleId",
		readOnly:true
	};
	
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "英文名称",
		allowBlank : false,
		name : "obj.roleName"
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
    
	RoleFormPanel.superclass.constructor.call(this, {
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
			id : 'roleId'
		}, [
		    {name:'obj.roleId', mapping:'roleId'},
		    {name:'obj.roleName',mapping:'roleName'}, 
		    {name:'obj.title',mapping:'title'},
		    {name:'obj.org.orgId',mapping:'orgId'},
		    {name:'obj.sequence',mapping:'sequence'}
		    ]
		),
		items : [this.idField,this.nameField,this.titleField,this.orgField,this.sortField]
	});
};
Ext.extend(RoleFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'role/load.jhtm?id=' + id;
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
				url : 'role/save.jhtm',
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
						form1.findField("obj.roleId").setValue(o.id);
						Ext.Msg.alert("保存角色","角色保存成功");
					}else{
						Ext.Msg.alert("保存角色","角色保存失败！");
					}

				}
			});
		}else{
			Ext.Masg.alert('验证信息','请把角色信息填写正确！');
		}
	}
});

RoleGridPanel=function(){
	this.orgId=0;
	this.window=new RoleWindow();
	this.window.gridPanel=this;
	var _win=this.window;
	var _grid=this;
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
							text : '添加角色',
							iconCls : 'x-btn-text-icon add',
							scope : this,
							handler : _grid.addInfo
						}, '-', {
							text : '修改角色',
							iconCls : 'x-btn-text-icon edit',
							scope : this,
							handler : _grid.loadInfo
						}, '-', {
							text : '删除角色',
							iconCls : 'x-btn-text-icon delete',
							scope : this,
							handler : _grid.deleteInfo
						},'-',{
							text: '分配权限',
				            iconCls: 'x-btn-text-icon menu-config',
				            scope: this,
							handler:function(){}
						} 
				],
				renderTo : 'roleGridPanel',
				onRowdbclick:function(){
					//this.attrWindow.loadInfo(sm.getSelected('attributeId'));
				}
				
			});
    
};
Ext.extend(RoleGridPanel, Ext.grid.GridPanel, {
	addInfo:function(){
		this.window.show();
		var form=this.window.formPanel.getForm();
		var node=westPanel.getSelectionModel().getSelectedNode();
		
		form.findField("obj.roleId").setValue(0);
		if(node){
			form.findField("obj.org.orgId").setFieldValue(node.id,node.text);
		}
		form.findField("obj.title").setValue("");
		form.findField("obj.roleName").setValue("");
		form.findField("obj.sequence").setValue(0);
		form.findField("obj.roleName").focus(false,true);
		
	},
	loadInfo:function(){
		var select=this.getSelectionModel().getSelected();
		if(select){
			this.window.show();
			this.window.loadData(select.get("roleId"));
		}else{
			Ext.Msg.alert("显示角色","请选择一个角色！");
		}
	},
	deleteInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("删除角色",'请选择角色！');
			return;
		}
		
		if(!confirm("确定要删除该角色吗？")){
			return;
		}
		var url='role/delete.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("删除角色","删除成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("删除角色",rep.repsonseText);
		}
	},
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
		
	}
});

RoleWindow=function(){
	this.gridPanel;
	this.formPanel=new RoleFormPanel();
	this.form=this.formPanel.getForm();
	var _form=this.form;
	var _formPanel=this.formPanel;
	var _win=this;
	var _gridPanel=this.gridPanel;
	RoleWindow.superclass.constructor.call(this, {
		title : '角色信息',
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
						url : 'role/save.jhtm',
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

Ext.extend(RoleWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});

var RoleApp= function(){
	
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var gridPanel=new RoleGridPanel();
	return {
		init:function(){
		    RoleApp.initTree();
			RoleApp.initLayout();
			
		},
		initLayout:function(){
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
                  split:true,
                  autoScroll:true,
                  items:[gridPanel]
             	}
		     );
		     var treePanel=new Ext.Panel({
		    	 layout:'fit',
		    	 el:'roleOrgPanel',
		    	 region:'west',
		    	 width:200,
		    	 collapsible:true,
		    	 split:true,
		    	 items:[westPanel]
		     });
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
		deleteInfo:function(){
			
		},
		getSelectedNode:function(){
			var node=westPanel.getSelectionModel().getSelectedNode();
			return node;
		}
		
		
	};
}();
RoleApp.init();