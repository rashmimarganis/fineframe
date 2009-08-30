var westPanel;
var centerPanel;

var OrgFormPanel = function() {
	var _formPanel=this;
	this.level=1;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.orgId",
		readOnly:true
	};
	this.parentId={
		xtype : 'hidden',
		fieldLabel : "上级编号",
		name : "obj.parent.orgId"
	};
	this.parentName={
		xtype : 'textfield',
		fieldLabel : "上级组织",
		name : "obj.parent.name",
		readOnly:true
	};
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "名称",
		allowBlank : false,
		name : "obj.name"
	};

	this.sortField = {
		xtype : 'numberfield',
		fieldLabel : "排序",
		allowBlank : false,
		name : "obj.sort"
	};

	var typeStore=new Ext.data.SimpleStore({
        fields: ['name', 'title','tip'],
        data : Ext.ux.OrgType 
    });
    this.typeField = new Ext.form.ComboBox({
        store:typeStore,
        displayField:'title',
        valueField:'name',
        fieldLabel: '组织类型',
        readOnly:true,
        typeAhead: true,
        allowBlank:false,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择类型...',
        selectOnFocus:true,
        hiddenName: 'obj.type'
    });
  
    this.saveBtn=new Ext.Button({
    	text:'保存组织',
    	handler:function(){
    		var _form=_formPanel.getForm();
	    	if (_form.isValid()) {
				_form.submit( {
					waitMsg : '正在保存数据...',
					url : 'org/save.jhtm',
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
									node.setText(_form.findField("obj.name").getValue());
								}else{
									var id=o.id;
									_formPanel.getForm().findField("obj.orgId").setValue(id);
									var text=_formPanel.getForm().findField("obj.name").getValue();
									var newNode = new Ext.tree.TreeNode({id:id,text:text,leaf:true});
									alert(_formPanel.level);
									if(_formPanel.level==0){
										if(node.attributes.iconCls=='file'){
											node.attributes.iconCls=='folder';
										}
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
				Ext.Masg.alert('验证信息','请把组织信息填写正确！');
			}
    	}
    });
	
	OrgFormPanel.superclass.constructor.call(this, {
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
			id : 'orgId'
		}, [
		    {name:'obj.orgId', mapping:'orgId'},
		    {name:'obj.name',mapping:'name'}, 
		    {name:'obj.type',mapping:'type'},
		    {name:'obj.parent.orgId',mapping:'parentId'},
		    {name:'obj.sort',mapping:'sort'}
		    ]
		),
		items : [this.idField,this.nameField,this.typeField,this.sortField,this.parentId,this.parentName],
		buttons:[this.saveBtn]
	});
};
Ext.extend(OrgFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'org/load.jhtm?id=' + id;
		var _form=this.getForm();
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			success:function(form,action){
				var node=westPanel.getSelectionModel().getSelectedNode();
				if(node.id!=0){
					_form.findField("obj.parent.name").setValue(node.parentNode.text);
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

var OrgApp= function(){
	
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var formPanel=new OrgFormPanel();
	return {
		init:function(){
		    OrgApp.initTree();
			OrgApp.initLayout();
			
		},
		initLayout:function(){
		    centerPanel=new Ext.Panel(
	     	{
                  region:'center',
				  split:true,
				  layout:'fit',
				  collapsible:false,
                  title:'组织信息',
                  el:'orgFormPanel',
                  border:true,
                  split:true,
                  autoScroll:true,
                  items:[formPanel],
				  tbar:[{
				  	text: '增加同级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:OrgApp.addTj
				  },'-',{
				  	text: '增加下级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:OrgApp.addXj
				  }]
             	}
		     );
		     var treePanel=new Ext.Panel({
		    	 layout:'fit',
		    	 el:'orgTreePanel',
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
				el:'orgTree',
		        autoScroll:true,
		        animate:true,
		        enableDD:false,
				split:true,
				border:true,
				title:'组织结构树',
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
				containerScroll: true, 
				margins: '0 0 0 0',
				tbar:[{
				  	text: '删除',
		            iconCls: 'delete',
					scope:this,
					handler: function(){
						OrgApp.deleteInfo();
					}
				  }],
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
		        text: '当前组织',
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
			var node=OrgApp.getSelectedNode();
			if(node.id==0){
				Ext.Msg.alert("添加组织","对不起，您没有权限创建本级组织的同级组织！");
				return;
			}
			
			formPanel.level=1;
			alert(formPanel.level);
			var parent=node.parentNode;
			var form=formPanel.getForm();
			form.findField("obj.orgId").setValue(0);
			form.findField("obj.parent.orgId").setValue(parent.id);
			form.findField("obj.parent.name").setValue(parent.text);
			form.findField("obj.name").setValue("");
			form.findField("obj.sort").setValue(0);
			form.findField("obj.name").focus(false,true);
			
		}
		,
		addXj:function(){
			var node=OrgApp.getSelectedNode();
			
			var form=formPanel.getForm();
			formPanel.level=0;
			alert(formPanel.level);
			form.findField("obj.orgId").setValue(0);
			form.findField("obj.parent.orgId").setValue(node.id);
			form.findField("obj.parent.name").setValue(node.text);
			form.findField("obj.name").setValue("");
			form.findField("obj.sort").setValue(0);
			form.findField("obj.name").focus(false,true);
		},
		deleteInfo:function(){
			var node=westPanel.getSelectionModel().getSelectedNode();
			if(!node){
				Ext.Msg.alert("删除组织",'请选择组织！');
			}
			if(node.firstChild!=null){
				Ext.Msg.alert("删除组织","本组织含有下级组织，请先删除下级组织！");
				return;
			}
			if(!confirm("确定要删除该组织吗？")){
				return;
			}
			var url='org/delete.jhtm';
			Ext.Ajax.request({
				url:url,
				params:'id='+node.id,
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var result="+rep.responseText);
				if(result.success){
					Ext.Msg.alert("删除组织","删除成功！");
					deleteNode=node;
					var node1=selectNode.nextSibling;
					deleteNode.remove();
					node1.select();
				}
				
			}
			function failure(rep){
				Ext.Msg.alert("删除组织",rep.repsonseText);
			}
		},
		getSelectedNode:function(){
			var node=westPanel.getSelectionModel().getSelectedNode();
			return node;
		}
	};
}();
OrgApp.init();