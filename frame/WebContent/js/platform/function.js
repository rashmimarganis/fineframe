var westPanel;
var centerPanel;

var FunctionFormPanel = function() {
	var _formPanel=this;
	this.level=1;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.functionId",
		readOnly:true
	};
	this.parentId={
		xtype : 'hidden',
		fieldLabel : "上级编号",
		name : "obj.parent.functionId"
	};
	this.parentName={
		xtype : 'textfield',
		fieldLabel : "上级功能",
		name : "parentName",
		readOnly:true
	};

	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "名称",
		allowBlank : false,
		name : "obj.functionName"
	};
	this.urlField = {
			xtype : 'textfield',
			fieldLabel : "URL",
			allowBlank : false,
			width:'300',
			name : "obj.url"
		};
	this.sortField = {
		xtype : 'numberfield',
		fieldLabel : "排序",
		allowBlank : false,
		name : "obj.sequence"
	};

	
    this.menuField = {
    	xtype:'checkbox',
    	autoShow:true,
        width: 520,
        boxLabel:'是',
        inputValue:'true',
        fieldLabel: '菜单',
        name:'obj.menu'
    };
    
    this.logField = {
    	xtype:'checkbox',
    	autoShow:true,
        width: 520,
        inputValue:'true',
        boxLabel:'是',
        fieldLabel: '记录',
        name:'obj.log'
    };

    
    this.saveBtn=new Ext.Button({
    	text:'保存功能',
    	handler:function(){
    		var _form=_formPanel.getForm();
	    	if (_form.isValid()) {
				_form.submit( {
					waitMsg : '正在保存数据...',
					url : 'function/save.jhtm',
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
							var node=westPanel.getSelectionModel().getSelectedNode();
								if(o.action=='update'){
									node.setText(_form.findField("obj.functionName").getValue());
								}else{
									var id=o.id;
									_formPanel.getForm().findField("obj.functionId").setValue(id);
									var text=_formPanel.getForm().findField("obj.functionName").getValue();
									var newNode = new Ext.tree.TreeNode({id:id,text:text,leaf:true});
									if(_formPanel.level==0){
										node.appendChild(newNode);
									}else{
										node.parentNode.appendChild(newNode);
									}
									newNode.select();
								}
						
						}
	
					}
				});
			}else{
				Ext.Masg.alert('验证信息','请把功能信息填写正确！');
			}
    	}
    });
	
	FunctionFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:300,
        height:300,
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'functionId'
		}, [
		    {name:'obj.functionId', mapping:'functionId'},
		    {name:'obj.functionName',mapping:'functionName'}, 
		    {name:'obj.menu',mapping:'isMenu'},
		    {name:'obj.sequence',mapping:'sequence'},
		    {name:'obj.log',mapping:'isLog'},
		    {name:'obj.url',mapping:'url'},
		    {name:'obj.parent.functionId',mapping:'parentId'},
		    {name:'obj.sequence',mapping:'sequence'}
		    ]
		),
		items : [this.idField,this.nameField,this.urlField,this.menuField,this.logField,this.sortField,this.parentId,this.parentName],
		buttons:[this.saveBtn]
	});
};
Ext.extend(FunctionFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'function/load.jhtm?id=' + id;
		var _form=this.getForm();
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			success:function(form,action){
				var node=westPanel.getSelectionModel().getSelectedNode();
				if(node.id!=0){
					if(node.parentNode.id==0){
						_form.findField("parentName").setValue('无上级');
					}else{
						_form.findField("parentName").setValue(node.parentNode.text);
					}
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

var FunctionApp= function(){
	
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var formPanel=new FunctionFormPanel();
	return {
		init:function(){
		    FunctionApp.initTree();
			FunctionApp.initLayout();
			
		},
		initLayout:function(){
		    centerPanel=new Ext.Panel(
	     	{
                  region:'center',
				  split:true,
				  layout:'fit',
				  collapsible:false,
                  title:'功能信息',
                  el:'functionFormPanel',
                  border:true,
                  split:true,
                  autoScroll:true,
                  items:[formPanel],
				  tbar:[{
				  	text: '增加同级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:FunctionApp.addTj
				  },'-',{
				  	text: '增加下级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:FunctionApp.addXj
				  }]
             	}
		     );
		     var treePanel=new Ext.Panel({
		    	 layout:'fit',
		    	 el:'functionTreePanel',
		    	 region:'west',
		    	 width:200,
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
				el:'functionTree',
		        autoScroll:true,
		        animate:true,
		        enableDD:false,
				split:true,
				border:true,
				title:'功能结构树',
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:false,
				containerScroll: true, 
				margins: '0 0 0 0',
				tbar:[{
				  	text: '删除',
		            iconCls: 'delete',
					scope:this,
					handler: function(){
						FunctionApp.deleteInfo();
					}
				  }],
		        loader: new Ext.tree.TreeLoader({
		            dataUrl:'function/tree.jhtm'
		        })
		    });
			westPanel.getSelectionModel().on({
		        'beforeselect' : function(sm, node){
					selectNode=node;
					if(node.id!='0'){
						if(!node.expanded){
							node.expand();
						};
						formPanel.loadData(node.id);
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
		        text: '功能树',
		        draggable:false,
		        id:'0'
		    });
		    westPanel.setRootNode(root);
			
			westPanel.render();
			root.select();
			root.expand();
			selectNode=root;
			
		},
		addTj:function(){
			var node=FunctionApp.getSelectedNode();
			if(node.id==0){
				Ext.Msg.alert("添加功能","对不起，您没有权限创建本级功能的同级功能！");
				return;
			}
			
			formPanel.level=1;
			var parent=node.parentNode;
			var form=formPanel.getForm();
			form.findField("obj.functionId").setValue(0);
			form.findField("obj.parent.functionId").setValue(parent.id);
			form.findField("parentName").setValue(parent.text);
			form.findField("obj.functionName").setValue("");
			form.findField("obj.url").setValue("");
			form.findField("obj.sequence").setValue(0);
			form.findField("obj.menu").setValue(true);
			form.findField("obj.log").setValue(true);
			form.findField("obj.functionName").focus(false,true);
			
		}
		,
		addXj:function(){
			var node=FunctionApp.getSelectedNode();
			
			var form=formPanel.getForm();
			formPanel.level=0;
			form.findField("obj.functionId").setValue(0);
			form.findField("obj.parent.functionId").setValue(node.id);
			form.findField("parentName").setValue(node.text);
			form.findField("obj.functionName").setValue("");
			form.findField("obj.sequence").setValue(0);
			form.findField("obj.url").setValue("");
			form.findField("obj.menu").setValue(true);
			form.findField("obj.log").setValue(true);
			form.findField("obj.functionName").focus(false,true);
		},
		deleteInfo:function(){
			var node=westPanel.getSelectionModel().getSelectedNode();
			if(!node){
				Ext.Msg.alert("删除功能",'请选择功能！');
			}
			if(node.firstChild!=null){
				Ext.Msg.alert("删除功能","本功能含有下级功能，请先删除下级功能！");
				return;
			}
			if(!confirm("确定要删除该功能吗？")){
				return;
			}
			var url='function/delete.jhtm';
			Ext.Ajax.request({
				url:url,
				params:'id='+node.id,
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var result="+rep.responseText);
				if(result.success){
					Ext.Msg.alert("删除功能","删除成功！");
					deleteNode=node;
					westPanel.getSelectionModel().selectNext();

					deleteNode.remove();
				}
				
			}
			function failure(rep){
				Ext.Msg.alert("删除功能",rep.repsonseText);
			}
		},
		getSelectedNode:function(){
			var node=westPanel.getSelectionModel().getSelectedNode();
			return node;
		}
	};
}();
FunctionApp.init();