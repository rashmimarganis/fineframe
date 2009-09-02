var westPanel;
var centerPanel;

var PersonFormPanel = function() {
	var _formPanel=this;
	this.idField = {
		xtype : 'textfield',
		fieldLabel : "编号",
		name : "obj.personId",
		readOnly:true
	};
	
	this.realnameField = {
		xtype : 'textfield',
		fieldLabel : "姓名",
		allowBlank : false,
		name : "obj.realname"
	};
	
	this.ageField = {
		xtype : 'textfield',
		fieldLabel : "年龄",
		allowBlank : false,
		name : "obj.age"
	};
	this.addressField = {
			xtype : 'textfield',
			fieldLabel : "地址",
			allowBlank : false,
			width:260,
			name : "obj.address"
		};
	
    this.genderField = new Ext.form.ComboBox({
        store:  new Ext.data.SimpleStore({
            fields: ['value', 'name', 'tip'],
            data : Ext.ux.PersonGender 
        }),
        fieldLabel: '性别',
        displayField:'name',
        valueField:'value',
        readOnly:true,
        typeAhead: true,
        allowBlank:false,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择类型...',
        selectOnFocus:true,
        hiddenName: 'obj.gender'
    });
	
	this.emailField = {
		xtype:'textfield',
		vtype:'email',
		fieldLabel : "Email",
		allowBlank : false,
		name : "obj.email"
	};
	this.sequenceField = {
		xtype : 'numberfield',
		fieldLabel : "排序",
		allowBlank : false,
		name : "obj.sequence"
	};
    
	this.homeTelephoneField = {
		xtype : 'textfield',
		fieldLabel : "家庭电话",
		allowBlank : true,
		name : "obj.homeTelephone"
	};
	
	
	this.officeTelephoneField = {
		xtype : 'textfield',
		fieldLabel : "办公电话",
		allowBlank : true,
		name : "obj.officeTelephone"
	};
	
	this.mobilephoneField = {
		xtype : 'textfield',
		fieldLabel : "手机",
		allowBlank : true,
		name : "obj.mobilephone"
	};
	this.orgField = {
            xtype:'combotree',
            fieldLabel:'所属组织',
            name:'obj.org.orgId',
            allowUnLeafClick:true,
            treeHeight:200,
            allowBlank:false,
            url:'org/tree.jhtm',
            onSelect:function(id){
            }
	};
	
	this.birthdayField =new Ext.form.DateField({
        fieldLabel: '生日',
        name: 'obj.birthday',
        width:190,
        format: "Y-m-d",
        width:120,
        selectOnFocus:true,
        allowBlank:false
    });
	PersonFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		width:230,
		labelWidth:90,
        height:200,
		waitMsgTarget:'main',
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			id : 'personId'
		}, [
		    {name:'obj.personId', mapping:'personId'},
		    {name:'obj.realname',mapping:'realname'}, 
		    {name:'obj.age',mapping:'age'},
		    {name:'obj.homeTelephone',mapping:'homeTelephone'},
		    {name:'obj.officeTelephone',mapping:'officeTelephone'},
		    {name:'obj.address',mapping:'address'},
		    {name:'obj.email',mapping:'email'},
		    {name:'obj.mobilephone',mapping:'mobilephone'},
		    {name:'obj.gender',mapping:'gender'},
		    {name:'obj.sequence',mapping:'sequence'}
		    ]
		),
		items : [
		         this.idField,
		         this.realnameField,
		         this.genderField,
		         this.ageField,
		         this.birthdayField,
		         this.orgField,
		         this.emailField,
		         this.addressField,
		         this.homeTelephoneField,
		         this.officeTelephoneField,
		         this.mobilephoneField,
		         this.sequenceField
		         ]
	});
};
Ext.extend(PersonFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'person/load.jhtm?id=' + id;
		var _form=this.getForm();
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			success:function(form, action){
				var json = action.response.responseText;
				
				var o = eval("(" + json + ")");
				if(o.success){
					_form.findField("obj.org.orgId").setFieldValue(o.data[0].orgId,o.data[0].orgName);
					var r=formatDate(o.data[0].birthday);
					_form.findField("obj.birthday").setRawValue(r);
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
				url : 'person/save.jhtm',
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
						form1.findField("obj.personId").setValue(o.id);
						Ext.Msg.alert("保存人员","人员保存成功");
					}else{
						Ext.Msg.alert("保存人员","人员保存失败！");
					}

				}
			});
		}else{
			Ext.Masg.alert('验证信息','请把人员信息填写正确！');
		}
	}
});

PersonGridPanel=function(){
	this.orgId=0;
	this.window=new PersonWindow();
	this.window.gridPanel=this;
	var _win=this.window;
	var _grid=this;
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'person/list.jhtm?orgId='+_grid.orgId
        }),
        reader: new Ext.data.JsonReader({
            root: 'objs',
            totalProperty: 'totalCount',
            id: 'personId',
            fields: [
                'personId','realname', 'age','mobilephone','birthday','gender','email','address','email','homeTelephone','officeTelephone','address','sequence'
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
        id: 'personId',
        header: "ID",
        dataIndex: 'personId',
        width: 20
     },{
        id: 'realname', 
        header: "姓名",
        dataIndex: 'realname',
        width: 100
     },{
        header: "性别",
        dataIndex: 'gender',
        width: 100,
        renderer:formatGender
     },{
         header: "年龄",
         dataIndex: 'age',
         width: 100
      },{
		header:"生日",
		dataIndex:'birthday',
		width:100,
		renderer:formatDate
      },{
		header:"email",
		dataIndex:'email',
		width:100
      },{
		header:"家庭电话",
		dataIndex:'homeTelephone',
		width:100
      }, {
  		header:"办公电话",
  		dataIndex:'officeTelephone',
  		width:100
      },{
  		header:"手机",
  		dataIndex:'mobilephone',
  		width:100
        },
      ]);

    this.cm.defaultSortable = true;
    PersonGridPanel.superclass.constructor.call(
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
							text : '添加人员',
							iconCls : 'x-btn-text-icon add',
							scope : this,
							handler : _grid.addInfo
						}, '-', {
							text : '修改人员',
							iconCls : 'x-btn-text-icon edit',
							scope : this,
							handler : _grid.loadInfo
						}, '-', {
							text : '删除人员',
							iconCls : 'x-btn-text-icon delete',
							scope : this,
							handler : _grid.deleteInfo
						} 
				],
				renderTo : 'personGridPanel'
			});
    _grid.on("dblclick",function(index,record){
    	var select=this.getSelectionModel().getSelected();
    	if(select){
    		_win.loadData(select.get("personId"));
    		_win.show();
    	}
    });
};
Ext.extend(PersonGridPanel, Ext.grid.GridPanel, {
	addInfo:function(){
		this.window.show();
		var form=this.window.formPanel.getForm();
		var node=westPanel.getSelectionModel().getSelectedNode();
		
		form.findField("obj.personId").setValue(0);
		if(node){
			form.findField("obj.org.orgId").setFieldValue(node.id,node.text);
		}
		form.findField("obj.realname").setValue("");
		form.findField("obj.age").setValue("0");
		form.findField("obj.gender").setValue("m");
		form.findField("obj.address").setValue("");
		form.findField("obj.email").setValue("@");
		form.findField("obj.birthday").setValue("");
		form.findField("obj.homeTelephone").setValue("");
		form.findField("obj.officeTelephone").setValue("");
		form.findField("obj.mobilephone").setValue("");
		form.findField("obj.sequence").setValue(0);
		form.findField("obj.realname").focus(false,true);
		
	},
	loadInfo:function(){
		var select=this.getSelectionModel().getSelected();
		if(select){
			this.window.show();
			this.window.loadData(select.get("personId"));
		}else{
			Ext.Msg.alert("显示人员","请选择一个人员！");
		}
	},
	deleteInfo:function(){
		
		var node=this.getSelectionModel().getSelected();
		if(!node){
			Ext.Msg.alert("删除人员",'请选择人员！');
			return;
		}
		
		if(!confirm("确定要删除该人员吗？")){
			return;
		}
		var url='person/delete.jhtm?'+this.getSelectedIds();
		Ext.Ajax.request({
			url:url,
			success:success,
			failure:failure
		});
		var _grid=this;
		function success(rep){
			eval("var result="+rep.responseText);
			if(result.success){
				Ext.Msg.alert("删除人员","删除成功！");
				_grid.reloadData();
			}
			
		}
		function failure(rep){
			Ext.Msg.alert("删除人员",rep.repsonseText);
		}
	},
	loadData:function(orgId){
		this.store.proxy=new Ext.data.HttpProxy({
            url: 'person/list.jhtm?orgId='+orgId
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
					ids='ids='+r.get("personId");
				}else{
					ids+="&ids="+r.get("personId");
				}
		}
		return ids;
		
	}
});

PersonWindow=function(){
	this.gridPanel;
	this.formPanel=new PersonFormPanel();
	this.form=this.formPanel.getForm();
	var _form=this.form;
	var _formPanel=this.formPanel;
	var _win=this;
	var _gridPanel=this.gridPanel;
	PersonWindow.superclass.constructor.call(this, {
		title : '人员信息',
		width : 420,
		height : 400,
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
						url : 'person/save.jhtm',
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

Ext.extend(PersonWindow, Ext.Window, {
	loadData : function(id) {
		this.formPanel.loadData(id);
	}
});


var PersonApp= function(){
	
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var gridPanel=new PersonGridPanel();
	return {
		init:function(){
		    PersonApp.initTree();
			PersonApp.initLayout();
			
		},
		initLayout:function(){
		    
		     var treePanel=new Ext.Panel({
		    	 layout:'fit',
		    	 el:'personOrgPanel',
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
                   title:'人员列表',
                   el:'personPanel',
                   contentEl:'personGridPanel',
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
				el:'personOrgTree',
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
PersonApp.init();